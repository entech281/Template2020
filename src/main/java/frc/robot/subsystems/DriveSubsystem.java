/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import frc.robot.drive.filter.AlignLateralFilter;
import frc.robot.drive.DriveInput;
import frc.robot.drive.filter.HoldYawFilter;
import frc.robot.drive.filter.JoystickJitterFilter;
import frc.robot.drive.filter.NudgeLeftFilter;
import frc.robot.drive.filter.NudgeRightFilter;
import frc.robot.drive.filter.RobotRelativeDriveFilter;
import frc.robot.drive.filter.TwistFilter;
import frc.robot.Robot;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.logging.SmartDashboardLogger;
import frc.robot.drive.DriveInputAggregator;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Add your docs here.
 */
public class DriveSubsystem extends BaseSubsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private Robot robot;
  private WPI_TalonSRX frontLeftTalon  = new WPI_TalonSRX(RobotMap.CAN.FRONT_LEFT_MOTOR);
  private WPI_TalonSRX rearLeftTalon   = new WPI_TalonSRX(RobotMap.CAN.REAR_LEFT_MOTOR);
  private WPI_TalonSRX frontRightTalon = new WPI_TalonSRX(RobotMap.CAN.FRONT_RIGHT_MOTOR);	
  private WPI_TalonSRX rearRightTalon  = new WPI_TalonSRX(RobotMap.CAN.REAR_RIGHT_MOTOR);
  private MecanumDrive robotDrive = new MecanumDrive(frontLeftTalon,rearLeftTalon,frontRightTalon,rearRightTalon);
  
  private TwistFilter twistFilter = new TwistFilter();
  private JoystickJitterFilter joystickJitterFilter = new JoystickJitterFilter();
  private RobotRelativeDriveFilter robotRelativeDriveFilter = new RobotRelativeDriveFilter();

  private HoldYawFilter holdYawFilter = null;
  private AlignLateralFilter alignLateralFilter = null;

  private NudgeRightFilter nudgeRightFilter = new NudgeRightFilter();
  private NudgeLeftFilter nudgeLeftFilter = new NudgeLeftFilter();

  private boolean targetLock = false;
  private boolean alignCargo;

  private final NetworkTableInstance ntist = NetworkTableInstance.getDefault();
  private final NetworkTableEntry targetLockReporter = ntist.getEntry("team281.targetLock.buttonPressed");
  
  private DriveInputAggregator inputAggregator = new DriveInputAggregator(true,true);
  
  public DriveSubsystem(Robot robot) {
    this.robot = robot;
  }

  @Override
  public void initialize() {
    frontLeftTalon.setInverted(false);
    rearLeftTalon.setInverted(false);
    frontRightTalon.setInverted(false);
    rearRightTalon.setInverted(false);
    
    frontLeftTalon.enableCurrentLimit(false);
    rearLeftTalon.enableCurrentLimit(false);
    frontRightTalon.enableCurrentLimit(false);
    rearRightTalon.enableCurrentLimit(false);
    
    holdYawFilter = new HoldYawFilter();
    holdYawFilter.disable();

    alignLateralFilter = new AlignLateralFilter();
    alignLateralFilter.disable();

    // Use drive subsystem to filter Joytsick not our own filter
    robotDrive.setDeadband(0.1);
    joystickJitterFilter.disable();
    robotRelativeDriveFilter.disable();
    twistFilter.enable();
  }

  @Override
  public void periodic() {
      periodicStopWatch.start("Drive Subsystem");
      SmartDashboardLogger.putBoolean("Robot Relative Drive:", robotRelativeDriveFilter.isEnabled());
      SmartDashboardLogger.putBoolean("Target Lock Enabled", targetLock);
      
      targetLockReporter.forceSetBoolean(targetLock);
      periodicStopWatch.end("Drive Subsystem");   
  }

  public void setAlignCargoBoolean(boolean alignCargo){
    this.alignCargo = alignCargo;
  }

  public void drive(DriveInput di) {
      
    DriveInput telemetryDriveInput = inputAggregator.mergeTelemetry(di, 
            this.robot.getNavXSubsystem().getDriveInput(),
            this.robot.getVisionSubsystem().getDriveInput(), alignCargo);
    
    SmartDashboardLogger.putNumber("Telemetry::LateralOffset", telemetryDriveInput.getTargetLateral());
    SmartDashboardLogger.putNumber("Telemetry::YawAngle", telemetryDriveInput.getFieldAngle());
    SmartDashboardLogger.putNumber("Telemetry::Distance", telemetryDriveInput.getTargetDistance());
    
    DriveInput filteredDriveInput =  applyActiveFilters(telemetryDriveInput);
    SmartDashboardLogger.putBoolean("DriveInput HoldYawOn", holdYawFilter.isEnabled());
    SmartDashboardLogger.putBoolean("DriveInput LateralAlignOn", alignLateralFilter.isEnabled());
    
    SmartDashboardLogger.putDriveInput("Operator Input", di);
    SmartDashboardLogger.putDriveInput("DriveInput JS", filteredDriveInput);
    
    robotDrive.driveCartesian(
            filteredDriveInput.getX(), 
            filteredDriveInput.getY(), 
            filteredDriveInput.getZ(), 
            -filteredDriveInput.getFieldAngle());
  }

  public DriveInput applyActiveFilters(DriveInput di) {
    // Add filters in here, be mindful of order!
    di = twistFilter.filter(di);
    di = joystickJitterFilter.filter(di);


    if (nudgeLeftFilter.isEnabled()) {
      di = nudgeLeftFilter.filter(di);
    } else if (nudgeRightFilter.isEnabled()) {
      di = nudgeRightFilter.filter(di);
    } else {
      di = holdYawFilter.filter(di);
      if ( alignLateralFilter.isEnabled()){
          di = alignLateralFilter.filter(di);
      }      
    }
    di = robotRelativeDriveFilter.filter(di);    
    return di;
  }

  public void toggleFieldAbsolute() {
    if (robotRelativeDriveFilter.isEnabled()) {
      robotRelativeDriveFilter.disable();
    } else {
      robotRelativeDriveFilter.enable();
    }
  }

  public void setFieldAbsolute(boolean enable) {
    if (enable) {
      robotRelativeDriveFilter.disable();
    } else {
      robotRelativeDriveFilter.enable();
    }
  }

  public void twistOn(boolean enabled) {
    // THIS IS NOT BACKWARDS
    // When enabled, the twist filter turns off the twist
    if ( enabled ) {
      twistFilter.disable();
    } else {
      twistFilter.enable();
    }
  }

  public void nudgeRight() {
    nudgeRightFilter.enable();
    nudgeLeftFilter.disable();
  }

  public void nudgeLeft() {
    nudgeLeftFilter.enable();
    nudgeRightFilter.disable();
  }

  public void enableHoldYaw(double angle){
      holdYawFilter.setDesiredYaw(angle);
      holdYawFilter.enable();
  }
  public void disableHoldYaw(){
      holdYawFilter.disable();
  }

  public void alignWithTarget(boolean enable) {
    if (enable) {
      alignLateralFilter.enable();
      targetLock = true;
    } else {
      alignLateralFilter.disable();
      targetLock = false;
    }
    
  }


}

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.logging.SmartDashboardLogger;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class FlipSubsystem extends BaseSubsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private double desiredSpeed = 0;

  private WPI_TalonSRX climbTalon  = new WPI_TalonSRX(RobotMap.CAN.CLIMB);

  @Override
  public void initialize() {
    
  }
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  public void periodic() {
    
    periodicStopWatch.start("Flip subsystem");
    SmartDashboardLogger.putNumber("Flip Motor Speed", getDesiredSpeed());
    periodicStopWatch.end("Flip subsystem");
  }

  public void forward() {
    desiredSpeed = 1;
    climbTalon.set(ControlMode.PercentOutput, desiredSpeed);
  }

  public void backward() {
    desiredSpeed = -1;
    climbTalon.set(ControlMode.PercentOutput, desiredSpeed);
  }
  
  public void stop() {
    climbTalon.set(ControlMode.PercentOutput, 0);
  }

  public double getDesiredSpeed() {
    return desiredSpeed;
  }

  public void setDesiredSpeed(double desiredSpeed) {
    this.desiredSpeed = desiredSpeed;
  }
}

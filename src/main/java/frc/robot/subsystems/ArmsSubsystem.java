/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import frc.logging.SmartDashboardLogger;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class ArmsSubsystem extends BaseSubsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public ArmsSubsystem() {
    super();
  }

  private DoubleSolenoid squeezeSolenoid;
  private DoubleSolenoid deploySolenoid;
  private Timer timer = new Timer();

  private boolean isDeploying = false;
  
  @Override
  public void initialize() {
    squeezeSolenoid = new DoubleSolenoid(RobotMap.CAN.PCM_ID, 
      RobotMap.PNEUMATICS.ARMS_GRAB_FORWARD, RobotMap.PNEUMATICS.ARMS_GRAB_REVERSE);
    deploySolenoid = new DoubleSolenoid(RobotMap.CAN.PCM_ID, 
      RobotMap.PNEUMATICS.ARMS_DEPLOY_FORWARD, RobotMap.PNEUMATICS.ARMS_DEPLOY_REVERSE);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  public void periodic() {
    periodicStopWatch.start("Arms Subsystem");
    SmartDashboardLogger.putBoolean("Deploying", isDeploying);
    if(isDeploying){
      updateDeploy();
    }
    periodicStopWatch.end("Arms Subsystem");
  }

public void updateDeploy(){
  if(timer.get() <=1.5){
    deploySolenoid.set(DoubleSolenoid.Value.kForward);
  }else if(timer.get() <=3){
    deploySolenoid.set(DoubleSolenoid.Value.kReverse);
  }else if(timer.get() <=4.5){
    deploySolenoid.set(DoubleSolenoid.Value.kForward);
  }
  else{
    isDeploying = false;
    timer.stop();
  }
}

  public void squeeze() {
    squeezeSolenoid.set(DoubleSolenoid.Value.kForward);
  }

  public void release() {
    squeezeSolenoid.set(DoubleSolenoid.Value.kReverse);
  }

  public void deploy() {
    isDeploying = true;
    timer.reset();
    timer.start();
  }

  public void reverse(){
    deploySolenoid.set(DoubleSolenoid.Value.kReverse);
  }
}

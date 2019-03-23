/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.ArmsSubsystem;

public class ArmsSqueeze extends Command {
  private ArmsSubsystem arms;
  private int counter = 0;

  public ArmsSqueeze(ArmsSubsystem arms) {
    this.arms=arms;
    requires(arms);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    arms.squeeze();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    arms.squeeze();
    counter++;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if ( counter > 10 ) {
      return true;
    } else {
      return false;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}

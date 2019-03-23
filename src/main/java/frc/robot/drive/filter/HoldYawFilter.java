/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.drive.filter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.pid.CappedLinearControl;
import frc.robot.drive.DriveInput;



/**
 * Add your docs here.
 */
public class HoldYawFilter extends DriveFilter {

  public static final double ANGLE_THRESHOLD_DEGREES = 1;
  private CappedLinearControl cappedLinear = new CappedLinearControl(ANGLE_THRESHOLD_DEGREES,
                    15.0, 0.15, 0.4);
  double desiredAngle = 0.0;

  public HoldYawFilter() {
    super(false);
    this.cappedLinear.manage180Degrees(true);
  }

  public void setDesiredYaw(double angle) {
       desiredAngle = angle;
  }

  @Override
  public DriveInput doFilter(DriveInput input) {
    SmartDashboard.putNumber("DesiredYawAngle", desiredAngle);
    double twist = cappedLinear.getOutput(input.getFieldAngle(),desiredAngle);
    
    return new DriveInput(input.getX(), 
            input.getY(), 
            twist, 
            input.getFieldAngle(), 
            input.getTargetDistance(), 
            input.getTargetLateral());
  }
}

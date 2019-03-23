/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.drive.filter;

import frc.robot.drive.DriveInput;

/**
 * Override the drive input to a joystick right
 * @author mandrews
 */
public class RobotRelativeDriveFilter extends DriveFilter {
    public RobotRelativeDriveFilter() {
        super(false);
    }

    @Override
    protected void onEnable() {
    }
    
    @Override
    public DriveInput doFilter(DriveInput input) {
        return new DriveInput(input.getX(), input.getY(), input.getZ(), 0.0, input.getTargetDistance(), input.getTargetLateral());
    }
}

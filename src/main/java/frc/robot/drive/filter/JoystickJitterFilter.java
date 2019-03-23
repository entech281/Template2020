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
public class JoystickJitterFilter extends DriveFilter {
    public JoystickJitterFilter() {
        super(false);
    }

    @Override
    protected void onEnable() {
    }
    
    @Override
    public DriveInput doFilter(DriveInput input) {
        double x = input.getX();
        double y = input.getY();
        double z = input.getZ();

        if (Math.abs(x) < 0.05) {
            x = 0.0;
        }
        if (Math.abs(y) < 0.05) {
            y = 0.0;
        }
        if (Math.abs(z) < 0.05) {
            z = 0.0;
        }

        return new DriveInput(x, y, z, input.getFieldAngle(), input.getTargetDistance(), input.getTargetLateral());
    }
}

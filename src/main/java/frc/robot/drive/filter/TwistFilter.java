/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.drive.filter;

import frc.robot.drive.DriveInput;
import frc.robot.drive.filter.DriveFilter;

/**
 * Override the drive input to a joystick right
 * @author mandrews
 */
public class TwistFilter extends DriveFilter {
    public TwistFilter() {
        super(false);
    }

    @Override
    protected void onEnable() {
    }
    
    @Override
    public DriveInput doFilter(DriveInput input) {
        return new DriveInput(input.getX(), input.getY(), 0.0, 
                input.getFieldAngle(), input.getTargetDistance(), input.getTargetLateral());
    }
}

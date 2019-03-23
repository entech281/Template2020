/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.drive.filter;

import frc.robot.drive.DriveInput;
import frc.robot.drive.DriveInput;

/**
 * Base Drive Filter
 * @author dcowden
 */
public abstract class DriveFilter {
    private boolean enabled = true;

    public DriveFilter(boolean start_state) {
        this.enabled = start_state;
    }
    
    public void enable(){
        this.enabled = true;
        onEnable();
    }
    protected void onEnable() {}

    public void disable(){
        this.enabled = false;
        onDisable();
    }
    protected void onDisable() {}

    public boolean isEnabled(){
        return this.enabled;
    }
    public DriveInput filter(DriveInput input){
        if ( this.enabled ) {
            return doFilter(input);
        }
        else{
            return input;
        }
    }
    abstract DriveInput doFilter(DriveInput input);
}

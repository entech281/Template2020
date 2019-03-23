/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.NavXSubsystem;

/**
 *
 * @author dcowden
 */
public class SquareUpCommand extends Command{

    public enum LockOption{
        STRAIGHT,
        ANGLED
    }
    private DriveSubsystem drive;
    private NavXSubsystem navx;
    private LockOption lockOption;
    public SquareUpCommand(NavXSubsystem navx, DriveSubsystem drive,LockOption lockOption){
        this.drive = drive;
        this.navx = navx;
        this.lockOption = lockOption;
    }

    @Override
    protected void initialize() {
        double angle = 0;
        if ( lockOption == LockOption.STRAIGHT){
            angle = navx.findNearestQuadrant();
        }
        else{
            angle = navx.findNearestAngledQuadrant();
        }
        drive.enableHoldYaw(angle);        
    }
    
    @Override
    protected boolean isFinished() {
        return true;
    }
    
}

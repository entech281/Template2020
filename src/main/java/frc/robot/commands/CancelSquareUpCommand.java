/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.DriveSubsystem;

/**
 *
 * @author dcowden
 */
public class CancelSquareUpCommand extends Command{

    private DriveSubsystem drive;
    public CancelSquareUpCommand(DriveSubsystem drive){
        this.drive = drive;
    }

    @Override
    protected void initialize() {
        drive.disableHoldYaw();
    }
    
    @Override
    protected void interrupted() {
        super.interrupted(); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    protected boolean isFinished() {
        return true;
    }
    
}

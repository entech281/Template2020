/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.logging.SmartDashboardLogger;
import frc.robot.drive.DriveInput;
import frc.robot.drive.GetDriveInput;

public class VisionSubsystem extends BaseSubsystem implements GetDriveInput {

    private double UNKNOWN = 99999999;
    private double lastFrameCount = 0;
    private double lastDistanceFromTarget = UNKNOWN;
    private double lastLateralDistance = UNKNOWN;

    //private double scaleFactor = 1.0;


    private NetworkTableInstance ntist;
    NetworkTableEntry distance;
    NetworkTableEntry lateral;
    NetworkTableEntry frameCount;


    public VisionSubsystem() {
    }

    @Override
    public void initialize() {
        ntist = NetworkTableInstance.getDefault();
        distance = ntist.getEntry("team281.Vision.distance");
        lateral = ntist.getEntry("team281.Vision.lateral");
        frameCount = ntist.getEntry("team281.frameCount");
    }

    @Override
    public void periodic() {
        periodicStopWatch.start("Vision subsystem");
        SmartDashboardLogger.putNumber("Frame Count:", frameCount.getDouble(UNKNOWN));
        SmartDashboardLogger.putNumber("Vision Distance To Target:", distance.getDouble(UNKNOWN));
        SmartDashboardLogger.putNumber("Vision Lateral:", lateral.getDouble(UNKNOWN));  
        periodicStopWatch.end("Vision subsystem");
    }

    @Override
    public DriveInput getDriveInput() {
        double currFrameCount = frameCount.getDouble(lastFrameCount);
        double lateralDistance = lateral.getDouble(UNKNOWN);
        double targetDistance = distance.getDouble(UNKNOWN);
        
        boolean valid = true;
        if ( currFrameCount <= lastFrameCount){
            valid = false;
        }
        if ( lateralDistance > 500 ){
            valid = false;
        }
        if ( targetDistance > 2000 ){
            valid = false;
        }
        if ( Double.isNaN(lateralDistance) || Double.isInfinite(lateralDistance)){
            valid = false;
        }
        if ( Double.isNaN(targetDistance) || Double.isInfinite(targetDistance)){
            valid = false;
        }
        
        DriveInput di = new DriveInput();  // created as invalid
        if (valid) {
          //scaleFactor = 1.0;
          lastDistanceFromTarget = distance.getDouble(UNKNOWN);
          lastLateralDistance = (-1)*lateral.getDouble(UNKNOWN);
          di.setTargetDistance(lastDistanceFromTarget);
          di.setTargetLateral(lastLateralDistance);
        } 
        SmartDashboardLogger.putBoolean("Vision Input Valid", valid);
        return di;
    }

}
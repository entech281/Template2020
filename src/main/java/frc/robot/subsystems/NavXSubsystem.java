/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.logging.SmartDashboardLogger;
import frc.robot.drive.DriveInput;
import frc.robot.drive.GetDriveInput;

/**
 *
 * @author dcowden
 */
public class NavXSubsystem extends BaseSubsystem implements GetDriveInput {

    private final AHRS navX = new AHRS(SPI.Port.kMXP);
    private double angle_scale = 1.0;
    private double latestYawAngle = 0.0;
    
    
    public NavXSubsystem() {
    }

    @Override
    public void initialize() {
        DriverStation.reportWarning("NavX Initialize Start", false);
        while (navX.isCalibrating()) {
            ;
        }
        navX.zeroYaw();
        DriverStation.reportWarning("NavX Initialize Complete", false);
    }
    
    @Override
    public DriveInput getDriveInput() {
        DriveInput di = new DriveInput();
        if (navX != null) {
            di.setFieldAngle(latestYawAngle);
        }
        return di;
    }

    public double getAngle() {
        return latestYawAngle;
    }

    public void zeroYaw() {
        navX.zeroYaw();
    }

    public void flipOutputAngle(boolean flip) {
      if (flip) {
        angle_scale = -1.0;
      } else {
        angle_scale = 1.0;
      }
    }

    @Override
    public void periodic() {
        periodicStopWatch.start("NAVX subsystem");
        latestYawAngle = angle_scale*navX.getYaw();
        SmartDashboard.putData(navX);
        SmartDashboardLogger.putNumber("Yaw Angle", latestYawAngle);
        SmartDashboardLogger.putNumber("Field Angle", angle_scale*navX.getAngle());
        periodicStopWatch.end("NAVX subsystem");
    }    

    public static double findNearestQuadrant(double angle){
        if (angle <= -135.0) {
          return -180.0;
        //} else if (angle < -112.5) {
        //  return -135.0;
        } else if (angle <= -45.0) {
          return -90.0;
        //} else if (angle < -22.5) {
        //  return -45.0;
        } else if (angle <= 45.0) {
          return 0.0;
        //} else if (angle < 67.5) {
        //  return 45.0;
        } else if (angle <= 135.0) {
          return 90.0;
        //} else if (angle < 157.5) {
        //  return 135.0;
        } else {
          return 180.0;
        }        
    }
    
    public static double findNearestRocketSide(double angle){
        if (angle < -81.25) {
           return -140.25;
        }
        else if (angle < 0) {
            return -22.25;
        } else if (angle < 81.25) {
            return 22.25;
        } 
        else{
            return 151.25;
        }        
    }    
    
    public double findNearestAngledQuadrant(){
        return findNearestRocketSide(angle_scale*navX.getYaw());
    }
    public double findNearestQuadrant() {
        return findNearestQuadrant(angle_scale*navX.getYaw());
    }

}

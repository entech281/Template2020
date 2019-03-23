package frc.robot.drive;

import frc.robot.subsystems.NavXSubsystem;


/**
 *
 * @author dcowden
 */
public class DriveInputAggregator {
    
    private boolean enableVision = true;
    private boolean includeAngleOffset = true;
    
    public DriveInputAggregator( boolean enableVision, boolean includeAngleOffset){
        this.enableVision = enableVision;
        this.includeAngleOffset = includeAngleOffset;
    }

    
    public DriveInput mergeTelemetry(DriveInput input, DriveInput navx, DriveInput vision,  boolean alignCargo){
        DriveInput result = input.copy();
       
        if (navx.isValid()) {
          result.setFieldAngle(navx.getFieldAngle());
        }
        
        if (vision.isValid() && enableVision) {
            
          //this version considers lateral offset via misalignment
          //to the target
          //if we're looking askew at the targets, we have to adjust 
          //for our perspective when calculating alignment
          if ( navx.isValid() && includeAngleOffset && alignCargo){
             double nearestQuadrantAngle = NavXSubsystem.findNearestQuadrant(navx.getFieldAngle());
             double deltaAngle = nearestQuadrantAngle - navx.getFieldAngle();
             double deltaAngleRads = Math.toRadians(deltaAngle);
             double dist = vision.getTargetDistance()*Math.cos(deltaAngleRads);
             double offsetDueToAngle = vision.getTargetDistance()*Math.sin(deltaAngleRads);
             double projectedLateralOffset = vision.getTargetLateral()*Math.cos(deltaAngleRads);
             result.setTargetDistance(dist);
             result.setTargetLateral(offsetDueToAngle+projectedLateralOffset);
          }
          else if(navx.isValid() && includeAngleOffset && alignCargo== false){
            double nearestRocketSide = NavXSubsystem.findNearestRocketSide(navx.getFieldAngle());
             double deltaAngle = nearestRocketSide - navx.getFieldAngle();
             double deltaAngleRads = Math.toRadians(deltaAngle);
             double dist = vision.getTargetDistance()*Math.cos(deltaAngleRads);
             double offsetDueToAngle = vision.getTargetDistance()*Math.sin(deltaAngleRads);
             double projectedLateralOffset = vision.getTargetLateral()*Math.cos(deltaAngleRads);
             result.setTargetDistance(dist);
             result.setTargetLateral(offsetDueToAngle+projectedLateralOffset);
          }
          else{
            //this version does not consider the lateral affect of 
            //angular offset to the target.              
            result.setTargetDistance(vision.getTargetDistance());
            result.setTargetLateral(vision.getTargetLateral());  
          }
        }
        
        if ( ! canDisplay(result.getTargetLateral())){
            result.setTargetLateral(8888.8);
        }
        return result;       
    }
    
    public static boolean canDisplay(double d){
        if ( Double.isNaN(d) ){
            return false;
        }
        if ( Double.isInfinite(d)){
            return false;
        }
        return true;
    }
}

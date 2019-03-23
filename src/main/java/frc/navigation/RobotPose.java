package frc.navigation;

/**
 *
 * @author dcowden
 */
public class RobotPose {

    public double getFieldAngle() {
        return fieldAngle;
    }

    public void setFieldAngle(double fieldAngle) {
        this.fieldAngle = fieldAngle;
    }

    public double getTargetLateral() {
        return targetLateral;
    }

    public void setTargetLateral(double targetLateral) {
        this.targetLateral = targetLateral;
    }

    public double getTargetDistance() {
        return targetDistance;
    }

    public void setTargetDistance(double targetDistance) {
        this.targetDistance = targetDistance;
    }
    
  private double fieldAngle;
  private double targetLateral;
  private double targetDistance;    
    
    
}

package frc.robot;

import edu.wpi.first.wpilibj.Preferences;

/**
 *
 * @author dcowden
 */
public class RobotPreferences {
  
    public static RobotPreferences loadPreferences(){
        RobotPreferences rp  = new RobotPreferences();
        Preferences p = Preferences.getInstance();
        
        //load values from roborio, using defaults in this class as fallbacks
        rp.cameraFPS = (int)p.getLong("DriverCameraFPS", rp.cameraFPS);
        rp.cameraWidth = (int)p.getLong("DriverCameraWidth", rp.cameraWidth);
        rp.cameraHeight = (int)p.getLong("DriverCameraHeight", rp.cameraHeight);
        rp.debug = p.getBoolean("Debugging", rp.debug);
        rp.enableLineSensors = p.getBoolean("EnableLineSensors",rp.enableLineSensors);
        rp.enableVision = p.getBoolean("EnableVision",rp.enableVision);
        rp.visionAlignThreshold = p.getDouble("VisionAlignThreshold", rp.visionAlignThreshold);
        return rp;
    }    

    public int getCameraWidth() {
        return cameraWidth;
    }

    public int getCameraHeight() {
        return cameraHeight;
    }

    public int getCameraFPS() {
        return cameraFPS;
    }

    public boolean isDebug() {
        return debug;
    }

    public boolean isEnableLineSensors() {
        return enableLineSensors;
    }

    
    public double getVisionAlignThreshold() {
        return visionAlignThreshold;
    }
    
    private int cameraWidth = 320;
    private int cameraHeight = 240;
    private int cameraFPS = 60;
    private boolean debug = false;
    private boolean enableLineSensors = false;
    private boolean enableVision = true;

    public boolean isEnableVision() {
        return enableVision;
    }
    private double visionAlignThreshold = 1.5;

    @Override
    public String toString() {
        return "RobotPreferences{" + "cameraWidth=" + cameraWidth + ", cameraHeight=" + cameraHeight + ", cameraFPS=" + cameraFPS + ", debug=" + debug + ", enableLineSensors=" + enableLineSensors + ", enableVision=" + enableVision + ", visionAlignThreshold=" + visionAlignThreshold + '}';
    }    

}

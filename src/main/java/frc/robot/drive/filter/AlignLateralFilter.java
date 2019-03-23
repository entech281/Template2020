package frc.robot.drive.filter;

import frc.logging.Logger;
import frc.logging.Logging;
import frc.pid.BangBangController;
import frc.pid.Controller;
import frc.pid.CappedLinearControl;import frc.robot.drive.DriveInput;
;

/**
 * Add your docs here.
 */
public class AlignLateralFilter extends DriveFilter  {

  public static final double ALIGN_THRESHOLD_INCHES=0.1;
  private Controller cappedLinear = new CappedLinearControl(
          ALIGN_THRESHOLD_INCHES,10, 0.2, 0.75);
  
  public AlignLateralFilter() {
    super(false);
  }

  @Override
  public DriveInput doFilter(DriveInput input) {
           
    double x_js = cappedLinear.getOutput(input.getTargetLateral(),0.0);

    return new DriveInput(x_js, input.getY(), 
            input.getZ(), 
            0.0, 
            input.getTargetDistance(), 
            input.getTargetLateral());
  }
}
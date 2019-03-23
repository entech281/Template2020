package frc.pid;

/**
 *
 * @author dcowden
 */
public class BangBangController implements Controller{
    
    private double threshold;
    private double onOutput;
    
    public BangBangController( double threshold, double onOutput){
        this.threshold = threshold;
        this.onOutput = onOutput;
    }
    
    public double getOutput( double actual, double desired){
        double delta = (actual - desired);
        if ( delta > threshold){
            return onOutput;
        }
        else if ( delta < -threshold){
            return -onOutput;
        }
        return 0.0;
    }

}

package frc.pid;

/**
 *
 * @author dcowden
 */
public interface Controller {
    public double getOutput(double actual, double setpoint);
}

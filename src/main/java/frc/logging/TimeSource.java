package frc.logging;


public interface TimeSource {
    double getSystemTime();

    void resetClock();

    double getElapsedSeconds();
}

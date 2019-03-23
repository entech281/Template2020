package frc.robot.subsystems;

import frc.logging.DataLoggerFactory;
import frc.logging.TimeSource;
import frc.navigation.Navigation;
import frc.oi.OperatorInput;
import frc.robot.RobotPreferences;
import frc.util.timer.TimeTracker;


public abstract class BaseSubsystem  {
  
  public final TimeTracker periodicStopWatch = new TimeTracker();

  protected DataLoggerFactory logFactory;
  protected Navigation navigation;
  protected String name;
  protected TimeSource timeSource;

  public BaseSubsystem(String name, Navigation navigation, DataLoggerFactory logFactory, TimeSource timeSource ) {
     this.name= name;
     this.navigation = navigation;
     this.logFactory = logFactory;
     this.timeSource = timeSource;

  }


  public abstract void robotInit(RobotPreferences preferences);
  public abstract void autoInit(String robotGameData);
  public abstract void telopInit();
  public abstract void disabledInit();
  public abstract void testInit();
  
  
  public abstract void teleopPeriodic( OperatorInput operatorInput);
  public abstract void autoPeriodic();
  public abstract void disabledPeriodic();
  public abstract void testPeriodic();


}


/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.oi;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;
import frc.logging.SmartDashboardLogger;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.AlignWithRocket;
import frc.robot.commands.AlignWithRocketOff;
import frc.robot.commands.AlignWithTarget;
import frc.robot.commands.AlignWithTargetOff;
import frc.robot.commands.ArmsDeploy;
import frc.robot.commands.ArmsRelease;
import frc.robot.commands.ArmsSqueeze;
import frc.robot.commands.CancelSquareUpCommand;
import frc.robot.commands.ReverseDeploy;
import frc.robot.commands.FlipBackward;
import frc.robot.commands.FlipForward;
import frc.robot.commands.FlipStop;
import frc.robot.commands.HatchExtend;
import frc.robot.commands.HatchRetract;
import frc.robot.commands.NudgeLeft;
import frc.robot.commands.NudgeRight;
import frc.robot.commands.SquareUpCommand;
import frc.robot.commands.ToggleFieldAbsolute;
import frc.robot.commands.TwistOff;
import frc.robot.commands.TwistOn;
import frc.robot.commands.ZeroYaw;
import frc.robot.drive.DriveInput;


/**
 * Has all the code for operator controls
 * Keep in mind that for testing, this cannot be instantiated.
 * @author dcowden
 */
public class OperatorInterface implements OperatorInputSource {
  private Robot robot;
  private Joystick driveStick;
  private Joystick gamePad;
  private Joystick operatorPanel;
  
  private POVButton povButton;
  private POVButton povButtonDown;
  
  // Robot Alignment
  private JoystickButton targetAlignCargoButton;
  private JoystickButton targetAlignRocketButton;
  private JoystickButton panelTargetAlignCargoButton;
  private JoystickButton panelTargetAlignRocketButton;

  // Arms Subsystem
  private JoystickButton armsDeployButton;
  private JoystickButton armsReverseButton;
  private JoystickButton armsSqueezeButton;
  private JoystickButton armsReleaseButton;

  private JoystickButton panelArmsDeployButton;
  private JoystickButton panelArmsSqueezeButton;
  private JoystickButton panelArmsReleaseButton;
  
  // Hatch Subsystem
  private JoystickButton hatchExtendButton;
  private JoystickButton hatchRetractButton;

  private JoystickButton panelHatchExtendButton;
  
  // Flip Subsystem
  private JoystickButton flipForwardButton;
  private JoystickButton flipBackwardButton;
  
  private JoystickButton panelFlipForwardButton;
  

  // Nudge Commands
  private JoystickButton nudgeLeftButton;
  private JoystickButton nudgeRightButton;
  
  // Twist Commands
  private JoystickButton twistButton;
  
  private JoystickButton zeroYawButton;
  
  // Field Absolute Toggle
  private JoystickButton fieldAbsoluteButton;
  
  public OperatorInterface(Robot robot){
    this.robot = robot;
    createButtons();
    createCommands();
  }


  protected void createButtons() {
    driveStick = new Joystick(RobotMap.DriveJoystick.PORT);
    
    povButton = new POVButton(driveStick,0);
    povButtonDown = new POVButton(driveStick,180);
    
    gamePad = new Joystick(RobotMap.GamePad.PORT);
    operatorPanel = new Joystick(RobotMap.OperatorPanel.PORT);
    // Target Alignment
    targetAlignCargoButton = new JoystickButton(gamePad, RobotMap.GamePad.Button.TARGET_ALIGN);
    targetAlignRocketButton = new JoystickButton(gamePad, RobotMap.GamePad.Button.TARGET_ALIGN_ROCKET);
  
    panelTargetAlignCargoButton = new JoystickButton(operatorPanel, RobotMap.OperatorPanel.Button.TARGET_ALIGN_CARGO);
    panelTargetAlignRocketButton = new JoystickButton(operatorPanel, RobotMap.OperatorPanel.Button.TARGET_ALIGN_ROCKET);
    // Arms Subsystem
    armsDeployButton = new JoystickButton(gamePad, RobotMap.GamePad.Button.ARMS_DEPLOY);
    armsSqueezeButton = new JoystickButton(gamePad, RobotMap.GamePad.Button.ARMS_SQUEEZE);
    armsReleaseButton = new JoystickButton(gamePad, RobotMap.GamePad.Button.ARMS_RELEASE);
    armsReverseButton = new JoystickButton(gamePad, RobotMap.GamePad.Button.ARMS_REVERSE_DEPLOY);

    // Hatch Subsystem
    hatchRetractButton = new JoystickButton(gamePad, RobotMap.GamePad.Button.HATCH_RETRACT);
    hatchExtendButton = new JoystickButton(gamePad, RobotMap.GamePad.Button.HATCH_EXTEND);
  
    // Flip Subsystem
    flipForwardButton = new JoystickButton(gamePad, RobotMap.GamePad.Button.FLIP_FORWARD);
    flipBackwardButton = new JoystickButton(gamePad, RobotMap.GamePad.Button.FLIP_BACKWARD);       
    
  
    // Arms Subsystem
    panelArmsDeployButton = new JoystickButton(operatorPanel, RobotMap.OperatorPanel.Button.ARMS_DEPLOY);
    panelArmsSqueezeButton = new JoystickButton(operatorPanel, RobotMap.OperatorPanel.Button.ARMS_SQUEEZE);
    panelArmsReleaseButton = new JoystickButton(operatorPanel, RobotMap.OperatorPanel.Button.ARMS_RELEASE);

    // Hatch Subsystem
 
    panelHatchExtendButton = new JoystickButton(operatorPanel, RobotMap.OperatorPanel.Button.HATCH_EXTEND);
  
    // Flip Subsystem
    panelFlipForwardButton = new JoystickButton(operatorPanel, RobotMap.OperatorPanel.Button.FLIP_FORWARD);

    // Nudge Commands
    nudgeLeftButton = new JoystickButton(driveStick, RobotMap.DriveJoystick.Button.NUDGE_LEFT);   
    nudgeRightButton = new JoystickButton(driveStick, RobotMap.DriveJoystick.Button.NUDGE_RIGHT);

    // Twist Commands
    twistButton = new JoystickButton(driveStick, RobotMap.DriveJoystick.Button.ALLOW_TWIST);
    
    zeroYawButton = new JoystickButton(driveStick, RobotMap.DriveJoystick.Button.ZERO_YAW);

    // Field Absolute Toggle
    fieldAbsoluteButton = new JoystickButton(driveStick, RobotMap.DriveJoystick.Button.FIELD_ABSOLUTE); 
    
    
  }
  
  protected void createCommands() { 
    // Target Align
    targetAlignCargoButton.whenPressed(new AlignWithTarget(this.robot));
    targetAlignCargoButton.whenReleased(new AlignWithTargetOff(this.robot));

    targetAlignRocketButton.whenPressed(new AlignWithRocket(this.robot));
    targetAlignRocketButton.whenReleased(new AlignWithRocketOff(this.robot));

    panelTargetAlignCargoButton.whenPressed(new AlignWithTarget(this.robot));
    panelTargetAlignCargoButton.whenReleased(new AlignWithTargetOff(this.robot));

    panelTargetAlignRocketButton.whenPressed(new AlignWithRocket(this.robot));
    panelTargetAlignRocketButton.whenReleased(new AlignWithRocketOff(this.robot));

    povButton.whenPressed(
            new SquareUpCommand(this.robot.getNavXSubsystem(),this.robot.getDriveSubsystem(),
            SquareUpCommand.LockOption.STRAIGHT));
    povButton.whenReleased(new CancelSquareUpCommand(this.robot.getDriveSubsystem()) );
    
    povButtonDown.whenPressed(
            new SquareUpCommand(this.robot.getNavXSubsystem(),this.robot.getDriveSubsystem(),
            SquareUpCommand.LockOption.ANGLED));
    
    povButtonDown.whenReleased(new CancelSquareUpCommand(this.robot.getDriveSubsystem()) );
    
    // Arms Subsystem
    armsDeployButton.whenPressed(new ArmsDeploy(this.robot.getArmsSubsystem()));
    armsSqueezeButton.whenPressed(new ArmsSqueeze(this.robot.getArmsSubsystem()));
    armsReleaseButton.whenPressed(new ArmsRelease(this.robot.getArmsSubsystem()));
    armsReverseButton.whenPressed(new ReverseDeploy(this.robot.getArmsSubsystem()));
    panelArmsDeployButton.whenPressed(new ArmsDeploy(this.robot.getArmsSubsystem()));
    panelArmsSqueezeButton.whenPressed(new ArmsSqueeze(this.robot.getArmsSubsystem()));
    panelArmsReleaseButton.whenPressed(new ArmsRelease(this.robot.getArmsSubsystem()));
    
    // Hatch Subsystem
    
    
    hatchRetractButton.whenPressed(new HatchRetract(this.robot.getHatchSubsystem()));
    hatchExtendButton.whenPressed(new HatchExtend(this.robot.getHatchSubsystem()));
    panelHatchExtendButton.whenPressed(new HatchExtend(this.robot.getHatchSubsystem()));
    panelHatchExtendButton.whenReleased(new HatchRetract(this.robot.getHatchSubsystem()));
    
    // Flip Subsystem
    flipForwardButton.whileHeld(new FlipForward(this.robot.getFlipSubsystem()));
    flipForwardButton.whenReleased(new FlipStop(this.robot.getFlipSubsystem()));
    flipBackwardButton.whileHeld(new FlipBackward(this.robot.getFlipSubsystem()));
    flipBackwardButton.whenReleased(new FlipStop(this.robot.getFlipSubsystem()));

    panelFlipForwardButton.whenReleased(new FlipStop(this.robot.getFlipSubsystem()));
    panelFlipForwardButton.whileHeld(new FlipBackward(this.robot.getFlipSubsystem()));

    // Nudge Commands
    nudgeRightButton.whenPressed(new NudgeRight(this.robot.getDriveSubsystem()));
    nudgeLeftButton.whenPressed(new NudgeLeft(this.robot.getDriveSubsystem()));

    // Twist Commands
    twistButton.whenPressed(new TwistOn(this.robot.getDriveSubsystem()));
    twistButton.whenReleased(new TwistOff(this.robot.getDriveSubsystem()));

    zeroYawButton.whenPressed(new ZeroYaw(this.robot.getNavXSubsystem()));

    // Field Absolute Toggle
    fieldAbsoluteButton.toggleWhenPressed(new ToggleFieldAbsolute(this.robot.getDriveSubsystem()));
  } 

    @Override
    public OperatorInput getOperatorInput() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

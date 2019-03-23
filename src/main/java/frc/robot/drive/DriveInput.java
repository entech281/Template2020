/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.drive;

/**
 * Add your docs here.
 */
public class DriveInput {
  private double x,y,z;
 

  public DriveInput(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;

  }
  
  public double getX(){
    return x;
  }
    
  public double getY(){
    return y;
  }
    
  public double getZ(){
    return z;
  }

  public DriveInput copy(){
      return new DriveInput(this.x,this.y,this.z);
  }
}

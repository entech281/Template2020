/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.drive.filter;

/**
 * Add your docs here.
 */
public class SnapFilter {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private double nearest = 0.0;

  public double findNearestQuadrant(double angle) {
    if ( angle >= 337.5 && angle <= 22.5 ) {
      nearest = 0.0;
    }
    if ( angle > 22.5 && angle <= 67.5 ) {
      nearest = 45.0;
    }
    if ( angle > 67.5 && angle <= 112.5 ) {
      nearest = 90.0;
    }
    if ( angle > 112.5 && angle < 157.5 ) {
      nearest = 135.0;
    }
    if ( angle >= 157.5 && angle <= 202.5 ) {
      nearest = 180.0;
    }
    if ( angle > 202.5 && angle < 247.5 ) {
      nearest = 225.0;
    }
    if ( angle >= 247.5 && angle < 292.5 ) {
      nearest = 270.0;
    }
    if ( angle >= 292.5 && angle < 337.5 ) {
      nearest = 315.0;
    }

    return nearest;

    // Too advanced for our time!
    // return (Math.round(((angle+22.5)%360)/45)*45);
  }
}

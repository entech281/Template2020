/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import frc.robot.drive.filter.SnapFilter;

public class TestSnapFilter {
    @Test
    public void testThatRounderWorks(){
        double delta = 1e-15;

        SnapFilter snap = new SnapFilter();
        assertEquals(0.0, snap.findNearestQuadrant(337.5), delta);
        assertEquals(0.0, snap.findNearestQuadrant(22.5), delta);

        assertEquals(45.0, snap.findNearestQuadrant(67.5), delta);

        assertEquals(90.0, snap.findNearestQuadrant(112.5), delta);

        assertEquals(180.0, snap.findNearestQuadrant(157.5), delta);
        assertEquals(180.0, snap.findNearestQuadrant(202.5), delta);

        assertEquals(270.0, snap.findNearestQuadrant(247.5), delta);

        assertEquals(315.0, snap.findNearestQuadrant(292.5), delta);
    }
}
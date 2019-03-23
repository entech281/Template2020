/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.pid;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author dcowden
 */
public class TestBangBangController {
    
    @Test
    public void testBangBang(){
        Controller c = new BangBangController(1.5,5.0);
        
        assertEquals( c.getOutput(2.0, 0.0) , 5.0,0.01);
        assertEquals( c.getOutput(-2.0, 0.0) , -5.0,0.01);
        assertEquals( c.getOutput(0.0, 0.0) , 0.0,0.01);
        assertEquals( c.getOutput(1.0, 0.0) , 0.0,0.01);
        assertEquals( c.getOutput(-1.0, 0.0) , 0.0,0.01);
    }
}

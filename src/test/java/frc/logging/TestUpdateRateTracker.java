package frc.logging;

import frc.util.timer.UpdateRateTracker;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author dcowden
 */
public class TestUpdateRateTracker {
    
    protected UpdateRateTracker rt = new UpdateRateTracker(100);
    
    @Test
    public void testUpdateRateTracker() throws Exception{
        String KEY = "KEY";
        String KEY2 = "KEY2";
        
        assertTrue(rt.shouldUpdate(KEY));
        assertFalse(rt.shouldUpdate(KEY));
        
        Thread.sleep(80);
        
        assertFalse(rt.shouldUpdate(KEY));
        assertTrue(rt.shouldUpdate(KEY2));
        
        Thread.sleep(40);
        assertTrue(rt.shouldUpdate(KEY));
        
    }
}

package frc.util.timer;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author dcowden
 */
public class UpdateRateTracker {
    
    private long updateIntervalMillis = 0;
    
    private Map<String,Long> tracker = new HashMap<>();
    
    public UpdateRateTracker(long updateIntervalMillis){
        this.updateIntervalMillis = updateIntervalMillis;
    }
    
    public boolean shouldUpdate(String key){
        Long lastUpdated = tracker.get(key);
        long currentTime = System.currentTimeMillis();
        
        boolean shouldUpdate = false;
        
        if ( lastUpdated == null ){
            shouldUpdate = true;
            tracker.put(key, currentTime);
        }
        else{
            long updateTime = lastUpdated + updateIntervalMillis;
            if ( currentTime > updateTime){
                shouldUpdate = true;
                tracker.put(key, currentTime);
            }
        }
        return shouldUpdate;
    }
    
}

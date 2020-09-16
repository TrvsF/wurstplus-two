package me.travis.wurstplus.wurstplustwo.event.events;

import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;

public class WurstplusEventSetupFog extends WurstplusEventCancellable {
    
    public int start_coords;
    public float partial_ticks;

    public WurstplusEventSetupFog(int coords, float ticks) {
        start_coords = coords;
        partial_ticks = ticks;        
    }

}
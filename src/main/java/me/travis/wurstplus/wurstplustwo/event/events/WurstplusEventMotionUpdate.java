package me.travis.wurstplus.wurstplustwo.event.events;

import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;

public class WurstplusEventMotionUpdate extends WurstplusEventCancellable {

    public int stage;

    public WurstplusEventMotionUpdate(int stage) {
        super();
        this.stage = stage;
    }
    
}
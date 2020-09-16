package me.travis.wurstplus.wurstplustwo.event.events;

import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;
import net.minecraft.entity.Entity;

public class WurstplusEventEntityRemoved extends WurstplusEventCancellable {
    
    private final Entity entity;

    public WurstplusEventEntityRemoved(Entity entity) {
        this.entity = entity;
    }

    public Entity get_entity() {
        return this.entity;
    }

}
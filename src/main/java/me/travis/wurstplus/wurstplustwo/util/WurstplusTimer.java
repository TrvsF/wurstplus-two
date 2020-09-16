package me.travis.wurstplus.wurstplustwo.util;

public class WurstplusTimer {

    private long time;

    public WurstplusTimer() {
        this.time = -1L;
    }

    public boolean passed(final long ms) {
        return this.getTime(System.nanoTime() - this.time) >= ms;
    }

    public void reset() {
        this.time = System.nanoTime();
    }

    public long getTime(final long time) {
        return time / 1000000L;
    }

}
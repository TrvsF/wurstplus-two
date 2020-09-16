package me.travis.wurstplus.wurstplustwo.util;

public class WurstplusPair<T, S> {
    T key;
    S value;

    public WurstplusPair(T key, S value) {
        this.key = key;
        this.value = value;
    }

    public T getKey() {
        return key;
    }

    public S getValue() {
        return value;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public void setValue(S value) {
        this.value = value;
    }
}

package com.citicbank.async;

/**
 * 事件类型
 * Created by FIN on 2016/12/12.
 */
public enum EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3);

    private int value;

    EventType(int value) {
        this.value=value;
    }

    public int getValue(){
        return value;
    }
}

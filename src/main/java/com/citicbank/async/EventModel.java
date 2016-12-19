package com.citicbank.async;

import java.util.HashMap;
import java.util.Map;

/**
 * 事件模型
 * Created by FIN on 2016/12/12.
 */
public class EventModel {
    private EventType type;
    private int actorId;
    private int entityId;
    private int entityOwnerId;
    //信息
    private Map<String,String> exts=new HashMap<>();

    public int getTicketId() {
        return ticketId;
    }

    public EventModel setTicketId(int ticketId) {
        this.ticketId = ticketId;
        return this;
    }

    public long getTicketCount() {
        return ticketCount;
    }

    public EventModel setTicketCount(long ticketCount) {
        this.ticketCount = ticketCount;
        return this;
    }

    private int ticketId;
    private long ticketCount;

    public String getExt(String key){
        return exts.get(key);
    }
    public EventModel setExt(String key,String value){
        exts.put(key,value);
        return this;
    }

    public EventModel(EventType type){
        this.type=type;
    }

    public EventModel(){

    }

    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }



    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public void setExts(Map<String, String> exts) {
        this.exts = exts;
    }
}

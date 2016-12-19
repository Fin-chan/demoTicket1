package com.citicbank.async;

import com.alibaba.fastjson.JSONObject;
import com.citicbank.util.JedisAdapter;
import com.citicbank.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 事件消费者
 * 作用：1.映射表 2.分发事件
 * Created by FIN on 2016/12/12.
 */
@Service
public class EventConsumer implements InitializingBean,ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
    private ApplicationContext applicationContext;
    private Map<EventType,List<EventHandler>> config=new HashMap<>();

    @Autowired
    JedisAdapter jedisAdapter;
    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String,EventHandler> beans=applicationContext.getBeansOfType(EventHandler.class);
        if(beans!=null){
            for(Map.Entry<String,EventHandler> entry:beans.entrySet()){
                List<EventType> eventTypes=entry.getValue().getSupportEventTypes();
                for(EventType type:eventTypes){
                    if(!config.containsKey(type)){
                        config.put(type,new ArrayList<EventHandler>());
                    }
                    config.get(type).add(entry.getValue());
                }
            }
        }

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    String key= RedisKeyUtil.getEventQueueKey();
                    List<String> events=jedisAdapter.brpop(0,key);
                    for(String message:events){
                        if(message.equals(key)){
                            continue;
                        }
                        //反序列化成Java类
                        EventModel eventModel= JSONObject.parseObject(message,EventModel.class);
                        if(!config.containsKey(eventModel.getType())){
                            logger.error("不能识别的事件");
                            continue;
                        }
                        for(EventHandler handler:config.get(eventModel.getType())){
                            handler.doHandle(eventModel);
                        }
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}

package com.citicbank.async;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * 事件消费者
 * 作用：1.映射表 2.分发事件
 * Created by FIN on 2016/12/12.
 */
@Service
public class EventConsumer implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}

package com.citicbank.async;

import java.util.List;

/** 操作接口，方便扩展
 * Created by FIN on 2016/12/12.
 */
public interface EventHandler {
    /**
     * 处理model
     * @param model
     */
    void doHandle(EventModel model);

    /**
     * 绑定某些不同类型的事件
     * @return
     */
    List<EventType> getSupportEventTypes();
}

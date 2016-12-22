package lab.io.rush.async;

import com.alibaba.fastjson.JSONObject;
import lab.io.rush.util.JedisAdapter;
import lab.io.rush.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 生产者
 * 作用：1.序列化 2.入队
 * Created by FIN on 2016/12/12.
 */
@Service
public class EventProducer {
    @Autowired
    JedisAdapter jedisAdapter;

    /**
     *
     * @param model
     * @return
     */
    public boolean fireEvent(EventModel model){
        try {
            String json = JSONObject.toJSONString(model);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, json);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}

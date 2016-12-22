package lab.io.rush.service;

import lab.io.rush.util.JedisAdapter;
import lab.io.rush.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by FIN on 2016/12/15.
 */
@Service
public class LikeService {
    @Autowired
    JedisAdapter jedisAdapter;

    public long like(int ticketId){
        String likeKey= RedisKeyUtil.getLikeKey(ticketId);
        return jedisAdapter.decr(likeKey);
    }

    public long getLikeKey(int ticketId){
        String likeKey=RedisKeyUtil.getLikeKey(ticketId);
        long ticketCount= Long.parseLong(jedisAdapter.get(likeKey));
        return ticketCount;
    }
}

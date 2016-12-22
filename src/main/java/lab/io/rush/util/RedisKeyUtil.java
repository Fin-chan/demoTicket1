package lab.io.rush.util;

/**
 * Created by FIN on 2016/12/12.
 */
public class RedisKeyUtil {
    private static String SPLIT=":";
    private static String BIZ_EVENT="EVENT";
    private static String BIZ_LIKE="LIKE";
    private static String BIZ_TICKET="TICKET-ID";

    /**
     * 买票key
     * @param ticketId
     * @return
     */
    public static String getLikeKey(int ticketId){
        return BIZ_LIKE+SPLIT+BIZ_TICKET+SPLIT+String.valueOf(ticketId);
    }
    /**
     * 事件队列
     * @return
     */
    public static String getEventQueueKey(){
        return BIZ_EVENT;
    }
}

package lab.io.rush.async.handler;

import lab.io.rush.async.EventHandler;
import lab.io.rush.async.EventModel;
import lab.io.rush.async.EventType;
import lab.io.rush.service.TicketService;
import lab.io.rush.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 卖票handler
 * Created by FIN on 2016/12/12.
 */
@Component
public class LikeHandler implements EventHandler {
    @Autowired
    MailSender mailSender;
    @Autowired
    TicketService ticketService;

    /**
     * 处理什么事件
     * @param model
     */
    @Override
    public void doHandle(EventModel model) {
        System.out.println("Like");
        Map<String,Object> map=new HashMap<>();
        map.put("username",model.getExt("username"));
        map.put("ticketname",model.getExt("ticketname"));
        //异步
        ticketService.updateLikeCount(model.getTicketId(),model.getTicketCount());
        mailSender.sendWithHTMLTemplate(model.getExt("email"),"抢票通知","mails/welcome.html",map);
    }

    /**
     * 获取相关的事件触发类型
     * @return
     */
    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}

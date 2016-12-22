package lab.io.rush.controller;

import lab.io.rush.async.EventModel;
import lab.io.rush.async.EventProducer;
import lab.io.rush.async.EventType;
import lab.io.rush.model.HostHolder;
import lab.io.rush.model.Ticket;
import lab.io.rush.service.LikeService;
import lab.io.rush.service.TicketService;
import lab.io.rush.util.DemoUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 抢票
 * Created by FIN on 2016/12/10.
 */
@Controller
public class LikeController {
    private static final Logger logger = LoggerFactory.getLogger(LikeController.class);

    @Autowired
    TicketService ticketService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    EventProducer eventProducer;

    @Autowired
    LikeService likeService;
    @RequestMapping(path = {"/like"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String like(@Param("ticketId") int ticketId){
       try{
           Ticket ticket=ticketService.getById(ticketId);

           /*
            //原始数据库操作
            long Likecount=ticketService.selectLikeCount(ticketId)-1;
            ticketService.updateLikeCount(ticketId,Likecount);
            */
           Long Likecount=likeService.getLikeKey(ticketId);
           if(Likecount>0){
               Likecount= likeService.like(ticketId);

           }else{
               return DemoUtil.getJSONString(1,"票不够");
           }
            //异步任务开始执行，分别执行发邮件和更新后台数据库
           eventProducer.fireEvent(new EventModel(EventType.LIKE).setEntityOwnerId(ticket.getUser_id())
                    .setActorId(hostHolder.getUser().getId()).setEntityId(ticketId).setExt("username",hostHolder.getUser().getName())
            .setExt("email",hostHolder.getUser().getName()).setExt("ticketname",ticket.getTitle()).setTicketId(ticketId).setTicketCount(Likecount));

            return DemoUtil.getJSONString(0,String.valueOf(Likecount));
        }catch (Exception e){
           logger.error("抢票异常"+e.getMessage());
            return DemoUtil.getJSONString(1,"抢票异常");
        }

    }
}

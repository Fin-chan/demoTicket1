package lab.io.rush.controller;

import lab.io.rush.model.HostHolder;
import lab.io.rush.model.Ticket;
import lab.io.rush.model.ViewObject;
import lab.io.rush.service.TicketService;
import lab.io.rush.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FIN on 2016/12/8.
 */
@Controller
public class HomeController {
    @Autowired
    TicketService ticketService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    /**
     * 获取所有票
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    private List<ViewObject> getTicket(int userId,int offset,int limit){
        List<Ticket> ticketList=ticketService.getLastTicket(userId,offset,limit);

        List<ViewObject> vos=new ArrayList<>();
        for(Ticket ticket:ticketList){
            ViewObject vo=new ViewObject();
            vo.set("ticket",ticket);
            vo.set("user", userService.getUser(ticket.getUser_id()));
            vos.add(vo);
        }
        return vos;
    }

    /**
     * 首页
     * @param model
     * @param pop
     * @return
     */
    @RequestMapping(path = {"/","/index"}, method = {RequestMethod.POST,RequestMethod.GET})
    public String index(Model model,@RequestParam(value = "pop", defaultValue= "0") int pop){
        model.addAttribute("vos",getTicket(0,0,11));
        if (hostHolder.getUser() != null) {
            pop = 0;
        }
        model.addAttribute("pop", pop);
        return "home";
    }
}

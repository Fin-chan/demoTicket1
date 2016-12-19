package com.citicbank.controller;

import com.citicbank.model.Ticket;
import com.citicbank.model.ViewObject;
import com.citicbank.service.TicketService;
import com.citicbank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(path = {"/","/index"}, method = {RequestMethod.POST,RequestMethod.GET})
    public String index(Model model){
        model.addAttribute("vos",getTicket(0,0,10));
        return "home";
    }
}

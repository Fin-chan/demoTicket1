package com.citicbank;

import com.citicbank.dao.LoginTicketDAO;
import com.citicbank.dao.TicketDAO;
import com.citicbank.dao.UserDAO;
import com.citicbank.model.LoginTicket;
import com.citicbank.model.Ticket;
import com.citicbank.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@Sql("/init-schema.sql")
public class InitDatabaseTests {
    @Autowired
    UserDAO userDAO;

    @Autowired
    TicketDAO ticketDAO;

    @Autowired
    LoginTicketDAO loginTicketDAO;

    /**
     * DAO层的接口测试
     */
    @Test
    public void intiData(){
        for (int i=0;i<11;i++){
            User user=new User();
            user.setName(String.format("USER%d",i));
            user.setSalt("");
            user.setPassword("");
            user.setUser_open_id("");
            userDAO.addUser(user);


            Ticket ticket=new Ticket();
            ticket.setTitle(String.format("TICKET%d",i));
            ticket.setLikecount(i+1);
            ticket.setUser_id(i+1);
            ticketDAO.addTicket(ticket);

            Date date=new Date();
            LoginTicket loginTicket=new LoginTicket();
            loginTicket.setStatus(0);
            loginTicket.setTicket(String.format("TICKET%d",i));
            loginTicket.setExpired(date);
            loginTicket.setUserId(i+1);

            loginTicketDAO.addTicket(loginTicket);

        }
        Assert.assertEquals(1,loginTicketDAO.selectByTicket("TICKET0").getId());

    }
}

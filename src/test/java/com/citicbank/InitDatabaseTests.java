package com.citicbank;

import com.citicbank.dao.LoginTicketDAO;
import com.citicbank.dao.TicketDAO;
import com.citicbank.dao.UserDAO;
import com.citicbank.model.LoginTicket;
import com.citicbank.model.Ticket;
import com.citicbank.model.User;
import com.citicbank.util.JedisAdapter;
import com.citicbank.util.RedisKeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * 数据库层单元测试
 */
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

    @Autowired
    JedisAdapter jedisAdapter;
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

            ticket.setLikecount(i*10);
            ticket.setTitle(String.format("TICKET%d",i+1));
            ticket.setUser_id(i+1);
            jedisAdapter.set(RedisKeyUtil.getLikeKey(i+1), String.valueOf(i*10));
            ticketDAO.addTicket(ticket);




            Date date=new Date();
            LoginTicket loginTicket=new LoginTicket();
            loginTicket.setStatus(0);
            loginTicket.setTicket(String.format("TICKET%d",i));
            loginTicket.setExpired(date);
            loginTicket.setUserId(i+1);

            loginTicketDAO.addTicket(loginTicket);



        }
        /*ticketDAO.updateLikeCount(1,30);

        Assert.assertEquals(30,ticketDAO.getById(1).getLikecount());
        Assert.assertEquals(30,ticketDAO.selectLikecount(1));

        Assert.assertEquals(1,loginTicketDAO.selectByTicket("TICKET0").getId());*/

    }
}

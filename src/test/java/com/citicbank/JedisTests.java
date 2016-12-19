package com.citicbank;

import com.citicbank.dao.LoginTicketDAO;
import com.citicbank.dao.TicketDAO;
import com.citicbank.dao.UserDAO;
import com.citicbank.model.LoginTicket;
import com.citicbank.model.Ticket;
import com.citicbank.model.User;
import com.citicbank.util.JedisAdapter;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import java.util.Date;

/**
 * redis层单元测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@Sql("/init-schema.sql")
public class JedisTests {
    @Autowired
    JedisAdapter jedisAdapter;

    /**
     * 缓存层的接口测试
     */
    @Test
    public void intiData(){
        User user=new User();
        user.setPassword("pwd");
        user.setName("user1");
        user.setSalt("salt");
        user.setUser_open_id("");
        jedisAdapter.setObject("user1xx",user);

        User u=jedisAdapter.getObject("user1xx",User.class);
        System.out.println(ToStringBuilder.reflectionToString(u));
    }
}

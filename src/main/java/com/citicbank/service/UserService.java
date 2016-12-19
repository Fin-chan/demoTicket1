package com.citicbank.service;

import com.citicbank.dao.LoginTicketDAO;
import com.citicbank.dao.TicketDAO;
import com.citicbank.dao.UserDAO;
import com.citicbank.model.LoginTicket;
import com.citicbank.model.User;
import com.citicbank.util.DemoUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by FIN on 2016/12/8.
 */
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;
    /**
     * 获取用户
     */
    public User getUser(int userid){
        return userDAO.selectByUserId(userid);
    }

    /**
     * 注册
     * @param name
     * @param password
     * @return
     */
    public Map<String,Object> register(String name,String password){
        Map<String,Object> map=new HashMap<>();
        if(StringUtils.isBlank(name)) {
            map.put("msgname", "用户名不能为空");
            return map;
        }

        if(StringUtils.isBlank(password)){
            map.put("msgpwd","密码不能为空");
            return map;
        }

        User user=userDAO.selectByUserName(name);
        if(user!=null){
            map.put("msgname","用户已注册");
            return map;
        }

        user=new User();
        user.setName(name);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setPassword(DemoUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);

        String ticket=addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    /**
     * 登录
     * @param name
     * @param password
     * @return
     */
    public Map<String,Object> login(String name,String password){
        Map<String,Object> map=new HashMap<>();
        if(StringUtils.isBlank(name)) {
            map.put("msgname", "用户名不能为空");
            return map;
        }

        if(StringUtils.isBlank(password)){
            map.put("msgpwd","密码不能为空");
            return map;
        }

        User user=userDAO.selectByUserName(name);
        if(user==null){
            map.put("msgname","用户名不存在");
        }
        if(!DemoUtil.MD5(password+user.getSalt()).equals(user.getPassword())){
            map.put("msgpwd","密码不正确");
        }

        String ticket=addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;

    }

    /**
     * 登出
     * @param ticket
     */
    public void logout(String ticket){
           loginTicketDAO.updateStatus(ticket,1);
        }
       private String addLoginTicket(int userId){
           LoginTicket loginTicket=new LoginTicket();
           loginTicket.setUserId(userId);
           Date date=new Date();
           date.setTime(date.getTime()+1000*3600*24);
           loginTicket.setExpired(date);
           loginTicket.setStatus(0);
           loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
           return loginTicket.getTicket();
       }
}

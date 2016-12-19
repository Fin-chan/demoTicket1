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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by FIN on 2016/12/8.
 */
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;

    private static final String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * email正则
     * @param email
     * @return
     */
    public static boolean checkEmail(String email){
        boolean flag=false;
        Pattern regex=Pattern.compile(check);
        Matcher matcher=regex.matcher(email);
        flag=matcher.matches();
        return flag;
    }

    /**
     * 根据id获取用户
     * @param userid
     * @return
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
        if(!checkEmail(name)){
            map.put("msgname","不符合邮箱格式");
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
            return map;
        }
        if(!DemoUtil.MD5(password+user.getSalt()).equals(user.getPassword())){
            map.put("msgpwd","密码不正确");
            return map;
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

    /**
     * 发出ticket并绑定user
     * @param userId
     * @return
     */
    private String addLoginTicket(int userId){
           LoginTicket loginTicket=new LoginTicket();
           loginTicket.setUserId(userId);
           Date date=new Date();
           date.setTime(date.getTime()+1000*3600*24);
           loginTicket.setExpired(date);
           loginTicket.setStatus(0);
           loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
           loginTicketDAO.addTicket(loginTicket);
           return loginTicket.getTicket();
    }
}

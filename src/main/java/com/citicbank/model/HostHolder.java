package com.citicbank.model;

import org.springframework.stereotype.Component;

/**
 * 线程本地变量保存用户信息 one by one
 * Created by FIN on 2016/12/10.
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users=new ThreadLocal<User>();

    public User getUser(){
        return users.get();
    }

    public void setUser(User user){
        users.set(user);
    }

    public void clear(){
        users.remove();
    }
}

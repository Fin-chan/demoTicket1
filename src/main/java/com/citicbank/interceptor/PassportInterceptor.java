package com.citicbank.interceptor;

import com.citicbank.dao.LoginTicketDAO;
import com.citicbank.dao.UserDAO;
import com.citicbank.model.HostHolder;
import com.citicbank.model.LoginTicket;
import com.citicbank.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 拦截器
 * Created by FIN on 2016/12/10.
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginTicketDAO loginTicketDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private HostHolder hostHolder;

    /**
     * controller之前
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String loginTicket=null;
        if(httpServletRequest.getCookies()!=null){
            for(Cookie cookie:httpServletRequest.getCookies()){
                if(cookie.getName().equals("ticket")){
                    loginTicket=cookie.getValue();
                    break;
                }
            }
        }

        if(loginTicket!=null){
            LoginTicket userTicket=loginTicketDAO.selectByTicket(loginTicket);
            if(userTicket==null||userTicket.getExpired().before(new Date())||userTicket.getStatus()!=0){
                return true;
            }
            //已经知道是哪个用户,保存用户信息传入controller,使用线程本地变量保存用户
            User user=userDAO.selectByUserId(userTicket.getUserId());
            hostHolder.setUser(user);

        }

        return true;
    }

    /**
     * 渲染之前
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
            if(modelAndView!=null&&hostHolder.getUser()!=null){
                modelAndView.addObject("user",hostHolder.getUser());
            }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
            hostHolder.clear();
    }
}

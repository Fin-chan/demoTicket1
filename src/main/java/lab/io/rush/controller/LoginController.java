package lab.io.rush.controller;

import lab.io.rush.service.UserService;
import lab.io.rush.util.DemoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by FIN on 2016/12/9.
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    /**
     * 注册接口
     * @param model
     * @param username
     * @param password
     * @param rememberme
     * @param response
     * @return
     */
    @RequestMapping(path = {"/reg/"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String reg(Model model, @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value = "rember",defaultValue = "0")int rememberme,
                      HttpServletResponse response){
        try {
            Map<String,Object> map=userService.register(username,password);
            if(map.containsKey("ticket")){
                Cookie cookie=new Cookie("ticket",map.get("ticket").toString());
                cookie.setPath("/");
                if(rememberme>0){
                    cookie.setMaxAge(3600*24*5);
                }
                response.addCookie(cookie);
                return DemoUtil.getJSONString(0,"注册成功");
            }else {
                return DemoUtil.getJSONString(1,map);
            }
        }catch (Exception e){
            logger.error("注册异常"+e.getMessage());
            return DemoUtil.getJSONString(1,"注册异常");
        }
    }

    /**
     * 登录接口
     * @param model
     * @param username
     * @param password
     * @param rememberme
     * @param response
     * @return
     */
    @RequestMapping(path = {"/login/"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String login(Model model, @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value = "rember",defaultValue = "0")int rememberme,
                        HttpServletResponse response
                     ){
        try {
            Map<String,Object> map=userService.login(username,password);
            if(map.containsKey("ticket")){
                Cookie cookie=new Cookie("ticket",map.get("ticket").toString());
                cookie.setPath("/");
                if(rememberme>0){
                    cookie.setMaxAge(3600*24*5);
                }
                response.addCookie(cookie);
                return DemoUtil.getJSONString(0,"登录成功");
            }else {
                return DemoUtil.getJSONString(1,map);
            }
        }catch (Exception e){
            logger.error("登录异常"+e.getMessage());
            return DemoUtil.getJSONString(1,"登录异常");
        }
    }

    /**
     * 登出
     * @param ticket
     * @return
     */
    @RequestMapping(path = {"/logout/"},method = {RequestMethod.GET,RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }
}
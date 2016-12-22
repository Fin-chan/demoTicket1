package lab.io.rush.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by FIN on 2016/12/7.
 */
//@Controller
public class IndexController {
    @RequestMapping("/")
    @ResponseBody
    public String index(){
        return "hello";
    }
}

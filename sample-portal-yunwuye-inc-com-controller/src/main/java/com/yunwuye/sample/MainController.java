package com.yunwuye.sample;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.yunwuye.sample.controller.BaseController;

/**
 *
 * @author Roy
 *
 * @date 2022年7月10日-下午6:53:16
 */
@Controller
public class MainController extends BaseController{

    @GetMapping ("/")
    public String index () {
        return "index";
    }

    @GetMapping ("/access_denied")
    public String notAuth () {
        return "access_denied";
    }

    @GetMapping ("/login")
    public String loginForm () {
        return "login";
    }

    @GetMapping ("/logout")
    public String logout () {
        return "login";
    }
}

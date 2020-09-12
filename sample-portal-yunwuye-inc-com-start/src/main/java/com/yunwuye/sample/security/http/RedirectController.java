package com.yunwuye.sample.security.http;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Roy
 *
 * @date 2020年5月4日-下午10:57:38
 */
@Controller
@RequestMapping("/http")
public class RedirectController {

    @RequestMapping("/redirect")
    public String gotoURL(@RequestParam String url){
        return "redirect"; // 安全跳转检测页去处理
    }

    @RequestMapping("/page")
    public String page(HttpServletRequest req, Model model){
        String from = req.getHeader("referer");
        model.addAttribute("from", from);
        return "security/http/page";
    }
}

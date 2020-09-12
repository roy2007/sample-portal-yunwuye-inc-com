package com.yunwuye.sample.security.json;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Roy
 *
 * @date 2020年5月4日-下午11:27:23
 */
@Controller
@RequestMapping("/jsonp")
public class JsonpController {

    @RequestMapping("/data")
    public Map<String, Object> data() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "springBoot");
        data.put("age", "5岁");
        data.put("html-code", "<html>");
        data.put("url", "http://www.baidu.com/?q=范冰冰,李冰冰");
        return data;
    }

    @RequestMapping("/rowData")
    public JsonpUser rowData() {
        JsonpUser data = new JsonpUser();
        data.setName("<script>alert(1)</script>");
        data.setSalary(9999);
        return data;
    }
}

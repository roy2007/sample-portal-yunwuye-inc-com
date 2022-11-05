package com.yunwuye.sample.config;

import org.springframework.context.annotation.Configuration;
import com.alibaba.dubbo.config.annotation.Reference;
import com.yunwuye.sample.service.StudentService;
import com.yunwuye.sample.service.UserService;


/**
 *
 * @author Roy
 *
 * @date 2020年7月5日-下午4:20:44
 */
@Configuration
public class DubboConfig {

  @Reference(group = "userService", version = "1.0", check = false)
  private UserService userService;

  @Reference(group = "studentService", version = "1.0", check = false)
  private StudentService studentService;
}

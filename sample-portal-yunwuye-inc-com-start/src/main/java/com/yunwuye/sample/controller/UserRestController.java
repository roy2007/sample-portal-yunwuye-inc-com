package com.yunwuye.sample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.yunwuye.sample.client.service.AccountUserService;
import com.yunwuye.sample.dto.AccountUserDTO;
import com.yunwuye.sample.result.Result;
import com.yunwuye.sample.security.SecurityUtils;


@RestController
@RequestMapping("/api")
public class UserRestController {

    @Reference (group = "accountUserService", version = "1.0", check = false)
    private AccountUserService accountUserService;

   @GetMapping("/user")
    public Result<AccountUserDTO> getActualUser () {
        String loginUserName = SecurityUtils.getCurrentUsername ().get ();
        return accountUserService.findByUsername (loginUserName);
   }
}

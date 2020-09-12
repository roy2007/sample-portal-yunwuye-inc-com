package com.yunwuye.sample.controller.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yunwuye.sample.common.base.result.Result;
import com.yunwuye.sample.common.util.ResultUtil;
import com.yunwuye.sample.controller.BaseController;
import com.yunwuye.sample.controller.account.vo.AccountUserVO;
import com.yunwuye.sample.dto.AccountUserDTO;
import com.yunwuye.sample.service.AccountUserService;
/**
 *
 * @author Roy
 *
 * @date 2020年8月15日-下午3:40:23
 */
@RestController
@RequestMapping("/account")
@EnableAutoConfiguration
public class AccountUserController extends BaseController {
  Logger logger = LoggerFactory.getLogger(AccountUserController.class);
  
  @Reference(group = "accountUserService", version = "1.0", check = false)
  private AccountUserService accountUserService;

  @GetMapping("/findById")
  public Result<AccountUserVO> findById(Long id) {
    Result<AccountUserDTO> findResult = accountUserService.findById(id);
    AccountUserDTO dto = findResult.getData();
    AccountUserVO vo = new AccountUserVO();
    BeanUtils.copyProperties(dto, vo);
    return ResultUtil.createSuccessResult(vo);
  }
}

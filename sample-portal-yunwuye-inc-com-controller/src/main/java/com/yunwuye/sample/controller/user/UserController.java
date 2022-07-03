package com.yunwuye.sample.controller.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yunwuye.sample.client.service.UserService;
import com.yunwuye.sample.controller.BaseController;
import com.yunwuye.sample.controller.user.vo.UserVO;
import com.yunwuye.sample.dto.UserDTO;
import com.yunwuye.sample.param.user.UserParam;
import com.yunwuye.sample.result.PageResult;
import com.yunwuye.sample.result.Result;
import com.yunwuye.sample.util.ListUtil;
import com.yunwuye.sample.util.ResultUtil;
import com.yunwuye.sample.validator.AclValidator;
import com.yunwuye.sample.validator.annotation.Validator;
import com.yunwuye.sample.validator.annotation.ValidatorConfig;

/**
 *
 * @author Roy
 *
 * @date 2020年5月10日-下午10:40:49
 */

@RestController
@RequestMapping("/user")
@EnableAutoConfiguration
public class UserController extends BaseController {
  Logger logger = LoggerFactory.getLogger(UserController.class);

  @Reference(group = "userService", version = "1.0", check = false)
  private UserService userService;

  @GetMapping("/findById")
  @Validator(@Validator.ValidateClass(validator = AclValidator.class))
  @ValidatorConfig(acls = { "UserController.getUserInfoJsonStr", "UserController.queryUserInfoJsonStr" }, empId = "10009")
  public Result<UserVO> getUserInfoJsonStr(Long id) {
    Result<UserVO> result = new Result<>();
    Result<UserDTO> findResult = userService.findById(id);
    if (findResult.getData() != null) {
      UserVO userVO = new UserVO();
      // TODO checke AspectControllerExceptionAOP exception
      // userVO = null;
      BeanUtils.copyProperties(findResult.getData(), userVO);
      logger.info("userVO : {}", userVO);
      return ResultUtil.createSuccessResult(userVO);
    }
    return result;
  }

  @GetMapping("/query")
  // @Validator(@Validator.ValidateClass(validator = AclValidator.class))
  // @ValidatorConfig(acls = { "UserController.getUserInfoJsonStr", "UserController.queryUserInfoJsonStr" }, empId =
  // "10009")
  public PageResult<List<UserVO>> getAllUserInfoJsonStr(UserParam userParam) {
    userParam = new UserParam();
    userParam.setAddress("北京安苑东坦克");
    PageResult<List<UserDTO>> queryResult = userService.queryByConditions(userParam);
    List<UserDTO> userDTOs = queryResult.getData();
    if (!CollectionUtils.isEmpty(userDTOs)) {
      List<UserVO> userVOs = ListUtil.copyProperties(userDTOs, UserVO.class);
      logger.info("userVO : {}", userVOs);
      int totalSize = queryResult.getTotalSize();
      int pageNo = queryResult.getPageNo();
      int pageSize = queryResult.getPageSize();
      return ResultUtil.createPageSuccess(totalSize, pageNo, pageSize, userVOs);
    }
    return ResultUtil.createPageSuccess();
  }
}

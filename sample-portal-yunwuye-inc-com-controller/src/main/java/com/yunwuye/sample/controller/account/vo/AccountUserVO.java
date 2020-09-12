package com.yunwuye.sample.controller.account.vo;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.yunwuye.sample.common.base.vo.BaseVO;

/**
 *
 * @author Roy
 *
 * @date 2020年8月15日-下午3:45:38
 */
@ToString
@Data
@EqualsAndHashCode(callSuper = false)
public class AccountUserVO extends BaseVO {

  /**
   * AccountUserVO.java -long
   */
  private static final long serialVersionUID = 4168059502954597738L;
  private String loginName;

  private String password;

  private String email;

  private String mobile;

  private Long empId;

  private String empName;

  private Long deptId;

  private Boolean lockStatus;

  private Date birthday;

  private Date lastLoginTime;

  private String gender;

  private String isTab;

  private String theme;

  private String avatar;

  private String description;
}

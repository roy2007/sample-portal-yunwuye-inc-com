package com.yunwuye.sample.controller.user.vo;

import java.util.Date;
import com.yunwuye.sample.vo.BaseVO;

/**
 *
 * @author Roy
 *
 * @date 2020年5月10日-下午10:47:26
 */
public class UserVO extends BaseVO {

  /**
   * UserVO.java -long
   */
  private static final long serialVersionUID = -6141041002655759588L;

  private String name;
  private Date birthday;
  private String sex;
  private String address;

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the birthday
   */
  public Date getBirthday() {
    return birthday;
  }

  /**
   * @param birthday
   *          the birthday to set
   */
  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  /**
   * @return the sex
   */
  public String getSex() {
    return sex;
  }

  /**
   * @param sex
   *          the sex to set
   */
  public void setSex(String sex) {
    this.sex = sex;
  }

  /**
   * @return the address
   */
  public String getAddress() {
    return address;
  }

  /**
   * @param address
   *          the address to set
   */
  public void setAddress(String address) {
    this.address = address;
  }
}

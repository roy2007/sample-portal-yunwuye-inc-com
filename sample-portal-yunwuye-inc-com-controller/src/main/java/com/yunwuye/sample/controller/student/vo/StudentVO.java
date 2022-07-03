package com.yunwuye.sample.controller.student.vo;

import com.yunwuye.sample.vo.BaseVO;

/**
 *
 * @author Roy
 *
 * @date 2020年6月27日-下午10:40:24
 */
public class StudentVO extends BaseVO {

  /**
   * StudentVO.java -long
   */
  private static final long serialVersionUID = 6894775516717122593L;
  /**
   * 姓名
   */
  private String name;
  /**
   * 年龄
   */
  private Integer age;

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
   * @return the age
   */
  public Integer getAge() {
    return age;
  }

  /**
   * @param age
   *          the age to set
   */
  public void setAge(Integer age) {
    this.age = age;
  }
}

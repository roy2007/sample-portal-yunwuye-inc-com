package com.yunwuye.sample.controller.student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yunwuye.sample.common.base.result.Result;
import com.yunwuye.sample.controller.BaseController;
import com.yunwuye.sample.controller.student.vo.StudentVO;
import com.yunwuye.sample.dto.StudentDTO;
import com.yunwuye.sample.service.StudentService;

/**
 *
 * @author Roy
 *
 * @date 2020年6月27日-下午10:42:32
 */
@RestController
@RequestMapping("/student")
@EnableAutoConfiguration
public class StudentController extends BaseController {
  Logger logger = LoggerFactory.getLogger(StudentController.class);

  @Reference(group = "studentService", version = "1.0", check = false)
  private StudentService studentService;

  @GetMapping("/find")
  public Result<StudentVO> findStudentById(Long id) {
    Result<StudentVO> result = new Result<>();
    StudentDTO dto = studentService.findById(id);
    StudentVO target = new StudentVO();
    BeanUtils.copyProperties(dto, target);
    result.setData(target);
    return result;
  }
}

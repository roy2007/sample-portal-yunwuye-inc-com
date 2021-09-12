package com.yunwuye.sample.controller.student;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping ("/student")
@EnableAutoConfiguration
public class StudentController extends BaseController{

    Logger                 logger = LoggerFactory.getLogger (StudentController.class);
    @Reference (group = "studentService", version = "1.0", check = false)
    private StudentService studentService;

    @GetMapping ("/find")
    public Result<StudentVO> findStudentById (Long id) {
        Result<StudentVO> result = new Result<> ();
        StudentDTO dto = studentService.findById (id);
        StudentVO target = new StudentVO ();
        BeanUtils.copyProperties (dto, target);
        result.setData (target);
        return result;
    }

    /**
     * 
     * @param isAsync 是否异步处理
     * @return
     */
    @GetMapping ("/batchUpdateById")
    public Result<Integer> batchUpdateStudentById (String isAsync) {
        if (StringUtils.isBlank (isAsync)) {
            isAsync = "0";
        }
        Result<Integer> result = new Result<> ();
        List<StudentDTO> dtos = new ArrayList<StudentDTO> ();
        StudentDTO dto1 = new StudentDTO ();
        dto1.setId (1L);
        dto1.setName ("tom");
        dto1.setAge (10);// sucess
        dtos.add (dto1);
        StudentDTO dto2 = new StudentDTO ();
        dto2.setId (2L);
        dto2.setName ("johe");
        dto2.setAge (10);
        dtos.add (dto2);
        StudentDTO dto3 = new StudentDTO ();
        dto3.setId (3L);
        dto3.setName ("roy");// sucess
        dto3.setAge (10);
        dtos.add (dto3);
        StudentDTO dto4 = new StudentDTO ();
        dto4.setId (4L);
        dto4.setName ("mary");
        dto4.setAge (10);
        dtos.add (dto4);
        StudentDTO dto5 = new StudentDTO ();
        dto5.setId (5L);
        dto5.setName ("saly");// sucess
        dto5.setAge (10);
        dtos.add (dto5);
        Integer count = 0;
        /**只要不等于0，表示是异步，如果等于0表示同步*/
        if (!"0".equals (isAsync)) {
            count = studentService.batchAsyncUpdateStudentById (dtos);
        } else {
            count = studentService.batchSyncUpdateStudentById (dtos);
        }
        result.setData (count);
        return result;
    }
}


package com.yunwuye.sample.validator;

import java.lang.annotation.Annotation;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.yunwuye.sample.common.validator.AbstractBaseValidator;
import com.yunwuye.sample.service.UserService;
import com.yunwuye.sample.validator.annotation.ValidatorConfig;

/**
 *
 * @author Roy
 *
 * @date 2020年6月23日-下午1:25:12
 */
@Slf4j
@Component
public class AclValidator extends AbstractBaseValidator<ValidatorConfig> {

    @Reference(group = "userService", version = "1.0", check = false)
    private UserService userService;

    @Override
    public Boolean validate(Annotation validatorConfig, String empId, Map<String, Object> nameWithValueMap) {
        log.info("validatorConfig: {}, nameWithValueMap: {} ", JSON.toJSONString(validatorConfig),
                JSON.toJSONString(nameWithValueMap));
        return Boolean.TRUE;
    }
}

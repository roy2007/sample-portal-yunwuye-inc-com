
package com.yunwuye.sample.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Roy
 *
 * @date 2020年6月24日-下午4:10:15
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface ValidatorConfig {

    /**
     * 员工号属性
     * 
     * @return
     */
    String empId() default "empId";

    /**
     * acl权限点定义字符串
     * 
     * @return
     */
    String[] acls() default {};

}

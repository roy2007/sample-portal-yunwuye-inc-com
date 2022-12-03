# sample-portal-yunwuye-inc-com
基于SpringBoot + JWT安全登录框架模板项目
登录后请求头需设置置：JWT_TOKEN: Bearer +JWTtoken值

未登录访问时后端将抛：- Access is denied (user is anonymous); redirecting to authentication entry point（访问被拒绝（用户是匿名的）；重定向到身份验证入口点）

# 入口：
## 1.先进行登录 http://localhost:8081/
用户名：admin, pw:admin, 
用户名：user,pw:password对应Master数据源对应表中没有此用户，所以无法登录,
用户名：disabled,pw:password用于测试禁用用户

## 2.用于测试服务URL

    登录API:http://localhost:8081/api/authenticate， 用户表：account_user、用户角色表：
    完整 调用流程 请参考`com.yunwuye.sample.controller.LoginRestController.authorize(LoginDto)
    => o.s.security.authentication.ProviderManager - Authentication attempt using org.springframework.security.authentication.dao.DaoAuthenticationProvider =>com.yunwuye.sample.security.UserModelDetailsService.loadUserByUsername(String)`
再通过dubbo调用对应Service服务查询库表中用户
验证存在用户后将生成安全用户，并附带查询相关用户角色信息   `com.yunwuye.sample.security.UserModelDetailsService.createSpringSecurityUser(String, AccountUserDTO)`

    登人员（无角色)都 可以访问：http://localhost:8081/student/find?id=1， 这个是分布式库查询操作
    登人员（有用户角色）都 可以访问：http://localhost:8081/api/person
    登人员（有管理员角色）都 可以访问：http://localhost:8081/api/person

## 3.登录druid数据连接池控制台
    URL: http://localhost:8080/druid/index.html
    登录用户名密码是通过代码中固定的值：com.yunwuye.sample.configuration.DruidConfiguration，
     servletRegistrationBean.addInitParameter("loginUsername", "root");
     servletRegistrationBean.addInitParameter("loginPassword", "123456");

 


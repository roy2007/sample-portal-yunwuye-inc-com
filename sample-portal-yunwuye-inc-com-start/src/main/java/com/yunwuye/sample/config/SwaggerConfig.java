package com.yunwuye.sample.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.yunwuye.sample.security.jwt.TokenProvider;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author Roy
 *
 * @date 2022年7月3日-上午11:14:59
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig{

    @Autowired
    private TokenProvider tokenProvider;

    @Bean
    public Docket api () {
        return new Docket (DocumentationType.SWAGGER_2)//
                        .select ()//
                        .apis (RequestHandlerSelectors.basePackage ("com.yunwuye.sample.controller"))// RequestHandlerSelectors.any()
                        .paths (PathSelectors.any ())// Predicates.not(PathSelectors.regex("/error"))
                        .build ()//
                        .apiInfo (metadata ())//
                        .useDefaultResponseMessages (false)//
                        .securitySchemes (Collections.singletonList (apiKey ()))
                        .securityContexts (Collections.singletonList (securityContext ()))
                        // .tags (new Tag ("users", "Operations about users"))//
                        .genericModelSubstitutes (Optional.class);
    }

    private ApiInfo metadata () {
        return new ApiInfoBuilder ()//
                        .title ("JSON Web Token 令牌认证 API")//
                        .description ("这是一个示例 JWT 身份验证服务。 您可以在 [https://jwt.io/](https://jwt.io/)上找到有关 JWT 的更多信息。 "
                                        + "对于此示例，您可以使用 `admin` 或 `client` 用户（密码分别为 admin 和 client）来测试授权过滤器。"
                                        + " 成功登录并获得token后， 点击右上方按钮`Authorize` ，引入前缀 \"Bearer \".")//
                        .version ("1.0.0")//
                        .license ("MIT License").licenseUrl ("http://opensource.org/licenses/MIT")//
                        .contact (new Contact (null, null, "yunwuyeDeveloper@gmail.com"))//
                        .build ();
    }

    private ApiKey apiKey () {
        String authorizeHeaderKey = tokenProvider.getAuthorizeHeaderKey ();
        return new ApiKey (authorizeHeaderKey, authorizeHeaderKey, "header");
    }

    private SecurityContext securityContext () {
        return SecurityContext.builder ()
                        .securityReferences (defaultAuth ())
                        .forPaths (PathSelectors.any ())
                        .build ();
    }

    private List<SecurityReference> defaultAuth () {
        AuthorizationScope authorizationScope = new AuthorizationScope ("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList (new SecurityReference (tokenProvider.getAuthorizeHeaderKey (), authorizationScopes));
    }
}

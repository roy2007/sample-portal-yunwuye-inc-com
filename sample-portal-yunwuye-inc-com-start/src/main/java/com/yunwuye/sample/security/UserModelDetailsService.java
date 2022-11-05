package com.yunwuye.sample.security;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.yunwuye.sample.common.base.dto.RoleDTO;
import com.yunwuye.sample.common.base.result.Result;
import com.yunwuye.sample.dto.AccountUserDTO;
import com.yunwuye.sample.service.AccountUserService;

/**
 * Authenticate a user from the database.
 */
@Component ("userDetailsService")
public class UserModelDetailsService implements UserDetailsService{

    private final Logger       log = LoggerFactory.getLogger (UserModelDetailsService.class);

    @Reference (group = "accountUserService", version = "1.0", check = false)
    private AccountUserService accountUserService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername (final String loginUserName) {
        log.debug ("Authenticating user '{}'", loginUserName);
        if (new EmailValidator ().isValid (loginUserName, null)) {
            Result<AccountUserDTO> result = accountUserService.findOneWithAuthoritiesByEmailIgnoreCase (loginUserName);
            Optional<AccountUserDTO> optional = Optional.of (result.getData ());
            return optional.map (user -> createSpringSecurityUser (loginUserName, user))
                            .orElseThrow ( () -> new UsernameNotFoundException (
                                            "User with LoginUserName " + loginUserName
                                                            + " was not found in the database"));
        }
        String lowercaseLoginUserName = loginUserName.toLowerCase (Locale.ENGLISH);
        Result<AccountUserDTO> result = accountUserService.findOneWithAuthoritiesByUsername (lowercaseLoginUserName);
        Optional<AccountUserDTO> optional = Optional.of (result.getData ());
        return optional.map (user -> createSpringSecurityUser (lowercaseLoginUserName, user))
                        .orElseThrow ( () -> new UsernameNotFoundException (
                                        "User " + lowercaseLoginUserName + " was not found in the database"));
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser (String lowercaseLoginUserName,
                    AccountUserDTO user) {
        if (user.getLockStatus ()) {
            throw new UserNotActivatedException ("User " + lowercaseLoginUserName + " was not activated");
        }
        if (CollectionUtils.isEmpty (user.getRoles ())) {
            RoleDTO roleDTO = new RoleDTO ();
            roleDTO.setIdentificationCode ("ANYBODY");
            roleDTO.setCnName ("只要登录，任何人都 可以访问 ");
            roleDTO.setRiskLevel (99);
            user.setRoles (Lists.newArrayList (roleDTO));
        }
        List<GrantedAuthority> grantedAuthorities = user.getRoles ().stream ()
                        .map (role -> new SimpleGrantedAuthority (role.getIdentificationCode ()))
                        .collect (Collectors.toList ());
        return new org.springframework.security.core.userdetails.User (user.getLoginName (),
                        user.getPassword (),
                        grantedAuthorities);
    }
}

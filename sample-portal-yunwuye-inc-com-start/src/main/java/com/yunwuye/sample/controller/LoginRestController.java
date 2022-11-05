package com.yunwuye.sample.controller;

import javax.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yunwuye.sample.controller.dto.LoginDto;
import com.yunwuye.sample.security.jwt.TokenProvider;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class LoginRestController {

   private final TokenProvider tokenProvider;

   private final AuthenticationManagerBuilder authenticationManagerBuilder;

   public LoginRestController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
      this.tokenProvider = tokenProvider;
      this.authenticationManagerBuilder = authenticationManagerBuilder;
   }

   @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize (@Valid @RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken (
                        loginDto.getUsername (), loginDto.getPassword ());
        Authentication authentication = authenticationManagerBuilder.getObject ().authenticate (authenticationToken);
        SecurityContextHolder.getContext ().setAuthentication (authentication);
        boolean rememberMe = (loginDto.isRememberMe () == null) ? false : loginDto.isRememberMe ();
        String jwt = tokenProvider.createToken (authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders ();
        httpHeaders.add (tokenProvider.getAuthorizeHeaderKey (), tokenProvider.getAuthorizePrefixKey () + jwt);
        return new ResponseEntity<> (new JWTToken (jwt), httpHeaders, HttpStatus.OK);
    }

   /**
    * Object to return as body in JWT Authentication.
    */
   static class JWTToken {

      private String idToken;

      JWTToken(String idToken) {
         this.idToken = idToken;
      }

      @JsonProperty("id_token")
      String getIdToken() {
         return idToken;
      }

      void setIdToken(String idToken) {
         this.idToken = idToken;
      }
   }
}

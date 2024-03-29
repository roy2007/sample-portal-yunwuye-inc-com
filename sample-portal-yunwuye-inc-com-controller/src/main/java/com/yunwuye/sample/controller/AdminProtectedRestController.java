package com.yunwuye.sample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yunwuye.sample.common.base.result.Result;

@RestController
@RequestMapping("/api")
public class AdminProtectedRestController {

   @GetMapping("/hiddenmessage")
    public Result<HiddenMessage> getAdminProtectedGreeting () {
        return Result.with (new HiddenMessage ("this is a hidden message!"));
   }

   private static class HiddenMessage {

      private final String message;

      private HiddenMessage(String message) {
         this.message = message;
      }

      public String getMessage() {
         return message;
      }
   }

}

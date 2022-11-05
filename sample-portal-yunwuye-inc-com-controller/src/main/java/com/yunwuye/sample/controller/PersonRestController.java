package com.yunwuye.sample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yunwuye.sample.common.base.result.Result;

@RestController
@RequestMapping("/api")
public class PersonRestController {

   @GetMapping("/person")
    public Result<Person> getPerson () {
        return Result.with (new Person ("John Doe", "john.doe@test.org"));
   }

    private static class Person{

      private final String name;

        /**
         * @return the name
         */
        public String getName () {
            return name;
        }

        /**
         * @return the email
         */
        public String getEmail () {
            return email;
        }

        private final String email;

      public Person(String name, String email) {
         this.name = name;
         this.email = email;
      }

   }
}

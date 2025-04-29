package com.bookit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class BookItApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookItApplication.class, args);
    }

    @RestController
    public class HomeController {

        @GetMapping("/")
        public String home() {
            return "Welcome to BookIt API";
        }

        @GetMapping("/success")
        public String success() {
            return "Success endpoint hit!";
        }
    }

}

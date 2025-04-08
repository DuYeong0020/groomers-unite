package com.petstylelab.groomersunite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.petstylelab.common.jwt", "com.petstylelab.common.response", "com.petstylelab.groomersunite"})
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}

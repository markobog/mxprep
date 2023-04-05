package com.mxprep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MxprepApplication {
    public static void main(String[] args) {

        SpringApplication.run(MxprepApplication.class, args);
    }

}

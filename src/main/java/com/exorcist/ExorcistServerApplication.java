package com.exorcist;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.exorcist.mapper")
public class ExorcistServerApplication {

    public static  void main(String[] args) {

        SpringApplication.run(ExorcistServerApplication.class, args);
    }

}

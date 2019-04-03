package com.multimodule.bootquartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling
public class BootquartzApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootquartzApplication.class, args);
    }

}

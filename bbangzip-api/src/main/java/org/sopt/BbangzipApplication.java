package org.sopt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class BbangzipApplication {
        public static void main(String[] args) {
            SpringApplication.run(BbangzipApplication.class, args);
        }
}
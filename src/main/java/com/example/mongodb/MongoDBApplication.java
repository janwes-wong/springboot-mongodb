package com.example.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

/**
 * @author Janwes
 * @version 1.0
 * @package com.example.mongodb
 * @date 2021/2/21 19:46
 * @description MongoDB启动类
 */
@SpringBootApplication
public class MongoDBApplication {
    public static void main(String[] args) {
        SpringApplication.run(MongoAutoConfiguration.class,args);
    }
}

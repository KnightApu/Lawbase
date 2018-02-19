package com.mongo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan( basePackages = "com.mongo" )
@EnableMongoRepositories

public class ConfigForApp {
    
    
}

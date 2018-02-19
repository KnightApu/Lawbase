package com.adhocmaster;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan( basePackages = "com.adhocmaster" )
@EnableMongoRepositories
public class ConfigForApp {
    

}

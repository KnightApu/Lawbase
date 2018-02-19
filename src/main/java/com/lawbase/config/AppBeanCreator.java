package com.lawbase.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import com.adhocmaster.context.SecurityContextFacade;
import com.adhocmaster.context.SecurityContextHolderFacade;
import com.adhocmaster.mongo.user.MongoUserRepository;
import com.adhocmaster.mongo.user.User;
import com.adhocmaster.mongo.user.UserHelper;

@Configuration
public class AppBeanCreator {
    

    @Autowired
    private MongoUserRepository userRepository;

    @Bean
    public SecurityContextFacade securityContextFacade() {

        SecurityContextFacade contextFacade = new SecurityContextHolderFacade();

        // contextFacade.setContext( SecurityContextHolder.getContext() );

        // this.securityContextFacade = contextFacade;

        return contextFacade;

    }

    @Bean
    @Autowired
    public AuditorAware<User> auditorProvider( SecurityContextFacade securityContextFacade ) {

        return new LawbaseAuditorAware( securityContextFacade, userRepository );

    }

    @Bean
    public UserHelper userHelper( SecurityContextFacade securityContextFacade ) {

        return new UserHelper( userRepository, securityContextFacade );

    }

}

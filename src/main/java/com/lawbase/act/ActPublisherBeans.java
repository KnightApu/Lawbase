package com.lawbase.act;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

@Configuration
public class ActPublisherBeans {

    @Bean( "actSavedPublisher" )
    public Subject<Act> actSavedPublisher() {
        
        Subject<Act> subject = PublishSubject.create();
        
        return subject;
        
    }
    @Bean( "actDeletedPublisher" )
    public Subject<Act> actDeletedPublisher() {
        
        Subject<Act> subject = PublishSubject.create();
        
        return subject;
        
    }
    
}

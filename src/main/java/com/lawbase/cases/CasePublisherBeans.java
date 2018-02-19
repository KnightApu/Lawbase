package com.lawbase.cases;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

@Configuration
public class CasePublisherBeans {

    @Bean( "caseSavedPublisher" )
    public Subject<Case> caseSavedPublisher() {
        
        Subject<Case> subject = PublishSubject.create();
        
        return subject;
        
    }
    @Bean( "caseDeletedPublisher" )
    public Subject<Case> caseDeletedPublisher() {
        
        Subject<Case> subject = PublishSubject.create();
        
        return subject;
        
    }
    
    
}

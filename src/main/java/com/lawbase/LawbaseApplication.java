package com.lawbase;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.adhocmaster.mongo.sequence.SequenceDao;
import com.test.mongo.CustomerRepository;

/**
 * Spring config and component imports
 * 
 * @author muktadir
 *
 */
@Import( {
        com.test.ConfigForApp.class,
        com.adhocmaster.ConfigForApp.class,
        com.utilility.ConfigForApp.class,
        com.mongo.ConfigForApp.class,
        com.solr.ConfigForApp.class
} )

@EnableWebSecurity
@EnableMongoRepositories
@SpringBootApplication
@EnableAsync
@EnableCaching
public class LawbaseApplication implements CommandLineRunner {

    @Autowired
    private SequenceDao sequenceDao;
    

    @Autowired
    private CustomerRepository repository;

    public static void main( String[] args ) {

        SpringApplication.run( LawbaseApplication.class, args );
    }

    @Override
    public void run( String... args ) throws Exception {
        // TODO Auto-generated method stub

        // repository.deleteAll();
        //
        // // save a couple of customers
        // repository.save(new Customer("Alice", "Smith"));
        // repository.save(new Customer("Bob", "Smith"));
        //
        // // fetch all customers
        // System.out.println("Customers found with findAll():");
        // System.out.println("-------------------------------");
        // for (Customer customer : repository.findAll()) {
        // System.out.println(customer);
        // }
        // System.out.println();
        //
        // // fetch an individual customer
        // System.out.println("Customer found with findByFirstName('Alice'):");
        // System.out.println("--------------------------------");
        // System.out.println(repository.findByFirstName("Alice"));
        //
        // System.out.println("Customers found with findByLastName('Smith'):");
        // System.out.println("--------------------------------");
        // for (Customer customer : repository.findByLastName("Smith")) {
        // System.out.println(customer);
        // }

    }

    @Bean( "caseIndexExecutor" )
    public Executor caseIndexExecutor() {
        
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor( "caseIndexExecutor" );
        
        executor.setConcurrencyLimit( 1 );
        
        executor.setThreadPriority( Thread.MIN_PRIORITY );
        
        return executor;
        
    }
    @Bean( "actIndexExecutor" )
    public Executor actIndexExecutor() {
        
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor( "actIndexExecutor" );
        
        executor.setConcurrencyLimit( 1 );
        
        executor.setThreadPriority( Thread.MIN_PRIORITY );
        
        return executor;
        
    }
    @Bean( "taskExecutor" )
    public Executor taskExecutor() {
        
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor( "taskExecutor" );
        
        executor.setConcurrencyLimit( 10 );
                
        return executor;
        
    }
    
    
}

package com.lawbase.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.adhocmaster.mongo.auth.MongoAuthenticationProvider;

@Configuration

@EnableGlobalMethodSecurity( securedEnabled = true, prePostEnabled = true )
public class SecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    
    @Autowired
    MongoAuthenticationProvider authenticationProvider;
    
    @Autowired
    public void configureAuthbuilder( AuthenticationManagerBuilder authBuilder ) throws Exception {
        
        authBuilder.authenticationProvider( authenticationProvider );
        
    }

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
    		
 ///i added   	
    //	http.csrf().disable();
////////
    	
        // TODO Auto-generated method stub
        //super.configure( http );
        http
            .authorizeRequests()
                //.antMatchers( HttpMethod.GET, "/" ).permitAll()
                .antMatchers( HttpMethod.GET, "/indexPublic" ).permitAll()
                .antMatchers( "/admin/**" ).hasAuthority("ADMIN")
                .antMatchers( "/front/**" ).access( "( hasAuthority('ENTERPRISE') ) or ( hasAuthority('INDIVIDUAL') ) or ( hasAuthority('ADMIN') )" )
                .anyRequest().permitAll()
                .and()
            .formLogin()
            	.loginPage( "/login" ).defaultSuccessUrl( "/" , true )
            	.and()
            	.logout().logoutUrl( "/logout" ).logoutSuccessUrl( "/" )
            	.and()
            .exceptionHandling()
            	.accessDeniedPage( "/error/accessDenied" )
            	.and()
            .sessionManagement()
            	.maximumSessions( 100 )
            	.maxSessionsPreventsLogin( false )
            	.expiredUrl( "/login" )
            	.sessionRegistry( sessionRegistry() )
            	;
            	
//        http
//            .authorizeRequests()
//                .antMatchers( HttpMethod.GET, "/indexPublic" ).permitAll();
        
    }
    
    
    @Bean
    SessionRegistry sessionRegistry() {
    	
        return new SessionRegistryImpl();
    
    }
    
    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {	
    
    	return new ServletListenerRegistrationBean( new HttpSessionEventPublisher() );
    
    }
    

}

package com.lawbase.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
                .antMatchers( "/front/case/view/**" ).access( "( hasAuthority('ENTERPRISE') ) or ( hasAuthority('USER') ) or ( hasAuthority('ADMIN') )" )
                .antMatchers("/front/profile/").access("( hasAuthority('USER') )")
                .anyRequest().permitAll()
                .and()
            .formLogin()
            	.loginPage("/login").defaultSuccessUrl("/")
            	.and()
            	.logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout")
            	.and()
            .exceptionHandling()
            	.accessDeniedPage("/error/accessDenied");
            	
//        http
//            .authorizeRequests()
//                .antMatchers( HttpMethod.GET, "/indexPublic" ).permitAll();
        
    }
    

}

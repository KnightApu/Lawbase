package com.lawbase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	
	@Override
    public void addViewControllers( ViewControllerRegistry registry ) {
		
        //registry.addViewController("/home").setViewName("home");
        //registry.addViewController("/").setViewName("home");
        //registry.addViewController("/hello").setViewName("hello");
		  registry.addViewController("/login").setViewName("front/login");
          registry.addViewController("/logout").setViewName("front/logout");
    }
	
	/*@Bean
    public ViewResolver getViewResolver(){
         InternalResourceViewResolver resolver = new InternalResourceViewResolver();
         resolver.setPrefix("");
         resolver.setSuffix("");
         return resolver;
    }*/


}

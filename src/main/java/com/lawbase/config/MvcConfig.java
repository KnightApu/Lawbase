package com.lawbase.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
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
	
	@Override
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {
	 
		 registry.addResourceHandler( "/**" )
		 		.addResourceLocations( "classpath:/static/" )
		 		.setCacheControl( CacheControl.maxAge( 1, TimeUnit.DAYS ) );
		 
		 registry.addResourceHandler( "/admin/js/**" )
		 		.addResourceLocations( "classpath:/static/admin/js/" )
		 		.setCacheControl( CacheControl.maxAge( 5, TimeUnit.MINUTES ) );
		 
		 registry.addResourceHandler( "/public/js/**" )
	 			.addResourceLocations( "classpath:/static/public/js/" )
	 			.setCacheControl( CacheControl.maxAge( 5, TimeUnit.MINUTES ) );
		 
	 
	 }


}

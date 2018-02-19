package com.lawbase.config;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.adhocmaster.context.SessionInterceptor;
import com.adhocmaster.mongo.user.MongoUserRepository;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Configuration
@EnableMongoAuditing
public class AppConfig extends WebMvcConfigurerAdapter {

    // private SecurityContextFacade securityContextFacade;
    @Autowired
    private MongoUserRepository userRepository;

    @Autowired
    private SessionInterceptor sessionInterceptor;


    /**
     * Only works in springboot
     * @return mappingJackson2HttpMessageConverter BEAN
     */
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {

        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();

        builder.serializerByType( ObjectId.class, new ToStringSerializer() );

        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter( builder.build() );
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
//        jsonConverter.setObjectMapper( objectMapper );
        return jsonConverter;
    }

    @Override
    public void addInterceptors( InterceptorRegistry registry ) {

        registry.addInterceptor( this.sessionInterceptor );
        super.addInterceptors( registry );

    }

    @Override
    public void configureMessageConverters( List<HttpMessageConverter<?>> converters ) {

        //converters.add( mappingJackson2HttpMessageConverter() );

    }

}

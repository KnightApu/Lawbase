package com.solr;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.convert.SolrJConverter;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

import com.solr.lawbase.search.act.ActSolrRepository;
import com.solr.lawbase.search.cases.CaseSolrRepository;

@Configuration
@ComponentScan( basePackages = "com.solr" )
@EnableSolrRepositories(basePackages = "com.solr.lawbase.search")

public class ConfigForApp {

    private static final Logger logger = LoggerFactory.getLogger( ConfigForApp.class );
    private static final String PROPERTY_NAME_SOLR_SERVER_URL = "spring.data.solr.host";
    private static final String PROPERTY_NAME_SOLR_COLLECTION_CASE = "spring.data.solr.collection.case";
    private static final String PROPERTY_NAME_SOLR_COLLECTION_ACT = "spring.data.solr.collection.act";
    @Resource
    private Environment environment;  


    private SolrTemplate getSolrTemplateWithSolrJConverter( String collectionName ) {

        //SolrClient client = new HttpSolrClient( environment.getRequiredProperty( PROPERTY_NAME_SOLR_SERVER_URL ) + "/" + collectionName );
        SolrClient client = new HttpSolrClient( environment.getRequiredProperty( PROPERTY_NAME_SOLR_SERVER_URL ) );
        
        SolrTemplate template = new SolrTemplate( client );
        
        template.setSolrConverter( new SolrJConverter() ); // not using spring converter
        
        
        template.setSolrCore( collectionName );
        
        return template;
        
    }
    
    
    @Bean
    public CaseSolrRepository caseSolrRepository( 
            
            @Qualifier( "caseSolrTemplate" ) SolrTemplate caseSolrTemplate
            
            ) throws IllegalAccessException {

        //SolrTemplate caseSolrTemplate = getSolrTemplateWithSolrJConverter( environment.getRequiredProperty( PROPERTY_NAME_SOLR_COLLECTION_CASE ) );
                
        return new CaseSolrRepository( caseSolrTemplate );
        
    }
    
    
    @Bean("caseSolrTemplate")
    public SolrTemplate caseSolrTemplate() {

        logger.info( "Creating caseSolrTemplate with core: " + environment.getRequiredProperty( PROPERTY_NAME_SOLR_COLLECTION_CASE ) );
        SolrTemplate template = getSolrTemplateWithSolrJConverter( environment.getRequiredProperty( PROPERTY_NAME_SOLR_COLLECTION_CASE ) );
        return template;
        
    }


    @Bean
    public ActSolrRepository actSolrRepository( 
            
            @Qualifier( "actSolrTemplate" ) SolrTemplate actSolrTemplate
            
            ) throws IllegalAccessException {
                
        return new ActSolrRepository( actSolrTemplate );
        
    }
    
    
    @Bean("actSolrTemplate")
    public SolrTemplate actSolrTemplate() {

        logger.info( "Creating actSolrTemplate with core: " + environment.getRequiredProperty( PROPERTY_NAME_SOLR_COLLECTION_ACT ) );
        
        SolrTemplate template = getSolrTemplateWithSolrJConverter( environment.getRequiredProperty( PROPERTY_NAME_SOLR_COLLECTION_ACT ) );
        return template;
        
    }

}

package com.mongo;

//@TestConfiguration
public class ConfigForTest {

//    @Autowired
//    MongoClient mongo;
//    
//    @Autowired
//    MongoProperties mongoProperties;
//    
//
//    @Autowired
//    Environment env;
//
//    @ConditionalOnMissingBean
//    @Bean
//    public MongoClient mongo() {
//
//        ServerAddress address = new ServerAddress( "127.0.0.1", 27017 );
//        MongoCredential credential = MongoCredential.createCredential( 
//                "root",
//                "admin",
//                "test".toCharArray() 
//                );
//        
//        List<MongoCredential> list = new ArrayList<MongoCredential>();
//        list.add( credential );
//        return new MongoClient( address,  list);
//        
//    }
//    @ConditionalOnMissingBean
//    @Bean
//    public MongoTemplate mongoTemplate() {
//        
//        return new MongoTemplate( mongo, mongoProperties.getDatabase() );
//        
//    }
    
}

package com.lawbase.taxonomy;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.adhocmaster.mongo.PersistenceException;
import com.adhocmaster.mongo.sequence.SequenceDao;
import com.adhocmaster.mongo.user.MongoUserRepository;
import com.adhocmaster.mongo.user.User;
import com.adhocmaster.mongo.user.UserCreator;
import com.lawbase.taxonomy.Category;
import com.lawbase.taxonomy.CategoryRepository;



@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource( locations = "classpath:application-test.properties" )
public class CategoryTest {

    @Autowired
    CategoryRepository categoryRepository; 

    @Autowired
    private MongoUserRepository userRepository;
    

    @Autowired
    private SequenceDao sequenceDao;
    
    @Test
    public void testSingle() {

        Category category = new Category();
        
        category.setName( "test" );
        
        categoryRepository.save( category );
        
        Category category2 = categoryRepository.findByName( "test" );
        
        System.out.println( category2 );
        
        assertNotNull( category2 );

    }
    
    @Test
    public void testMultiple() {

        Category category = new Category();
        
        category.setName( "test parent" );
        
        categoryRepository.save( category );
        
        for( int i=0; i < 10; ++i ) {
            
            Category categoryChild = new Category();
            String name = "test-" + i;
            categoryChild.setName( name );
            
            categoryChild.setParent( category );
            category.addChild( categoryChild );
            
            categoryRepository.save( categoryChild );
            
        }
        
        categoryRepository.save( category );
        
        Category category2 = categoryRepository.findByName( "test parent" );
        
        System.out.println( category2 );
        
        assertNotNull( category2 );
        
        assertEquals( 10, category2.getChildren().size() );
        
        
    }

    
    private User getTestAuthor() throws PersistenceException {

        UserCreator userCreator = new UserCreator( sequenceDao, userRepository, "Test User", "test" + System.currentTimeMillis(), "test" + System.currentTimeMillis() + "@test.com" );
        
        return userCreator.createAndPersist();
        
    }
}

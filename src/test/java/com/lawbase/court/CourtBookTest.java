package com.lawbase.court;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.adhocmaster.context.SecurityContextFacade;
import com.adhocmaster.mongo.PersistenceException;
import com.adhocmaster.mongo.sequence.SequenceDao;
import com.adhocmaster.mongo.user.MongoUserRepository;
import com.adhocmaster.mongo.user.User;
import com.adhocmaster.mongo.user.UserCreator;
import com.adhocmaster.mongo.user.UserException;
import com.book.BookNode;
import com.book.content.Content;
import com.book.exceptions.NotALeafNodeException;
import com.book.exceptions.NotAParentNodeException;
import com.book.simpleBook.SimpleBookNode;
import com.book.simpleBook.TextContent;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource( locations = "classpath:application-test.properties" )
public class CourtBookTest {


    private SecurityContextFacade mockSecurityContextFacade;
    private SecurityContext mockSecurityContext;
    
    @Autowired
    AuditorAware<User> auditorAware;
    
    @Autowired
    private CourtBookRepository courtRepository;
    
    @Autowired
    private MongoUserRepository userRepository;
    

    @Autowired
    private SequenceDao sequenceDao;

    @Before
    public void setUp() throws Exception {
        
      mockSecurityContextFacade = mock(SecurityContextFacade.class);
      mockSecurityContext = mock(SecurityContext.class);
      stub(mockSecurityContextFacade.getContext()).toReturn(mockSecurityContext);
      
      
    }
    
    
    @Test
    public void test() throws PersistenceException {
        
        User author = getTestAuthor();
                
        String title =  "Test Supreme";
        CourtBook book = new CourtBook( title, author );
        
        List<CourtBook> existing = courtRepository.findByTitleStartingWith( title, new PageRequest(0,1) ).getContent();

        if ( null != existing )
            courtRepository.delete( existing );
        
        courtRepository.save( book );
        
        System.out.println( book );
        
        //courtRepository.delete( book );
        
        
    }
    
    @Test
    public void testTree() throws PersistenceException {


        User author = getTestAuthor();
        
        String title =  "Test Supreme " + System.currentTimeMillis();
        CourtBook book = new CourtBook( title, author );
        
        courtRepository.save( book );
        
        // create some branches

        BookNode aNode1 = new SimpleBookNode( false, "Section 1" );
        BookNode aNode2 = new SimpleBookNode( false, "Section 2" );
        BookNode aNode3 = new SimpleBookNode( true, "Preface" ); // leaf
        
        Content content = new TextContent( "title", "body" );
        
        try {
            
            aNode3.addContent( content );
            
        } catch ( NotALeafNodeException e1 ) {

            fail( "NotALeafNodeException" );
            
        }
        
        BookNode root = book.getRootNode();
        
        System.out.println( root );
        
        try {
            
            root.addNode( aNode1 );
            root.addNode( aNode2 );
            root.addNode( aNode3 );

            courtRepository.save( book );
            
            
        } catch ( NotAParentNodeException e ) {

            fail( "NotAParentNodeException" );
            
        }
        
    }
    
    private User getTestAuthor() throws PersistenceException {

        UserCreator userCreator = new UserCreator( sequenceDao, userRepository, "Test User", "test" + System.currentTimeMillis(), "test" + System.currentTimeMillis() + "@test.com" );
        
        return userCreator.createAndPersist();
        
    }

}

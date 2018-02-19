package com.lawbase.media;

import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.adhocmaster.mongo.sequence.SequenceDao;
import com.mongo.media.Media;
import com.mongo.media.MediaRepository;
import com.mongo.media.MediaService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource( locations = "classpath:application-test.properties" )
public class MediaServiceTest {

    @Autowired
    private MediaRepository mediaRepository;
    
    @Autowired
    SequenceDao sequenceDao;
    
    String rootPath = "E:/testLawbase";  //destination folder
    String relativeUrl = "";
    
    MediaService mediaService;
    
    MediaService failedMediaService;


    @Before
    public void before() {
        
        mediaService = new MediaService( sequenceDao, mediaRepository, rootPath, relativeUrl );
        
        failedMediaService=new MediaService(mediaRepository, rootPath, relativeUrl);
        
    }
    
    @Test
    public void testSave() throws Exception {
    	
    	// mediaService = new MediaService( sequenceDao, mediaRepository, rootPath, relativeUrl );
         
        String tempPath = "E:/newSFileTest.txt";   //sourceFile
        
        FileInputStream is = new FileInputStream( tempPath );
        
        Media media = mediaService.save( is, "newFileTest.txt", "text/plain" );//(source file stream, fileName in destination folder,mime type)
        
        System.out.println( media );
        
        assertNotNull( media );
        
            }
    
    @Test
    public void failTestSave() 
    {
    	//failedMediaService=new MediaService(mediaRepository, rootPath, relativeUrl);
        
    	String tempPath = "E:/newSFileTest.txt";   //sourceFile
        
        FileInputStream is=null;
        
		try {
			is = new FileInputStream( tempPath );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        Media media=null;
        
		try {
		
			media = failedMediaService.save( is, "newFileTest.txt", "text/plain" );
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			//e.printStackTrace();
			System.out.println(e.getMessage());
			
			assert(e.getMessage()!=null);
			assert(media==null);
		}
		
        System.out.println("the value of media: "+media );
        
      
        
    	
    }

}

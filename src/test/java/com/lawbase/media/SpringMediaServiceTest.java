package com.lawbase.media;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import com.mongo.media.Media;
import com.mongo.media.SpringMediaService;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource( locations = "classpath:application-test.properties" )
public class SpringMediaServiceTest {

  
    @Autowired 
    SpringMediaService springMediaService;
 
    
    @Test
    public void testSpringMediaSave() throws Exception {

        String tempPath = "E:/newSFileTest.txt";   //sourceFile
       
        System.out.println( "inside testing" );
        
        File file = new File(tempPath);
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",file.getName(), "text/plain", IOUtils.toByteArray(input));
        
        System.out.println( "done creating mockFile" );
        Media media = springMediaService.save( multipartFile );//(source file stream, fileName in destination folder,mime type)
        
       // Media media = springMediaService.mediaService.save( input, "newFileTest.txt", "text/plain" );//(source file stream, fileName in destination folder,mime type)
        
        
        System.out.println( media );
        
        assertNotNull( media );
        
        
        //fail( "Not yet implemented" );
    }
    
    

}

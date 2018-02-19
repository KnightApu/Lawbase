package com.mongo.media;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.adhocmaster.controller.MongoRestController;
import com.adhocmaster.service.RepositoryService;

import util.restApi.RestBadDataException;
import util.restApi.RestInternalServerException;

@RestController
@RequestMapping("/data-api/extra/media")
public class MediaServiceRestController extends MongoRestController<Media>{

    @Autowired
    MediaRepository mediaRepository;
    @Autowired
    SpringMediaService mediaManager;
    
    
    private static final Logger logger = LoggerFactory.getLogger( SpringMediaService.class );


    @Override
    protected RepositoryService<Media> getService() {

        // TODO Auto-generated method stub
        return null;
    }
    
    @PostMapping("")
    public Media handleFileUpload ( 
            
            @RequestParam("file") MultipartFile file
            
            )  throws RestBadDataException, RestInternalServerException {
        
        try {
            
            Media media = mediaManager.save( file );

            return media;
            
        } catch ( Exception e ) {

            
            throw new RestInternalServerException( e );
            
        }
        
    }

}

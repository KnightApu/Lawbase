package com.mongo.media;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adhocmaster.mongo.sequence.SequenceDao;


public class MediaService {

    private static final Logger logger = LoggerFactory.getLogger( SpringMediaService.class );

    protected String rootPath;
    protected String relativeUrl;
    private MediaRepository mediaRepository;
    
    ////i added
    private SequenceDao sequenceDao;
    /////

    public MediaService( MediaRepository mediaRepository, String rootPath, String relativeUrl ) {

        this.rootPath = rootPath;
        this.relativeUrl = relativeUrl;
        this.mediaRepository = mediaRepository;
    }

    public MediaService( MediaRepository mediaRepository, String relativeUrl ) {

        this.rootPath = "/";
        this.relativeUrl = relativeUrl;
        this.mediaRepository = mediaRepository;
    }

    public MediaService( SequenceDao seq, MediaRepository mediaRepository, String rootPath, String relativeUrl ) {
    	
        this.rootPath = rootPath;
        this.relativeUrl = relativeUrl;
        this.mediaRepository = mediaRepository;
        this.sequenceDao = seq;
    }

    public Media save( InputStream fileStream, String fileName, String MIMEtype ) throws Exception {
    	
        //step 1. save media object

        Media media = getObjectFromFileName( fileName );
      
        
        //i edited here
        try{
        	
        	logger.debug( "trying to generate seqId" );
        	 
	        long numericId = sequenceDao.getNextSequenceId( Media.SEQ_KEY );
	      
	        logger.debug( "the id for media:" + numericId );
	                    
	        media.setId( numericId );	      
	        
        }
        catch(NullPointerException e)
        {
        	e.printStackTrace();
        	
        	throw new Exception("failed in generatingNextSequenceId for "+media.getName());
        	
        }
        ////my edit ends

        mediaRepository.save( media );
        
        //step 2. generate path
        String destPath = generatePath( media );

        //step 3. Move the file to the path. if move fails, rollback

        try {

            saveStream( fileStream, destPath );
            
        } catch ( IOException ex ) {
            
            ex.printStackTrace();

            mediaRepository.delete( media );
            
            throw new Exception ("couldn't save stream ( " + media.getName() + " ) to destination path: " + destPath );
            
        }
        
        //step 4. update database record with path and url

        media.setMimeType( MIMEtype );

        media.setPath( destPath );

        media.setUrl( generateUrl( media ) );

        mediaRepository.save( media );

        return media;
    }

    private void saveStream( InputStream is, String destPath ) throws IOException {

        FileOutputStream out = null;

        try {

            logger.debug( "before file opening..................................." + destPath );

            File file = new File( destPath );

            logger.debug( "after file opening....................................=" + file.getAbsolutePath() + " name="
                    + file.getName() );

            out = new FileOutputStream( file );

            logger.debug( "after opening outputstream" );

            int read = 0;
            byte[] bytes = new byte[1024];

            while ( ( read = is.read( bytes ) ) != -1 ) {

                out.write( bytes, 0, read );

            }

            out.flush();
            out.close();

            String output = "File successfully uploaded to : " + destPath;

            logger.debug( output );

        } catch ( IOException e ) {

            logger.debug( "in catch" );

            throw e;

        } finally {

            if ( out != null ) {

                out.close();

            }

        }

    }

    private Media getObjectFromFileName( String fileName ) {

        String status = "available";

        Media media = new Media();

        media.setName( fileName );

        media.setStatus( status );

        media.setExtension( FilenameUtils.getExtension( fileName ) );

        return media;
    }

    private String generatePath( Media media ) throws IOException {

        // String name = media.getName();
        //
        // String basename = FilenameUtils.getBaseName( name );
        // String extension = FilenameUtils.getExtension( name );
        //
        // String newName = basename + "_" + System.currentTimeMillis() + "." +
        // extension;
        //
        // File tempFile = new File( rootPath + "\\" + newName );
        //
        // media.setName( newName );
        //
        // logger.debug( "temp path==" + tempFile.getAbsolutePath() );
        //
        // return tempFile.getAbsolutePath();

        String generatedPath = "";

        int innerFolder = ( int ) ( media.getId() / Media.maxFileNumber );

        String tempFolder = "";

        for ( int counter = 0; counter < innerFolder; counter++ ) {

            tempFolder = tempFolder + "/" + Integer.toString( 0 );

        }

        // Check if the folder exists? if not then first create the directory
        String tempPath = rootPath + tempFolder.trim();

        Path path = Paths.get( tempPath );

        if ( Files.isDirectory( path ) ) {

            generatedPath = tempPath;

        }

        else {

            path = Files.createDirectories( path );
            generatedPath = path.toString();

        }

        return generatedPath + "/" + media.getId() + "-" + media.getSlug() + "." + media.getExtension();

    }

    private String generateUrl( Media media ) {

//        String fileUrl = relativePath + media.getName();
//
//        logger.debug( "fileUrl==" + fileUrl );
//
//        return fileUrl;

        String relativeURL = media.getPath().replace(rootPath, relativeUrl);
        
        return relativeURL;

    }

}

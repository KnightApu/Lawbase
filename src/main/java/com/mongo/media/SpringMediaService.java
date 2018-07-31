package com.mongo.media;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.adhocmaster.mongo.PersistenceException;
import com.adhocmaster.mongo.sequence.SequenceDao;

@Component

public class SpringMediaService {

	private static final Logger logger = LoggerFactory.getLogger( SpringMediaService.class );

	@Autowired
	MediaRepository mediaRepository;
	@Autowired
    SequenceDao sequenceDao;

	@Autowired
	private Environment env;
	@Autowired
	ServletContext context;
	
	/**
	 * Only to be used for testing purposes. This constructor only used in JUnit
	 * tests as Context is not available.
	 * 
	 * @param rootPath
	 * @throws Exception
	 */
	
	//i wrote
	
	
	public Resource loadAsResource(Media media) throws Exception {

		try {

			File file = new File(media.getPath());

			URI uri = file.toURI();

			Resource resource = new UrlResource(uri);
			if (resource.exists() || resource.isReadable()) {
				logger.debug("resouce found==" + resource.getFilename());

				return resource;
			} else {
				throw new Exception("Could not read file:");

			}
		} catch (MalformedURLException e) {
			throw new Exception("Could not read file:");
		}

	}

	
	public Media save( MultipartFile file ) throws Exception {

		// String port = env.getProperty( "server.port" );
		//
		// String server = env.getProperty( "server.address" );
		//
		// logger.debug( "server path=" + port + " server=" + server );
		//
		// String relativePath = server + ":" + port + "/" + env.getProperty(
		// "upload.folder" );
	
				
		String rootPath = "";
		String relativeUrl = "";
		
		rootPath = env.getProperty( "upload.folder" );
		relativeUrl = env.getProperty( "media.relative.url" );
		
		MediaService mediaService = new MediaService( sequenceDao, mediaRepository, rootPath, relativeUrl );

		
		InputStream inputStream = file.getInputStream();
		String fileName = file.getOriginalFilename();
		String MIMEtype = file.getContentType();


		Media media = mediaService.save( inputStream, fileName, MIMEtype );

		return media;

	}

	
	public Set<Media> validateAndPopulateFromFormData(

			MultipartFile imageFile, MultipartFile audioFile, MultipartFile videoFile

			) throws PersistenceException {

		Set<Media> mediaSet = new HashSet<Media>();
		
		if (imageFile != null && imageFile.isEmpty() != true) {

			Media image;
			
			try {
			
				image = save( imageFile );
				mediaSet.add( image );
			
			} catch ( Exception e ) {
				
				throw new PersistenceException( "Error in saving image file" );
			}

		}

		if (audioFile != null && audioFile.isEmpty() != true) {

			Media audio;
			
			try {
			
				audio = save( audioFile );
				mediaSet.add( audio );
			
			} catch ( Exception e ) {
			
				throw new PersistenceException( "Error in saving audio file" );
			
			}

		}

		if (videoFile != null && videoFile.isEmpty() != true) {
			
			Media video;
			
			try {

				video = save( videoFile );
				mediaSet.add( video );

			} catch ( Exception e ) {
				
				throw new PersistenceException( "Error in saving video file" );
			
			}

		}

		if (mediaSet.size() > 0)
	
			return mediaSet;
		
		else
			
			return null;
		
	}

	
	public void delete( long id ) {

		mediaRepository.delete( id );
	}

}

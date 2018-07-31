package com.mongo.media;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adhocmaster.controller.MvcController;


@Controller

public class MediaController extends MvcController {

	@Autowired
	MediaRepository mediaRepository;

	@Autowired
	SpringMediaService mediaManager;

	@Override
	protected void generateControllerPaths() {

		// TODO Auto-generated method stub

	}

	@RequestMapping( "/media" )
	@ResponseBody
	public ResponseEntity<Resource> serveFile(

			@RequestParam Map<String, String> param

	) throws Exception {

		String id = param.get( "mediaId" );

		if ( null == id ) {

			id = param.get( "id" );

		}

		if ( null == id )
			throw new Error( "Media id not found in id or mediaId" );

		Media media = mediaRepository.findOne( Long.valueOf( id ) );

		try {

			Resource file = mediaManager.loadAsResource( media );
			return ResponseEntity.ok()
					.header( HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"" )
					.body( file );

		} catch ( Exception e ) {
			
			throw new IOException( "Error in serving media file" );

		}

	}

	
	@RequestMapping( "/mediaOpen" )
	@ResponseBody
	public ResponseEntity<Resource> openFile(

			@RequestParam Map<String, String> param

	) throws Exception {

		String id = param.get( "mediaId" );

		if ( null == id ) {

			id = param.get( "id" );

		}

		if ( null == id )
			throw new Error( "Media id not found in id or mediaId" );

		Media media = mediaRepository.findOne( Long.valueOf( id ) );

		try {

			// System.out.println(" Serving media :" + media.getId());

			Resource file = mediaManager.loadAsResource( media );
			return ResponseEntity.ok()
					.header( HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"" )
					.header( HttpHeaders.CONTENT_TYPE, media.getMimeType() )
					.body( file );

		} catch ( Exception e ) {
			
			throw new IOException( "Error in opening media file" );
			
		}

	}
	
//	@RequestMapping("/getmedia")
//	@ResponseBody
//	public ResponseEntity<Resource> getMedia(
//
//			@RequestParam Map<String, String> param
//
//	) throws Exception {
//
//		String id = param.get("translationId");
//		String type = param.get("type");
//
//		if (null == id) {
//
//			id = param.get("id");
//
//		}
//
//		if (null == id)
//			throw new Error("Translation id not found in id or translation id");
//
//		Word word = wordRepository.findByTranslationIdAndLanguage(Long.parseLong(id), Language.EN).get(0);
//
//		// System.out.println(word.getText());
//
//		Long mediaId = null;
//
//		for (Media media : word.getMediaSet()) {
//			// System.out.println("got type: " + media.getMimeType());
//
//			if (media.getMimeType().contains(type)) {
//
//				mediaId = media.getId();
//			}
//		}
//
//		// System.out.println("media Id : " + mediaId);
//
//		Media media;
//
//		try {
//
//			media = mediaRepository.findOne(mediaId);
//			// System.out.println(" Serving getmedia :" + media.getId());
//			Resource file = mediaManager.loadAsResource(media);
//
//			return ResponseEntity.ok()
//					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
//					.body(file);
//
//		} catch (Exception e) {
//			// TODO: handle exception
//
//			// e.printStackTrace();
//			return null;
//		}
//
//	}

}

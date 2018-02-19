package com.test.fileUpload;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class fileUpload {

	@RequestMapping(value="/uploadForm")  
    public String index(){  
		//ModelAndView modelAndView = new ModelAndView();  
	   // modelAndView.setViewName("fileUploadForm"); 
		//return modelAndView; 
        return "fileUploadForm";  
        
    } 
}

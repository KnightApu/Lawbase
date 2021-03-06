package com.lawbase.module.front.index;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lawbase.cases.CaseRepository;
import com.lawbase.controller.FrontCommonController;

@Controller
public class FrontIndexController extends FrontCommonController  {
	
	private static final Logger logger = LoggerFactory.getLogger( FrontIndexController.class );

	@Autowired
    private CaseRepository caseRepository;
	
    @RequestMapping("/")
    public String base(Model model) {
        
    	addCommonFrontMenuAttributes(model);
    	
    	logger.info("base method");
    	
        return "front/index";
        
    }
    
    @PreAuthorize( "hasAuthority('READ_CASE')" )
    @RequestMapping("/case")
    public String readCase(Model model) {
        
    	addCommonFrontMenuAttributes(model);
    	
        return "front/index";
        
    }
    
    @RequestMapping("index")
    public String index(Model model) {
    	
    	addCommonFrontMenuAttributes(model);
        
        return "front/index";
        
    }
    
    @Secured("ROLE_INDIVIDUAL")
    @RequestMapping("index2")
    public String index2(Model model) {
        
    	addCommonFrontMenuAttributes(model);
    	
        return "front/index";
        
    }

    @RequestMapping( value = "/indexPublic", method = RequestMethod.GET )
    public String index3(Model model) {

    	addCommonFrontMenuAttributes(model);
    	
        return "front/index";
        
    }
    



}

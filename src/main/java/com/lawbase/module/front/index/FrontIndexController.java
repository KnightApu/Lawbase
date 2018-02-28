package com.lawbase.module.front.index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawbase.cases.CaseRepository;
import com.lawbase.controller.FrontCommonController;



@Controller
public class FrontIndexController extends FrontCommonController  {

	@Autowired
    private CaseRepository caseRepository;
	

	
    @RequestMapping("/")
    String base(Model model) {
        
    	addCommonFrontMenuAttributes(model);
    	
        return "front/index";
        
    }
    @PreAuthorize( "hasAuthority('READ_CASE')" )
    @RequestMapping("/case")
    String readCase(Model model) {
        
    	addCommonFrontMenuAttributes(model);
    	
        return "front/index";
        
    }
    @RequestMapping("index")
    String index(Model model) {
    	
    	addCommonFrontMenuAttributes(model);
        
        return "front/index";
        
    }
    @Secured("ROLE_INDIVIDUAL")
    @RequestMapping("index2")
    String index2(Model model) {
        
    	addCommonFrontMenuAttributes(model);
    	
        return "front/index";
        
    }

    @RequestMapping( value = "/indexPublic", method = RequestMethod.GET )
    String index3(Model model) {

    	addCommonFrontMenuAttributes(model);
    	
        return "front/index";
        
    }
    



}

package com.lawbase.court;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lawbase.cases.Case;
import com.lawbase.cases.CaseRepository;
import com.lawbase.module.admin.book.RestHelper;

@RestController
@RequestMapping( "/admin/rest/search" )
public class CaseSearchRestControllerAdminPleaseDelete {
	
	@Autowired
    private CourtBookRepository courtRepository;
    @Autowired
    private RestHelper<CourtBook> restHelper;
    @Autowired
    private CaseRepository caseRepository;
	//only can search by title
	 @RequestMapping("/case")
	    public @ResponseBody List<Case> index(
	    		@RequestParam(value = "title", required = false, defaultValue = "0") String title
				) {
	    		
	    		List<Case> cases = caseRepository.findByTitle(title);
			
			
			 
			return cases;
	   }
	
	

}

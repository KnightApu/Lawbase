package com.lawbase.module.admin.index;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import com.adhocmaster.context.SecurityContextFacade;
import com.adhocmaster.controller.MvcUserController;
import com.adhocmaster.mongo.user.UserHelper;
import com.adhocmaster.mongo.user.UserNotFoundInSessionException;

@Controller
@RequestMapping("/admin")
public class AdminIndexController extends MvcUserController {
    
    private static final Logger logger = LoggerFactory.getLogger( AdminIndexController.class );
    
    private final SecurityContextFacade securityContextFacade;

    @Autowired
    UserHelper userHelper;
    
    @Autowired
    public AdminIndexController( SecurityContextFacade securityContextFacade ) {
        
        this.securityContextFacade = securityContextFacade;
        
    }

    public void doSomething(){
      SecurityContext context = securityContextFacade.getContext();
      // do something w/ context
    }
    
    @GetMapping("")
    String index( 
            
        Model model,
        @AuthenticationPrincipal Object principal, 
        WebRequest webRequest,
        HttpSession httpSession
        
        ) throws UserNotFoundInSessionException {
        model.addAttribute("userName", getUser(httpSession).getName());
        
        
        //logger.debug( userHelper.getFromSession( httpSession, principal ).toString() ); // not needed.
        
        return "admin/index";
        
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping("/index")
    String subIndex() {
        
        return "admin/index";
        
    }
    @RequestMapping("/index2")
    String subIndex2() {
        
        return "admin/index";
        
    }

	@Override
	protected void generateControllerPaths() {
		// TODO Auto-generated method stub
		
	}
    

}

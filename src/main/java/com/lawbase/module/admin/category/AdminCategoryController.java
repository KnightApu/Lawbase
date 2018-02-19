package com.lawbase.module.admin.category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.adhocmaster.controller.MvcUserController;
import com.adhocmaster.mongo.PersistenceException;
import com.adhocmaster.mongo.user.User;
import com.adhocmaster.mongo.user.UserNotFoundInSessionException;
import com.lawbase.taxonomy.Category;
import com.lawbase.taxonomy.CategoryRepository;
import com.lawbase.taxonomy.CategoryService;

@Controller
@RequestMapping( "/admin/category" )
public class AdminCategoryController extends MvcUserController {
    
    private static final Logger logger = LoggerFactory.getLogger( AdminCategoryController.class );
    
    private static final String viewRoot = "admin/category-";
    private static final String pathRoot = "/admin/category";

    @Autowired 
    CategoryService categoryService;
    @Autowired 
    CategoryRepository categoryRepository;
    

    public AdminCategoryController() {

        super(); 
        
    }
    
    protected void generateControllerPaths() {

        controllerPaths = new HashMap<>();

        controllerPaths.put( "index", pathRoot );
        controllerPaths.put( "add", pathRoot + "/add" );
        controllerPaths.put( "edit", pathRoot + "/edit?id=" );
        controllerPaths.put( "delete", pathRoot + "/delete?id=" );
        controllerPaths.put( "manage", pathRoot + "/manage" );        
        
    }
    

    @GetMapping
    public String index(

            Model model, 
            @RequestParam Map<String, String> params
            
            ) {

        return manage( model, params );
    }

    @GetMapping("/add")
    public String add(
            
            Model model, 
            HttpSession httpSession,
            @RequestParam Map<String, String> params
            
            ) {


        try {
             
            System.out.println( getUser( httpSession ) );
            
        } catch ( UserNotFoundInSessionException e ) {

            e.printStackTrace();
            
        }
        //System.out.println( params );
        
        String methodName = "add";
        String forEntity = params.get( "forEntity" );
        
        List<Category> entityCategories = categoryRepository.findByForEntity( forEntity );
        
        //System.out.println( entityCategories );
        
        
        model.addAttribute( "category", new Category() );
        model.addAttribute( "forEntityParam", forEntity );
        model.addAttribute( "entityCategories", entityCategories );
        
        addCommonModelAttributes( model, methodName );
        
        return viewRoot + "index";
    }

    @PostMapping("/add")
    public String add(
            
            @ModelAttribute Category category,
            Model model, 
            HttpSession httpSession,
            @RequestParam Map<String, String> params
            
        ) {

        System.out.println( params );
        
        try {
            
            User user = getUser( httpSession );
            category.setAuthor( user );
            categoryService.save( category );
            
            
        } catch ( UserNotFoundInSessionException | PersistenceException e ) {

            model.addAttribute( "error", e.getMessage() );
            
        }
        
        return add( model, httpSession, params);
    }

    public String edit() {

        // TODO Auto-generated method stub
        return null;
    }

    @GetMapping( "/manage" )
    public String manage(
            
            Model model, 
            @RequestParam Map<String, String> params
            
            ) {

        
        String methodName = "manage";
        String forEntity = params.get( "forEntity" );
        
        List<Category> entityCategories = categoryRepository.findByForEntityOrderByParentAsc( forEntity );
        
        model.addAttribute( "entityCategories", entityCategories );
        model.addAttribute( "forEntityParam", forEntity );
        
        addCommonModelAttributes( model, methodName );
        
        return viewRoot + "index";

    }
      

}

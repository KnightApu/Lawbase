package com.lawbase.module.admin.article;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.adhocmaster.controller.MvcUserController;
import com.book.simpleBook.SimpleBook;
import com.book.simpleBook.SimpleMissingBook;
import com.lawbase.article.Article;
import com.lawbase.article.ArticleRepository;

@Controller
@RequestMapping( "/admin/article" )
public class AdminArticleController  extends MvcUserController {
	
	private static final Logger logger = LoggerFactory.getLogger( AdminArticleController.class );

    private static final String viewRoot = "admin/article-";
    private static final String pathRoot = "/admin/article";
    
    @Autowired
    private ArticleRepository articleRepository;

	@Override
	protected void generateControllerPaths() {
		
		controllerPaths = new HashMap<>();

        controllerPaths.put( "index", pathRoot );
        controllerPaths.put( "add", pathRoot + "/add" );
        controllerPaths.put( "edit", pathRoot + "/edit?id=" );
        controllerPaths.put( "delete", pathRoot + "/delete?id=" );
        controllerPaths.put( "manage", pathRoot + "/manage" );   
		
	}
	
	@GetMapping( value = { "", "/", "/index" } )
    public String index(

            Model model, 
            @RequestParam Map<String, String> params
            
            ) {

        // TODO make is paginated
        
        List<Article> books = articleRepository.findAll();
        
        model.addAttribute( "books", books );
        
        addCommonModelAttributes( model, "index" );  
        
        return viewRoot + "index";
        
    }

	@GetMapping("/edit")
	public String edit(

			Model model, @RequestParam ObjectId id

	) {

		SimpleBook articleBook = articleRepository.findOne(id);

		if (null == articleBook)
			articleBook = new SimpleMissingBook();

		model.addAttribute("articleBook", articleBook);
		addCommonModelAttributes(model, "edit");

		return viewRoot + "index";

	}


}

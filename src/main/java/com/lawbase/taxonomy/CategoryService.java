package com.lawbase.taxonomy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adhocmaster.mongo.PersistenceException;

@Component
public class CategoryService {
    

    @Autowired 
    CategoryRepository categoryRepository;
    
    public Category save( Category category ) throws PersistenceException {
        
        //1. validate
        
        //2. save it
        
        Category updatedCategory = categoryRepository.save( category );
        
        if ( null == updatedCategory ) 
            throw new PersistenceException( "Category couldn't be saved due to DB error" );
        
        //3. add to parent
        
        Category parent = updatedCategory.getParent();
        
        if ( null != parent ) {

            List<Category> children = parent.getChildren();
            
            if ( ! children.contains( updatedCategory ) ) {
                
                children.add( updatedCategory );
                Category updatedParent = categoryRepository.save( parent );
                
                if ( null == updatedParent ) 
                    throw new PersistenceException( "Parent category couldn't be saved due to DB error" );
                
            }
            
        }
        
        return updatedCategory;
        
    }

}

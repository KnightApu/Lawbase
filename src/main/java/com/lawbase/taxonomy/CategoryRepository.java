package com.lawbase.taxonomy;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CategoryRepository extends MongoRepository< Category, ObjectId > {
    
    public Category findByName( String name );
    
    public List<Category> findByParent( Category parent );
    
    public List<Category> findByForEntity( String forEntity );
    
    public List<Category> findByForEntityOrderByParentAsc( String forEntity );
    
    @Query( "{ parent: null, forEntity: ?0 }" )
    public List<Category> findRootCategoriesOnly( String forEntity );
    
    public Category findById( ObjectId _id );

}

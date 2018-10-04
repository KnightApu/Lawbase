package com.book.simpleBook;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface SimpleBookRepository<T extends SimpleBook> extends MongoRepository<T, ObjectId>  {

    public List<T> findByTitle( String title );
    
    public Page<T> findByStatus( Status status, Pageable page );
    
    public Page<T> findByTitleContaining( String title, Pageable page );
    
}

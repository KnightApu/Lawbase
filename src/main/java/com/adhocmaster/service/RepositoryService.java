package com.adhocmaster.service;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public abstract class RepositoryService<E> {

    abstract public E findOne( ObjectId id );
    
    abstract public E save( E book );
    
    abstract public void delete( E book );

    abstract public Page<E> findAll( Pageable pageable );
    public Page<E> findAll( int offset, int size ) {

        int page = offset / size;
        PageRequest pageRequest = new PageRequest(page, size);
        
        return findAll( pageRequest );
        
    }
    
}

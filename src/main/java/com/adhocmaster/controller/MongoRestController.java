package com.adhocmaster.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adhocmaster.mongo.user.UserHelper;
import com.adhocmaster.service.RepositoryService;

import util.restApi.RestInternalServerException;

public abstract class MongoRestController<T> extends RestController {

    @Autowired
    protected UserHelper userHelper;

    abstract protected RepositoryService<T> getService();

    @RequestMapping( "/{id}" )
    public T getOne( @PathVariable ObjectId id ) throws RestInternalServerException {
        
        try {
                        
            return getService().findOne( id );
            
        } catch ( Exception e ) {

            throw new RestInternalServerException( e );
            
        }
        
    }
}

package com.adhocmaster.mongo.user;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface MongoUserRepository extends MongoRepository<User, ObjectId>{
    
    public User findByUserName( String userName );
    public User findById( String id);
    public List<User> findByStatus( String status);

}

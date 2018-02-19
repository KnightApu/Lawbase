package com.adhocmaster.user.role;

import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.adhocmaster.mongo.user.User;


public interface ExpirableCapabilityAuthorityRepository extends MongoRepository<ExpirableCapabilityAuthority, ObjectId> {
	
	public Set<ExpirableCapabilityAuthority> findByUser( User user );
	

}

package com.lawbase.court;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourtBookSearchRepository extends MongoRepository<CourtBookSearchProjection, ObjectId> {
    
	public Page<CourtBookSearchProjection> findAll(Pageable pageable);
    
}
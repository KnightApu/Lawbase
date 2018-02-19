package com.lawbase.journal;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalRepository extends MongoRepository<Journal, ObjectId>{
	public Page<Journal> findByTitle( String title, Pageable pageable );
}

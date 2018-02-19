package com.lawbase.court;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourtBookRepository extends MongoRepository<CourtBook, ObjectId> {
    
    public Page<CourtBook> findByTitleStartingWith( String title, Pageable pageable );
    



}

package com.lawbase.journal;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalManagementProjectionRepository extends MongoRepository< JournalManagementProjection, ObjectId> {

}

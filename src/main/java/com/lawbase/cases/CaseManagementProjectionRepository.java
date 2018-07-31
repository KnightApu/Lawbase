package com.lawbase.cases;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CaseManagementProjectionRepository extends MongoRepository<CaseManagementProjection, ObjectId> {

}

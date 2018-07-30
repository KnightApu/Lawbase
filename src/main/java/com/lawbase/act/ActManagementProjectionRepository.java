package com.lawbase.act;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ActManagementProjectionRepository  extends MongoRepository< ActManagementProjection, ObjectId >  {

}

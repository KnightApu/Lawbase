package com.lawbase.article;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleManagementProjectionRepository extends MongoRepository<ArticleManagementProjection, ObjectId> {

}

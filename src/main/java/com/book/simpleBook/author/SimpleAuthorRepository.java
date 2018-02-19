package com.book.simpleBook.author;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SimpleAuthorRepository extends MongoRepository<SimpleAuthor, ObjectId> {

}

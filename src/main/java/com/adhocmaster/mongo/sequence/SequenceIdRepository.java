package com.adhocmaster.mongo.sequence;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SequenceIdRepository extends MongoRepository<SequenceId, String> {

}

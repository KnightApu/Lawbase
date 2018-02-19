package com.adhocmaster.mongo.sequence;

public interface SequenceDao {

    long getNextSequenceId(String key) throws SequenceException;

}
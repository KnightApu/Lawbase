package com.adhocmaster.mongo.sequence;

import org.bson.types.BSONTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sequence")
public class SequenceId {

    @Id
    private String id;

    private long seq;
    
    private long lastUpdateMiliSecond;
    private BSONTimestamp lastModified;

    public long getSeq() {

        return seq;
        
    }

}
package com.lawbase.court;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import com.book.simpleBook.Status;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Document( collection = "courtBook" )
public class CourtBookSearchProjection {

    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String title;
    private Status status;
    
	public CourtBookSearchProjection(ObjectId id, String title, Status status) {
		super();
		this.id = id;
		this.title = title;
		this.status = status;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
    
    
    
}

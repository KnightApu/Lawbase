package com.lawbase.journal;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Document( collection = "journal" )
public class JournalManagementProjection {
	
	private final String title, status;
	
	@JsonSerialize(using = ToStringSerializer.class)
	private final ObjectId id;
	
	JournalManagementProjection( ObjectId id, String title, String status ) {
		
		super();
		this.id = id;
		this.title = title;
		this.status = status;
		
	}
	
	public ObjectId getId() {
		
		return this.id;
		
	}
	
	public String getStringIdFormat() {
		
		return id.toHexString();
		
	}
	
	public String getTitle() {
		
		return this.title;
		
	}
	
	public String getStatus() {
		
		return this.status;
		
	}
	
	

}

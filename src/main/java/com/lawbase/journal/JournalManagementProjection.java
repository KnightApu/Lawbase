package com.lawbase.journal;

import org.bson.types.ObjectId;

public class JournalManagementProjection {
	
	private final String title, status;
	private final ObjectId id;
	
	JournalManagementProjection( ObjectId id, String title, String status ) {
		
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

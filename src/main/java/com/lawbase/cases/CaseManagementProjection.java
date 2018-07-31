package com.lawbase.cases;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Document( collection = "case" )
public class CaseManagementProjection {

private final String title, status;
	
	@JsonSerialize(using = ToStringSerializer.class)
	private final ObjectId id;
	
	private final String courtBookTitle, volume;
	
	private final int year;
	
	
	CaseManagementProjection( ObjectId id, String title, String status, String courtBookTitle, int year, String volume ) {
		
		super();
		this.id = id;
		this.title = title;
		this.status = status;
		this.courtBookTitle = courtBookTitle;
		this.year = year;
		this.volume = volume;
		
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
	
	public String getcourtBookTitle() {
		
		return this.courtBookTitle;
		
	}

	public String getVolume() {
		
		return this.volume;
		
	}	
	
	public int getYear() {
		
		return this.year;
		
	}
	
}

package com.lawbase.cases;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class CaseManagementProjection {

private final String title, status;
	
	@JsonSerialize(using = ToStringSerializer.class)
	private final ObjectId id;
	
	private final String journalTitle, volume;
	
	private final int year;
	
	
	CaseManagementProjection( ObjectId id, String title, String status, String journalTitle, int year, String volume ) {
		
		super();
		this.id = id;
		this.title = title;
		this.status = status;
		this.journalTitle = journalTitle;
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
	
	public String getJournalTitle() {
		
		return this.journalTitle;
		
	}

	public String getVolume() {
		
		return this.volume;
		
	}	
	
	public int getYear() {
		
		return this.year;
		
	}
	
}

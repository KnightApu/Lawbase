package com.adhocmaster.user.role;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.adhocmaster.mongo.user.User;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Document
public class ExpirableCapabilityAuthority extends CapabilityAuthority {
	
	
	@JsonSerialize(using = ToStringSerializer.class)
	@Id
	ObjectId id;
	
	@DBRef
	@Indexed( unique = false )
	User user;
	
	@Indexed( name = "expireAt", expireAfterSeconds = 600   )
	Date expireAt;
		
	
	public ExpirableCapabilityAuthority()
	{
		super( null );
	}

	public ExpirableCapabilityAuthority( Capability capability ) {
		
		super( capability );
		
		expireAt = new Date();
		
		//TO-DO add user in constructor
		
	}
	
	public ExpirableCapabilityAuthority( User user, Capability capability ) {
		
		super( capability );
		
		expireAt = new Date();
		
		this.user = user;
		
	}
	
	public void setId( ObjectId id )
	{
		this.id = id;
	}
	
	public ObjectId getId()
	{
		return this.id;
	}
	
	public User getUser()
	{
		return this.user;
	}
	
	public void setUser( User user )
	{
		this.user = user;
	}
	
	
	public Date getExpireAt()
	{
		return this.expireAt;
	}
	
	public void setExpireAt( Date expireddate )
	{
		this.expireAt = expireddate;
	}
	
	public CapabilityAuthority getCapabilityAuthority() throws CapabilityNotFoundException
	{
		
		Capability capability = this.getCapability();
		
		return new CapabilityAuthority( capability );
		
		
	}
	
	
	
}

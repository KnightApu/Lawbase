package com.adhocmaster.mongo.user;

import java.io.Serializable;

import org.bson.types.ObjectId;

import com.adhocmaster.user.role.Role;

public class UserInfoMap implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private ObjectId id;
    
    private String name;
    
    private long numericId = 0;

    private String userName = "";
    
    private String email = "";
    
    private String passwordHash = "";
    
    private Role role = Role.USER;

    
    public ObjectId getId() {
    
        return id;
    }

    
    public void setId( ObjectId id ) {
    
        this.id = id;
    }

    
    public String getName() {
    
        return name;
    }

    
    public void setName( String name ) {
    
        this.name = name;
    }

    
    public long getNumericId() {
    
        return numericId;
    }

    
    public void setNumericId( long numericId ) {
    
        this.numericId = numericId;
    }

    
    public String getUserName() {
    
        return userName;
    }

    
    public void setUserName( String userName ) {
    
        this.userName = userName;
    }

    
    public String getEmail() {
    
        return email;
    }

    
    public void setEmail( String email ) {
    
        this.email = email;
    }

    
    public String getPasswordHash() {
    
        return passwordHash;
    }

    
    public void setPasswordHash( String passwordHash ) {
    
        this.passwordHash = passwordHash;
    }

    
    public Role getRole() {
    
        return role;
    }

    
    public void setRole( Role role ) {
    
        this.role = role;
    }


    public UserInfoMap( ObjectId id, String name, long numericId, String userName, String email, String passwordHash,
            Role role ) {
        super();
        this.id = id;
        this.name = name;
        this.numericId = numericId;
        this.userName = userName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }


    public UserInfoMap() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    
    
    

}

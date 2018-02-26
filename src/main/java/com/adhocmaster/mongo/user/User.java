package com.adhocmaster.mongo.user;

import java.util.Map;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.adhocmaster.user.role.Role;
import com.book.simpleBook.author.SimpleAuthor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document( collection = "user" )
@JsonIgnoreProperties( value = {
        "passwordHash"
} )
public class User extends SimpleAuthor {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static final String SEQ_KEY = "user";
    public static final String DEFAULT_PASS = "user";

    private long numericId = 0;

    @Indexed( unique = true )
    private String userName = "";

    @Indexed( unique = true )
    private String email = "";

    private String passwordHash = "";

    private Role role = Role.USER;

    private UserStatus status = UserStatus.ACTIVE;

    private String contactNo;

    private String occupation;

    private String institute;

    private int duePayment;
    
    private String ipAddress;

    public User( String name, long numericId, String userName ) {

        super( name );

        this.numericId = numericId;
        this.userName = userName;

        setDefaultPassword();

    }
   
	
    /**
     * protected constructor to be used by a builder or creator
     */
    protected User( String name ) {

        super( name );
        setDefaultPassword();

    }
    
  

    /**
     * Needed for mongo reflexion or serialization
     */
    public User() {

        super( "" );
        setDefaultPassword();

    }

    


	private void setDefaultPassword() {

        try {

            this.passwordHash = PasswordHelper.getHash( DEFAULT_PASS );

        } catch ( PasswordException e ) {

            this.passwordHash = "";

        }

    }
    
   
    
    public String getUserName() {

        return userName;
    }

    public void setUserName( String userName ) {

        this.userName = userName;
    }

    public long getNumericId() {

        return numericId;
    }

    /**
     * protected to be used by a builder or creator
     */
    protected void setNumericId( long numericId ) {

        this.numericId = numericId;
    }

    public String getPasswordHash() {

        return passwordHash;
    }

    public void setPasswordHash( String passwordHash ) {

        this.passwordHash = passwordHash;

    }
    
    public void setPasswordHashFromPassword( String password ) {

        try {
            this.passwordHash = PasswordHelper.getHash( password );

        } catch ( PasswordException e ) {

            this.passwordHash = "";

        }

    }

    public String getEmail() {

        return email;
    }

    public void setEmail( String email ) {

        this.email = email;
    }

    public Role getRole() {

        return role;
    }

    public void setRole( Role role ) {

        this.role = role;
    }

    public UserStatus getStatus() {

        return this.status;
    }

    public void setStatus( UserStatus status ) {

        this.status = status;
    }

    public String getContactNo() {

        return this.contactNo;
    }

    public void setContactNo( String contactNo ) {

        this.contactNo = contactNo;
    }

    public String getOccupation() {

        return this.occupation;
    }

    public void setOccupation( String occupation ) {

        this.occupation = occupation;
    }

    public String getInstitute() {

        return this.institute;
    }

    public void setInstitute( String institute ) {

        this.institute = institute;
    }

    public int getDuePayment() {

        return this.duePayment;

    }

    public void setDuePayment( int duePayment ) {

        this.duePayment = duePayment;

    }
    
    public String getIpAddress() {

        return this.ipAddress;

    }

    public void setIpAddress( String ipAddress ) {

        this.ipAddress = ipAddress;

    }

    @Override
    public String toString() {

        return "User [numericId=" + numericId + ", userName=" + userName + ", email=" + email + ", passwordHash="
                + passwordHash + ", role=" + role + ", status=" + status + ", contactNo=" + contactNo + ", occupation="
                + occupation + ", institute=" + institute + ", duePayment=" + duePayment + "]";
    }

    // public UserInfoMap getUserInfoMap() {
    //
    // UserInfoMap infoMap = new UserInfoMap();
    //
    // infoMap.setId( getId() );
    // infoMap.setName( getName() );
    //
    // infoMap.setUserName( userName );
    // infoMap.setEmail( email );
    // infoMap.setNumericId( numericId );
    // infoMap.setPasswordHash( passwordHash );
    // infoMap.setRole( role );
    //
    // return infoMap;
    //
    // }

}

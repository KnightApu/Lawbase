package com.adhocmaster.mongo.user;

import com.adhocmaster.mongo.PersistenceException;
import com.adhocmaster.mongo.sequence.SequenceDao;
import com.adhocmaster.user.role.Role;

public class UserCreator {

    private SequenceDao sequenceDao;
    private MongoUserRepository userRepository;
    
    private User user;
    
    
    public UserCreator( SequenceDao sequenceDao, MongoUserRepository userRepository,  String name, String userName, String email ) {
        
        this.sequenceDao = sequenceDao;
        this.userRepository = userRepository;
        
        user = new User( name );
        user.setUserName( userName );
        user.setEmail( email );
        
    }

    
    


	public void setName( String name ) {
    
        user.setName( name );
    }

        
    public void setUserName( String userName ) {

        user.setUserName( userName );
    }

    public void setEmail( String email ) {
    
        user.setEmail( email );
    }
    public void setRole( Role role ) {
    
        user.setRole( role );
    }
    public void setContactNo(String mobile ) {
        
        user.setContactNo(mobile);
    }
    public void setOccupation(String occupation ) {
        
        user.setOccupation(occupation);
    }
    public void setInstitute(String institute ) {
        
        user.setInstitute(institute);
    }
    public void setPassword( String password ) throws PasswordException {
    
        user.setPasswordHash( PasswordHelper.getHash( password ) );
    }
    public void setIpAddress( String ipAddress ) {
		
    	user.setIpAddress( ipAddress );
	}
    
    /**
     * creates and persists user
     * @return
     * @throws PersistenceException
     */
    public User createAndPersist() throws PersistenceException {
        
        try {
            

            long numericId = sequenceDao.getNextSequenceId( User.SEQ_KEY );
                        
            user.setNumericId( numericId );
            
            System.out.println( user );
            userRepository.save( user );
            
            
            return user;
            
            
        } catch ( Exception e ) {
            
            throw new PersistenceException( "User creation failed", e );
            
        }
        
        
    }


	
    
    

}

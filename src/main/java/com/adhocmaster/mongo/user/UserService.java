package com.adhocmaster.mongo.user;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adhocmaster.mongo.PersistenceException;
import com.adhocmaster.mongo.sequence.SequenceDao;
import com.adhocmaster.service.RepositoryService;
import com.adhocmaster.user.role.Role;
import com.adhocmaster.user.role.RoleNotFoundException;
import com.utility.form.FormValidationException;
import javassist.NotFoundException;

@Service
public class UserService extends RepositoryService<User> {

    @Autowired
    MongoUserRepository userRepository;

    @Autowired
    SequenceDao sequenceDao;
	
	public User createUser( String name, String userName, String email, String role ) throws RoleNotFoundException, PersistenceException
	{
		
		UserCreator userCreator = new UserCreator( sequenceDao, userRepository, name, userName,  email );
		
		User user = userCreator.createAndPersist() ;
		
		user.setRole( Role.findByName( role ) );
		
		save( user );
		
		return user;
		
	}
	
	
	public User createUserfromRegistrationForm(String name, String userName, String email, String contactNo,
			String password, String occupation, String institute) throws PasswordException, PersistenceException {
		
		UserCreator userCreator = new UserCreator( sequenceDao, userRepository, name, userName,  email );
		userCreator.setContactNo(contactNo);
		userCreator.setPassword(password);
		userCreator.setOccupation(occupation);
		userCreator.setInstitute(institute);
		userCreator.setRole(Role.INDIVIDUAL);
		
		User user = userCreator.createAndPersist();
		
		return user;
	}

	
	
	public User save( User user )
	{
		return userRepository.save( user );
		
	}
	
	@Override
	public int getSizeOfRepository() {
		
		return (int) userRepository.count();
	} 

    public User createUser( String name, String userName, String email, String role, String password )
            throws RoleNotFoundException, PasswordException, PersistenceException {

        UserCreator userCreator = new UserCreator( sequenceDao, userRepository, name, userName, email );

        userCreator.setRole( Role.findByName( role ) );
        
        userCreator.setPassword(password);

        User user = userCreator.createAndPersist();

        return user;

    }
    
    public User createUserWithIpAddress( String name, String userName, String email, String role, String password, String ipAddress )
    		 	throws RoleNotFoundException, PasswordException, PersistenceException{
    	
    	 UserCreator userCreator = new UserCreator( sequenceDao, userRepository, name, userName, email );

         userCreator.setRole( Role.findByName( role ) );

         userCreator.setIpAddress( ipAddress );
         
         User user = userCreator.createAndPersist();

         return user;
    	
    }



    @Override
    public User findOne( ObjectId id ) {

        return userRepository.findOne( id );

    }

    @Override
    public void delete( User user ) {

        userRepository.delete( user );

    }

    @Override
    public Page<User> findAll( Pageable pageable ) {

        return userRepository.findAll( pageable );

    }

    public User findByUserName( String userName ) {

        return userRepository.findByUserName( userName );
    }
    
    public List<User> findByStatus( String status ) {

        return  userRepository.findByStatus(status);
    }
    
    public User findById( String id ) {
    	
    	return userRepository.findById(id);
    }

    /**
     * 
     * @param userCreatorId
     * @param params
     *            name, userName, email, role, password
     * @throws PersistenceException
     * @throws RoleNotFoundException
     * @throws UserException
     * @throws PasswordException
     * @throws FormValidationException 
     */
    public User addFromFormData( ObjectId userCreatorId, Map<String, String> params )
            throws PersistenceException, UserException, FormValidationException {


        try {

            if ( StringUtils.isBlank( params.get( "userName" ) ) )
                throw new FormValidationException( "userName not set" );

            if ( StringUtils.isBlank( params.get( "password" ) ) )
                throw new FormValidationException( "password not set" );

            String name = "", userName = "", email = "", role = "", password = "", ipAddress = "";

            if ( StringUtils.isNotBlank( params.get( "name" ) ) ) {

                name = params.get( "name" );

            }
            if ( StringUtils.isNotBlank( params.get( "userName" ) ) ) {

                userName = params.get( "userName" );

            }
            if ( StringUtils.isNotBlank( params.get( "email" ) ) ) {

                email = params.get( "email" );

            }
           

            if ( StringUtils.isNotBlank( params.get( "password" ) ) ) {

                password = params.get( "password" );

            }
            
            if ( StringUtils.isNotBlank( params.get( "role" ) ) ) {

                role = params.get( "role" );
                
                if( role.equals( "ENTERPRISE" ) ){
                    
                	if ( StringUtils.isNotBlank( params.get( "ipAddress" ) ) ) {

                        ipAddress = params.get( "ipAddress" );
                        
                        return createUserWithIpAddress( name, userName, email, role, password, ipAddress );

                    }                	
                	
                }                	

            }
            
            return createUser( name, userName, email, role, password );
            
            
        } catch ( RoleNotFoundException |  PasswordException e ) {
            
            throw new FormValidationException( e.getMessage() );
            
        } 

    }

    /**
     * 
     * @param userId
     * @param params
     *            updateType: basicInformation, services
     * @throws PersistenceException
     * @throws NotFoundException
     * @throws FormValidationException
     */
    public User updateFromFormData( ObjectId userId, Map<String, String> params )
            throws PersistenceException, FormValidationException, NotFoundException {

        if ( StringUtils.isBlank( params.get( "updateType" ) ) )
            throw new FormValidationException( "no update type set" );

        User user = userRepository.findOne( userId );

        switch ( params.get( "updateType" ) ) {

            case "basicInformation" :

                updateBasicInformation( userId, params );

                break;

            case "services" :

            	updateServices( userId, params );

                break;
        }

        return user;

    }

    /**
     * 
     * @param caseId
     * @param params
     *            firstParty, secondParty, sources, keywords
     * @throws FormValidationException
     * @throws NotFoundException
     * @throws PersistenceException
     */
    public User updateBasicInformation( ObjectId userId, Map<String, String> params )
            throws FormValidationException, NotFoundException, PersistenceException {

        User user = findOne( userId );

        if ( StringUtils.isNotBlank( params.get( "name" ) ) ) {

            user.setName( params.get( "name" ) );

        }
        if ( StringUtils.isNotBlank( params.get( "email" ) ) ) {

            user.setEmail( params.get( "email" ) );

        }
        if ( StringUtils.isNotBlank( params.get( "contactNo" ) ) ) {

            user.setContactNo( params.get( "contactNo" ) );

        }
        if ( StringUtils.isNotBlank( params.get( "occupation" ) ) ) {

            user.setOccupation( params.get( "occupation" ) );

        }
        if ( StringUtils.isNotBlank( params.get( "institute" ) ) ) {

            user.setInstitute( params.get( "institute" ) );

        }

        if ( StringUtils.isNotBlank( params.get( "status" ) ) ) {

            String userStatus = params.get( "status" );

            UserStatus status = UserStatus.valueOf( userStatus );

            user.setStatus( status );

        }

        try {

            save( user );

        } catch ( Exception e ) {

            throw new PersistenceException( e.getMessage() );

        }
        return user;

    }

    public void updateServices( ObjectId userId, Map<String, String> params )
            throws FormValidationException, NotFoundException, PersistenceException {

        User user = findOne( userId );

        if ( StringUtils.isNotBlank( params.get( "duePayment" ) ) ) {

            user.setDuePayment( Integer.parseInt( params.get( "duePayment" ) ) );

        }

        try {

            save( user );

        } catch ( Exception e ) {

            throw new PersistenceException( e.getMessage() );

        }

    }


	public User registrationFromFormData(Map<String, String> params) throws PasswordException, PersistenceException {
		
		if ( StringUtils.isBlank( params.get( "userName" ) ) )
            throw new PersistenceException( "userName not set" );
		
		if ( StringUtils.isBlank( params.get( "email" ) ) )
            throw new PersistenceException( "Email not set" );
		
		if ( StringUtils.isBlank( params.get( "password" ) ) )
            throw new PersistenceException( "password not set" );
    	
    	String name = "", userName = "", email = "", role = "", contactNo = "", institute = "", password = "", occupation = "";
		
    	if ( StringUtils.isNotBlank( params.get( "name" ) ) ) {
            
            name = params.get( "name" );
            
            
        }
    	if ( StringUtils.isNotBlank( params.get( "userName" ) ) ) {
            
    		userName = params.get( "userName" );
            
            
        }
        if ( StringUtils.isNotBlank( params.get( "email" ) ) ) {
            
        	email = params.get( "email" );
            
        }
        if ( StringUtils.isNotBlank( params.get( "password" ) ) ) {
            
        	password = params.get( "password" );
            
            
        }
        if ( StringUtils.isNotBlank( params.get( "mobile" ) ) ) {
            
        	contactNo = params.get( "mobile" );
        	
        }
        if ( StringUtils.isNotBlank( params.get( "occupation" ) ) ) {
            
        	occupation = params.get( "occupation" );
        	
        }
        if ( StringUtils.isNotBlank( params.get( "institute" ) ) ) {
    
        	institute = params.get( "institute" );
	
        }
			
			User user = createUserfromRegistrationForm(name, userName, email, contactNo, password, occupation, institute);
			return user;
		
	}



	


	

	
}

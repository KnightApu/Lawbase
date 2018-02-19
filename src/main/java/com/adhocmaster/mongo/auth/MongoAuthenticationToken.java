package com.adhocmaster.mongo.auth;

import java.util.Collection;

import org.bson.types.ObjectId;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;

public class MongoAuthenticationToken extends UsernamePasswordAuthenticationToken implements UserDetails{

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    public MongoAuthenticationToken( Object principal, Object credentials ) {
        super( principal, credentials );
    }

    public MongoAuthenticationToken( Object principal, Object credentials,
            Collection<? extends GrantedAuthority> authorities ) {
        super( principal, credentials, authorities );
    }
    
    public ObjectId getUserId() {
        
        return new ObjectId( getPrincipal().toString() );
        
    }

    @Override
    public String getPassword() {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getUsername() {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {

        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {

        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEnabled() {

        // TODO Auto-generated method stub
        return false;
    }

}

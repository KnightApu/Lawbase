package com.adhocmaster.mongo.auth;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.adhocmaster.mongo.user.User;

public class MongoAuthentication implements Authentication {

    User user;
    @Override
    public String getName() {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getCredentials() {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getDetails() {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getPrincipal() {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isAuthenticated() {

        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setAuthenticated( boolean isAuthenticated ) throws IllegalArgumentException {

        // TODO Auto-generated method stub
        
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( user == null ) ? 0 : user.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {

        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        MongoAuthentication other = ( MongoAuthentication ) obj;
        if ( user == null ) {
            if ( other.user != null )
                return false;
        } else if ( !user.equals( other.user ) )
            return false;
        return true;
    }

}

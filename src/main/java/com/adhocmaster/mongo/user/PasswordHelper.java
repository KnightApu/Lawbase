package com.adhocmaster.mongo.user;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHelper {
    
    public static String getHash( String password ) throws PasswordException {
        
        byte[] bytes;
        try {
            
            bytes = password.getBytes("UTF-8");
            
        } catch ( UnsupportedEncodingException e ) {

            throw new PasswordException( "Password don't have UTF-8 encoding", e );
            
        }
        
        MessageDigest md;
        
        try {
            
            md = MessageDigest.getInstance( "MD5" );
            
            
        } catch ( NoSuchAlgorithmException e ) {

            throw new PasswordException( "MD5 hash algorithm not found", e );
            
        }
        
        byte[] theDigest = md.digest( bytes );
        
        try {
            
            return new String( theDigest, "UTF-8" );
            
        } catch ( UnsupportedEncodingException e ) {

            throw new PasswordException( "Cannot convert md5 hash to string", e );
            
        } 
        
    }
    
    public static boolean check( String password, String hash ) throws PasswordException {
        
        String newHash = getHash( password );
        
        if ( newHash.equals( hash ) )
            return true;
        
        return false;
        
    }

}

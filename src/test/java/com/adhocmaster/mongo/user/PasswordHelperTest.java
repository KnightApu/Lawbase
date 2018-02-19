package com.adhocmaster.mongo.user;

import static org.junit.Assert.*;

import org.junit.Test;


public class PasswordHelperTest {

    @Test
    public void test() {

        try {
            
            String password = "Lsdfkj*9";
            String hash = PasswordHelper.getHash( password );
            
            System.out.println( hash );
            
            assertTrue( PasswordHelper.check( password, hash ) );
            
            password = "আমি ভালো আছি";
            
            hash = PasswordHelper.getHash( password );
            
            assertTrue( PasswordHelper.check( password, hash ) );
            
        } catch ( PasswordException e ) {
            
            e.printStackTrace();
            fail();
            
        }
        
    }

}

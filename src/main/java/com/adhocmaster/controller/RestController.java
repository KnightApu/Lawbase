package com.adhocmaster.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import util.restApi.RestBadDataException;
import util.restApi.RestError;
import util.restApi.RestInternalServerException;

public class RestController {

    @ResponseStatus( HttpStatus.BAD_REQUEST )
    @ExceptionHandler( RestBadDataException.class )
    @ResponseBody RestError
    handleBadRequest( HttpServletRequest request, Exception ex ) {
        
        Throwable cause = ex.getCause();
        return new RestError( cause.getClass().getCanonicalName(), cause.getMessage() );
        
    }
    
    @ResponseStatus( HttpStatus.FORBIDDEN )
    @ExceptionHandler( AccessDeniedException.class )
    @ResponseBody RestError
    handleAccessDeniedRequest( HttpServletRequest request, Exception ex ) {
        
        //Throwable cause = ex.getCause();
        return new RestError( ex.getClass().getCanonicalName(), ex.getMessage() );
        
    }
    

    @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
    @ExceptionHandler( RestInternalServerException.class )
    @ResponseBody RestError
    handleInternalServerError( HttpServletRequest request, Exception ex ) {
        
        Throwable cause = ex.getCause();
        return new RestError( cause.getClass().getCanonicalName(), cause.getMessage() );
        
    }
    
    public void throwException( Throwable e ) throws RestInternalServerException {
        
        throw new RestInternalServerException( e );
        
    }

}

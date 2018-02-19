package com.adhocmaster.mongo.sequence;


public class SequenceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String errMsg;

    public SequenceException(String errMsg) {
        this.errMsg = errMsg;
    }

    
    public String getErrMsg() {
    
        return errMsg;
    }
    
    

}
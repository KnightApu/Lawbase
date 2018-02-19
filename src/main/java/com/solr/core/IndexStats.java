package com.solr.core;

import java.time.Instant;
import java.util.Date;

public class IndexStats {

    long recordsIndexed = 0;
    long totalRecords = 0;
    Date startTime;
    Date endTime;
    
    String status = "NO_STATUS";
    
    public long getRecordsIndexed() {
    
        return recordsIndexed;
    }
    
    public void setRecordsIndexed( long recordsIndexed ) {
    
        this.recordsIndexed = recordsIndexed;
    }
    
    public long getTotalRecords() {
    
        return totalRecords;
    }
    
    public void setTotalRecords( long totalRecords ) {
    
        this.totalRecords = totalRecords;
    }
    
    public Date getStartTime() {
    
        return startTime;
    }
    
    public void setStartTime( Date startTime ) {
    
        this.startTime = startTime;
    }
    
    public Date getEndTime() {
    
        return endTime;
    }
    
    public void setEndTime( Date endTime ) {
    
        this.endTime = endTime;
    }
    
    
    
    
    public String getStatus() {
    
        return status;
    }

    
    public void setStatus( String status ) {
    
        this.status = status;
    }

    public void start() {
        
        setStatus( "STARTED" );
        this.startTime = Date.from( Instant.now());
        
    } 
    
    public void end() {
        
        setStatus( "ENDED" );
        this.endTime = Date.from( Instant.now() );
    }
    
    public void incIndexRecords( long increment ) {
        
        this.recordsIndexed += increment;
        
    }

    public void reset() {
        
        setStatus( "RESETTED" );
        this.totalRecords = 0;
        this.recordsIndexed = 0;
        this.startTime = null;
        this.endTime = null;
        
    }
}

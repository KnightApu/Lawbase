package com.adhocmaster.controller;

import java.util.List;

import org.springframework.data.domain.Page;

public class DataTableResponseEntity<T> {

    public long iTotalRecords;
    public long iTotalDisplayRecords;
    public int sEcho;
    public List <T> aaData;
    
    public DataTableResponseEntity( long iTotalRecords, long iTotalDisplayRecords, int sEcho, List<T> aaData ) {
        
        super();
        this.iTotalRecords = iTotalRecords;
        this.iTotalDisplayRecords = iTotalDisplayRecords;
        this.sEcho = sEcho;
        this.aaData = aaData;
        
    }
    
    public DataTableResponseEntity( Page<T> entityPage, int sEcho  ) {

        this.iTotalRecords = entityPage.getTotalElements();
        this.iTotalDisplayRecords = entityPage.getTotalElements();
        this.sEcho = sEcho;
        this.aaData = entityPage.getContent();
        
    }
    
    
}

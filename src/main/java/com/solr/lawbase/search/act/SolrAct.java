package com.solr.lawbase.search.act;

import org.apache.solr.client.solrj.beans.Field;

import com.book.simpleBook.SolrSimpleBook;

public class SolrAct extends SolrSimpleBook {

    @Field
    private int year;

    @Field
    private String number;


    @Field
    private String description = "";
    
    @Field
    private String sections = "";

    
    public int getYear() {
    
        return year;
    }

    
    public void setYear( int year ) {
    
        this.year = year;
    }

    
    public String getNumber() {
    
        return number;
    }

    
    public void setNumber( String number ) {
    
        this.number = number;
    }

    
    public String getDescription() {
    
        return description;
    }

    
    public void setDescription( String actDescription ) {
    
        this.description = actDescription;
    }

    
    public String getSections() {
    
        return sections;
    }

    
    public void setSections( String sections ) {
    
        this.sections = sections;
    }


    @Override
    public String toString() {

        return "SolrAct [year=" + year + ", number=" + number + ", actDescription=" + description + ", sections="
                + sections + ", getId()=" + getId() + ", getTitle()=" + getTitle() + ", getAuthorId()=" + getAuthorId()
                + ", toString()=" + super.toString() 
                + "]";
    }


    
    
}

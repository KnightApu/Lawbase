package com.solr.lawbase.search.cases;

import java.util.List;

import org.apache.solr.client.solrj.beans.Field;
import com.book.simpleBook.SolrSimpleBook;

public class SolrCase extends SolrSimpleBook {

    
    @Field
    public
    String firstParty = "";
    
    @Field
    public
    String secondParty = "";

    @Field
    public
    int year;

    @Field
    public
    String courtBookTitle = "";
    @Field
    public
    List<String> sources;
    @Field
    public
    List<String> keywords;
    @Field
    public
    String subject = "";
    
    @Field
    public
    String summary = "";
    @Field
    public
    String transcript = "";
    
    
    public String getFirstParty() {
    
        return firstParty;
    }
    
    public void setFirstParty( String firstParty ) {
    
        this.firstParty = firstParty;
    }
    
    public String getSecondParty() {
    
        return secondParty;
    }
    
    public void setSecondParty( String secondParty ) {
    
        this.secondParty = secondParty;
    }
    
    public int getYear() {
    
        return year;
    }
    
    public void setYear( int year ) {
    
        this.year = year;
    }
    
    public String getCourtBookTitle() {
    
        return courtBookTitle;
    }
    
    public void setCourtBookTitle( String courtBookTitle ) {
    
        this.courtBookTitle = courtBookTitle;
    }
    
    public List<String> getSources() {
    
        return sources;
    }
    
    public void setSources( List<String> sources ) {
    
        this.sources = sources;
    }
    
    public List<String> getKeywords() {
    
        return keywords;
    }
    
    public void setKeywords( List<String> keywords ) {
    
        this.keywords = keywords;
    }
    
    public String getSubject() {
    
        return subject;
    }
    
    public void setSubject( String subject ) {
    
        this.subject = subject;
    }
    
    public String getSummary() {
    
        return summary;
    }
    
    public void setSummary( String summary ) {
    
        this.summary = summary;
    }
    
    public String getTranscript() {
    
        return transcript;
    }
    
    public void setTranscript( String transcript ) {
    
        this.transcript = transcript;
    }

    @Override
    public String toString() {

        return "SolrCase [firstParty=" + firstParty + ", secondParty=" + secondParty + ", year=" + year
                + ", courtBookTitle=" + courtBookTitle + ", sources=" + sources + ", keywords=" + keywords
                + ", subject=" + subject + ", summary=" + summary + ", transcript=" + transcript + "]";
    }
    
    
}

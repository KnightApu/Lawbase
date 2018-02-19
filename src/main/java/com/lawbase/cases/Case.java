package com.lawbase.cases;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.beans.Field;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.solr.core.mapping.SolrDocument;

import com.book.Author;
import com.book.BookNode;
import com.book.simpleBook.SimpleBook;
import com.lawbase.citation.Citation;
import com.lawbase.court.CourtBook;
import com.mongo.media.Media;

@SolrDocument(solrCoreName="")
@Document
@CompoundIndexes(

    @CompoundIndex ( name = "unique_title", def = "{ title: 1 }", unique = true, background = true )
    
)
public class Case  extends SimpleBook {

    static final String _VS = "vs";

    @Field
    String firstParty = "";
    @Field
    String secondParty = "";

    @Field
    int year;

    String volume = "";
    
    @Field
    String courtBookTitle = "";
    
    ObjectId courtBookId = null;
    ObjectId yearId = null;
    ObjectId volumeId = null;

    @Field
    List<String> sources;
    @Field
    List<String> keywords;
    
    /** digest elements **/
    
    String caseNo = "";
    Date caseDate; 

    String judge = "";
    String counsels = "";
    
    @Field
    String subject = "";
    
    @Field
    String summary = "";
    
    String _abstract = "";
    String mentionedIn = "";
    String followed = "";
    String affirmed = "";
    
    Date appliedIn;
    String appliedBy = "";
    
    String disposition = "";

    @Field
    String transcript = "";
    
    List<Citation> citations;
    
    @DBRef
    List<Media> mediaSet;
    
    String reference = "";
    
    CaseTreatment caseTreatment = CaseTreatment.NA;

    public Case() {
        
        super( "No title", null );
        
    }


    public Case( String title, Author author ) {
        
        super( title, author );

        sources = new ArrayList<String>();
        keywords = new ArrayList<String>();
        citations = new ArrayList<Citation>();
        
        extractPartiesFromTitle();
        
    }
    

    private void extractPartiesFromTitle() {
        
        String title = getTitle();
        
        int vsIndex = StringUtils.indexOfIgnoreCase( title, _VS );
        
        if ( vsIndex >= 0  ) {
            
            firstParty = StringUtils.substring( title, 0, vsIndex );
            firstParty = firstParty.trim();
            
            secondParty = StringUtils.substring( title, vsIndex+2 );
            secondParty = secondParty.trim();
       
            
        } else {
            
            firstParty = title;
            
        }
        
    }
    
    @Override
    public String getURL() {

        return "/case/" + getId();

    }


    
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


    
    public List<String> getSources() {
    
        return sources;
    }


    
    public void setSources( List<String> sources ) {
    
        this.sources = sources;
    }


    
    
    public String getVolume() {
    
        return volume;
    }


    
    public void setVolume( String volume ) {
    
        this.volume = volume;
    }


    
    public String getCourtBookTitle() {
    
        return courtBookTitle;
    }


    
    public void setCourtBookTitle( String courtBookTitle ) {
    
        this.courtBookTitle = courtBookTitle;
    }


    
    public ObjectId getCourtBookId() {
    
        return courtBookId;
    }


    
    public void setCourtBookId( ObjectId courtBookId ) {
    
        this.courtBookId = courtBookId;
    }


    
    public ObjectId getYearId() {
    
        return yearId;
    }


    
    public void setYearId( ObjectId yearId ) {
    
        this.yearId = yearId;
    }


    
    public ObjectId getVolumeId() {
    
        return volumeId;
    }


    
    public void setVolumeId( ObjectId volumeId ) {
    
        this.volumeId = volumeId;
    }


    public List<String> getKeywords() {
    
        return keywords;
    }


    
    public void setKeywords( List<String> keywords ) {
    
        this.keywords = keywords;
    }


    
    public String getCaseNo() {
    
        return caseNo;
    }


    
    public void setCaseNo( String caseNo ) {
    
        this.caseNo = caseNo;
    }


    
    public Date getCaseDate() {
    
        return caseDate;
    }


    
    public void setCaseDate( Date caseDate ) {
    
        this.caseDate = caseDate;
    }


    
    public String getJudge() {
    
        return judge;
    }


    
    public void setJudge( String judge ) {
    
        this.judge = judge;
    }


    
    public String getCounsels() {
    
        return counsels;
    }


    
    public void setCounsels( String counsels ) {
    
        this.counsels = counsels;
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


    
    public String get_abstract() {
    
        return _abstract;
    }


    
    public void set_abstract( String _abstract ) {
    
        this._abstract = _abstract;
    }


    
    public String getMentionedIn() {
    
        return mentionedIn;
    }


    
    public void setMentionedIn( String mentionedIn ) {
    
        this.mentionedIn = mentionedIn;
    }


    
    public String getFollowed() {
    
        return followed;
    }


    
    public void setFollowed( String followed ) {
    
        this.followed = followed;
    }


    
    public String getAffirmed() {
    
        return affirmed;
    }


    
    public void setAffirmed( String affirmed ) {
    
        this.affirmed = affirmed;
    }


    
    public Date getAppliedIn() {
    
        return appliedIn;
    }


    
    public void setAppliedIn( Date appliedIn ) {
    
        this.appliedIn = appliedIn;
    }


    
    public String getAppliedBy() {
    
        return appliedBy;
    }


    
    public void setAppliedBy( String appliedBy ) {
    
        this.appliedBy = appliedBy;
    }


    
    public String getDisposition() {
    
        return disposition;
    }


    
    public void setDisposition( String disposition ) {
    
        this.disposition = disposition;
    }


    public String getTranscript() {
    
        return transcript;
    }


    
    public void setTranscript( String transcript ) {
    
        this.transcript = transcript;
    }


    
    public List<Citation> getCitations() {
    
        return citations;
    }


    
    public void setCitations( List<Citation> citations ) {
    
        this.citations = citations;
    }
    
    public void addCitation( Citation citation ) {
        
        this.citations.add( citation );
        
    }
    
    public void removeCitation( Citation citation ) {
        
        this.citations.remove( citation );
        
    }
    
    public void setMediaSet(List<Media> medias){
    	
    	this.mediaSet=medias;
    }
    
    public List<Media> getMediaSet(){
    	
    	return mediaSet;
    }
    
    public String getReference() {
        
        return reference;
    }


    
    public void setReference( String reference ) {
    
        this.reference = reference;
    }
    
    public void setTreatment(CaseTreatment caseTreatment)
    {
    	this.caseTreatment = caseTreatment;
    }
    
    public CaseTreatment getTreatment()
    {
    	if(caseTreatment == null)
    	{
    		caseTreatment = CaseTreatment.NA;
    	}
    	return caseTreatment;
    }
    
    
    public void populateCourtInformation( CourtBook courtBook, BookNode leafNode ) {

        // TODO Auto-generated method stub
        courtBookId = courtBook.getId();
        courtBookTitle = courtBook.getTitle();
        
        BookNode volumeNode = courtBook.findNode( leafNode.getParentId() );
        BookNode yearNode = courtBook.findNode( volumeNode.getParentId() );
        
        try {

            year = Integer.parseInt( yearNode.getTitle() );
            
        } catch ( NumberFormatException e ) {
            
            year = 0;
            
        }
        
        volume = volumeNode.getTitle();
        
        yearId = yearNode.getId();
        volumeId = volumeNode.getId();
        
        
    }

    public String getSourcesAsCSV() {
        
        return StringUtils.join( sources, ", " );
        
    }
    
    public String getKeywordsAsCSV() {
        
        return StringUtils.join( keywords, ", " );
        
    }
    
    public String getDateForInputMask( Date date ) {

        if ( null == date )
            return "";
        
        SimpleDateFormat df = new SimpleDateFormat( "dd-mm-yyyy" );
        
        return df.format( date );
        
    }

}

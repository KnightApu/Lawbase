package com.lawbase.act;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.book.Author;
import com.book.simpleBook.SimpleBook;
import com.book.simpleBook.Status;
import com.mongo.media.Media;

@Document
@CompoundIndexes(

@CompoundIndex( name = "unique_title", def = "{ title: 1 }", unique = true, background = true )

)
public class Act extends SimpleBook {

    private int year;

    private String number;

    Date datePublished;
    
    private String description = "";
    private String sections = "";
    
    private String searchTerms = "";
    private String subject = "";

    ActTreatment treatment = ActTreatment.NA;

    @DBRef
    List<Media> mediaSet;

    public Act( String title, Author author, String number ) {

        super( title, author );

        this.number = number;

    }

    public String getNumber() {

        return number;
    }

    public void setNumber( String number ) {

        this.number = number;
    }

    public int getYear() {

        return year;
    }

    public void setYear( int year ) {

        this.year = year;
        
    }


    
    public Date getDatePublished() {
    
        return datePublished;
    }

    
    public void setDatePublished( Date datePublished ) {
    
        this.datePublished = datePublished;
    }


    
    public String getDescription() {
    
        return description;
    }

    
    public void setDescription( String description ) {
    
        this.description = description;
    }

    public String getSections() {

        return sections;
    }

    public void setSections( String sections ) {

        this.sections = sections;
    }

    public void setTreatment( ActTreatment actTreatment ) {

        this.treatment = actTreatment;
    }

    public ActTreatment getTreatment() {

        if ( treatment == null ) {
            treatment = ActTreatment.NA;
        }

        return treatment;
    }
    
 

    public void setMediaSet( List<Media> medias ) {

        this.mediaSet = medias;
    }

    public List<Media> getMediaSet() {

        return mediaSet;
    }

    public String getSearchTerms() {
		return searchTerms;
	}

	public void setSearchTerms(String searchTerms) {
		this.searchTerms = searchTerms;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDateForInputMask( Date date ) {

        if ( null == date )
            return "";

        SimpleDateFormat df = new SimpleDateFormat( "dd-mm-yyyy" );

        return df.format( date );

    }

    @Override
    public String getURL() {

        return "/act/" + getId();

    }

}

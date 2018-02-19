package com.book.simpleBook;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;

import com.solr.core.SolrEntity;

public class SolrSimpleBook extends SolrEntity {

    @Id
    @Field
    String id;
    
    @Field
    String title = "";
    
    @Field
    String authorId = "";

    
    public String getId() {
    
        return id;
    }

    
    public void setId( String id ) {
    
        this.id = id;
    }

    
    public String getTitle() {
    
        return title;
    }

    
    public void setTitle( String title ) {
    
        this.title = title;
    }


    
    public String getAuthorId() {
    
        return authorId;
    }


    
    public void setAuthorId( String authorId ) {
    
        this.authorId = authorId;
    }

    @Override
    public String toString() {

        return "SolrSimpleBook [id=" + id + ", title=" + title + ", authorId=" + authorId + "]";
    }
    
    
    
}

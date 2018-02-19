package com.lawbase.journal;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import com.book.Author;
import com.book.simpleBook.SimpleBook;

@Document
@CompoundIndexes(

    @CompoundIndex ( name = "unique_title", def = "{ title: 1 }", unique = true, background = true )
    
)

public class Journal extends SimpleBook {

	public Journal(String title, Author author) {
		super(title, author);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
    public String getURL() {

        return "/journal/" + getId();

    }

}

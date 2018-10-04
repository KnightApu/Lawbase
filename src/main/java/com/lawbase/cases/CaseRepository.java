package com.lawbase.cases;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.book.simpleBook.SimpleBookRepository;
import com.book.simpleBook.Status;

public interface CaseRepository extends SimpleBookRepository<Case> {

	public List<Case> findByFirstParty(String firstParty);
	public List<Case> findByCourtBookId(ObjectId id);
	
	public Page<Case> findByYear( int year, Pageable page );
	
	public Page<Case> findByCourtBookTitleContaining( String courtBookTitle , Pageable page );
	
	public List<Case> findByYear(int year);
	public List<Case> findByCourtBookTitleContaining( String courtBookTitle );
	
	//public Page<Case> findByTitleOrCourtBookTitle( String value, Pageable page );
	
	//public List<Case> findByTitleOrCourtBookTitle( String value );
}

package com.lawbase.cases;

import java.util.List;

import org.bson.types.ObjectId;

import com.book.simpleBook.SimpleBookRepository;

public interface CaseRepository extends SimpleBookRepository<Case> {

	public List<Case> findByFirstParty(String firstParty);
	public List<Case> findByCourtBookId(ObjectId id);
}

package com.lawbase.cases;

import java.util.List;

import com.book.simpleBook.SimpleBookRepository;

public interface CaseRepository extends SimpleBookRepository<Case> {

	public List<Case> findByFirstParty(String firstParty);
}

package com.lawbase.act;

import java.util.List;

import com.book.simpleBook.SimpleBookRepository;

public interface ActRepository extends SimpleBookRepository<Act> {
    
    public List<Act> findByTitle( String title );
    
}

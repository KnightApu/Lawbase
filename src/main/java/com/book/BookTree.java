package com.book;

import org.bson.types.ObjectId;

public interface BookTree {
    
    BookNode getRoot();
    
    BookNode find( ObjectId id );

}

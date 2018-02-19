package com.test.mongo;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes(

    @CompoundIndex( name = "fName", def = "{ 'firstName' : 1 }" )

)
public class PremiumCustomer extends Customer {

    public PremiumCustomer() {
        super();
        // TODO Auto-generated constructor stub
    }

    public PremiumCustomer( String firstName, String lastName ) {
        super( firstName, lastName );
        // TODO Auto-generated constructor stub
    }

    public void setFirstName( String firstName ) {

        super.setFirstName( firstName );

    }

}

package com.test.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PremiumCustomerRepository extends MongoRepository<PremiumCustomer, String> {

    public PremiumCustomer findByFirstName(String firstName);
    public List<PremiumCustomer> findByLastName(String lastName);

}

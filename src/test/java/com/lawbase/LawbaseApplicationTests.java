package com.lawbase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.mongo.PremiumCustomer;
import com.test.mongo.PremiumCustomerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource( locations = "classpath:application-test.properties" )
public class LawbaseApplicationTests {

	@Autowired
	private PremiumCustomerRepository repository;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testInheritance() {
		

		// TODO Auto-generated method stub
		repository.deleteAll();

		// save a couple of customers
		repository.save(new PremiumCustomer("Alice", "Smith"));
		repository.save(new PremiumCustomer("Bob", "Smith"));

		// fetch all customers
		System.out.println("PremiumCustomers found with findAll():");
		System.out.println("-------------------------------");
		for (PremiumCustomer customer : repository.findAll()) {
			System.out.println(customer);
		}
		System.out.println();

		// fetch an individual customer
		System.out.println("PremiumCustomer found with findByFirstName('Alice'):");
		System.out.println("--------------------------------");
		System.out.println(repository.findByFirstName("Alice"));

		System.out.println("PremiumCustomers found with findByLastName('Smith'):");
		System.out.println("--------------------------------");
		for (PremiumCustomer customer : repository.findByLastName("Smith")) {
			System.out.println(customer);
		}
		
	}
		
	

}

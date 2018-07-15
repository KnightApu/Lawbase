package com.lawbase.court;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.adhocmaster.mongo.sequence.SequenceDao;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource( locations = "classpath:application-test.properties" )
public class CourtBookSearchProjectionTest {
	
	@Autowired
    CourtBookService courtBookService;
	
	@Autowired
	CourtBookSearchRepository courtBookSearchRepository;
	
	@Test
    public void courtBookSearchProjectTest() {
    	
    	Page<CourtBookSearchProjection> courtPage = courtBookService.findAllSearchProjections(0, 10);
    	
    	assertNotNull(courtPage);
    }
	
	@Test
	public void courtBookSearchRepository() {
		
		 PageRequest pageRequest = new PageRequest(0, 10);
		 
		 assertNotNull(courtBookSearchRepository.findAll(pageRequest));
		
		
	}

}

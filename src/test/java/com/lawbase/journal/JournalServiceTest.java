package com.lawbase.journal;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource( locations = "classpath:application-farin.properties" )
public class JournalServiceTest {
	
	@Autowired
	JournalService journalService;
	
	@Test
	public void testManagementProjection()
	{
		Page<JournalManagementProjection>	journalPage = journalService.findAllManagementProjection( 0, 10 );
    	
    	assertNotNull( journalPage );
		
	}

}

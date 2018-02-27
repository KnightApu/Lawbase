package com.lawbase.act;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import com.adhocmaster.mongo.user.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource( locations = "classpath:application-apu.properties" )
public class ActTest {
	
	@Autowired
	ActService actService;
	@Autowired
	ActRepository actRepository;
	
	@Test
	public void test() {
		
		User user = new User();
		
		Act act = new Act("testAct", user, "12313");	
		actService.save(act);	
		Act act2 = (Act) actRepository.findByTitle("testAct");
		
		assertEquals(act, act2);
	}
	
	
	

}

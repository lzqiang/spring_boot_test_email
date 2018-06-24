package com.test.email.testEmail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ttpai.test.email.TestEmailApplication;
import com.ttpai.test.sms.service.SendSmsService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestEmailApplication.class)
@WebAppConfiguration
public class TestEmailApplicationTests {
	@Autowired
	private SendSmsService sendSmsService;
	@Test
	public void contextLoads() {
		System.out.println("aa");
	}
	@Test
	public void sms(){
		String result = sendSmsService.rrcLoginCode("15601750880");
		System.out.println(result);
	}
}

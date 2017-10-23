package com.chen;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.chen.entity.User;
import com.chen.express.QLExpressContext;
import com.chen.express.QlExpressUtil;
import com.chen.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QlSpringApplicationTests {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	QlExpressUtil qlExpressUtil ;
	
	@Test
	public void contextLoads() {
		List<User> findAll = userRepository.findAll();
		findAll.stream().forEach(System.err::println);
		
	}
	
	@Test
	public void testQlexpress(){
		QLExpressContext qlExpressContext = new QLExpressContext(null);
		
		Object result = qlExpressUtil.coreExecute(qlExpressContext, "userRepository.findAll();");
		System.err.println("- - - - - - - - -");
		System.err.println(result);
		System.err.println("- - - - - - - - -");

	}

}

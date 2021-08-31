package com.metalservices.mvc;

import com.metalservices.mvc.controllers.ServiceOrderController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AppTests {

	@Autowired
	private ServiceOrderController serviceOrderController;

	@Test
	void contextLoads() {
		assertThat(serviceOrderController).isNotNull();
	}

}

package com.metalservices.mvc;

import com.metalservices.mvc.controllers.ServiceOrderController;
import com.metalservices.mvc.entity.ServiceOrder;
import com.metalservices.mvc.services.ServiceOrderServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ServiceOrderController.class)
public class AppTests {

	@Autowired
	private ServiceOrderController serviceOrderController;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ServiceOrderServices service;

	@Test
	public void greetingShouldReturnMessageFromService() throws Exception {
		List<ServiceOrder> serviceOrders = new ArrayList<ServiceOrder>();
		ServiceOrder serviceOrder1 = new ServiceOrder(1L, "123", LocalDateTime.now());
		ServiceOrder serviceOrder2 = new ServiceOrder(2L, "231", LocalDateTime.now());
		serviceOrders.add(serviceOrder1);
		serviceOrders.add(serviceOrder2);

		when(service.list()).thenReturn(serviceOrders);

		this.mockMvc.perform(get("/service-orders/")).andDo(print()).andExpect(status().isOk());
	}
}

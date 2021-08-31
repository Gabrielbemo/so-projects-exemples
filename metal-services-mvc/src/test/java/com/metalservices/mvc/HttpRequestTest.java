//package com.metalservices.mvc;
//
//import com.metalservices.mvc.controllers.dtos.out.ListServiceOrderResponseDTO;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.ResponseEntity;
//
//import java.util.ArrayList;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class HttpRequestTest {
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//    private Object ListServiceOrderResponseDTO;
//
//    @Test
//    public void greetingShouldReturnDefaultMessage() throws Exception {
//
//        //ResponseEntity<List<ListServiceOrderResponseDTO>> responseEntity = this.restTemplate.getForEntity("http://localhost:" + port + "/service-orders",
//        //        List<ListServiceOrderResponseDTO>);
//
//
//        //ResponseEntity<List<ListServiceOrderResponseDTO>> responseEntity =
//        //        restTemplate.exchange("http://localhost:" + port + "/service-orders",
//        //                HttpMethod.GET, null, new ParameterizedTypeReference<List<ListServiceOrderResponseDTO>>() {
//        //                });
//
//        //List<ListServiceOrderResponseDTO> list = responseEntity.getBody();
//
//        ResponseEntity<? extends ArrayList<ListServiceOrderResponseDTO>> responseEntity2 = restTemplate.getForEntity("http://localhost:" + port + "/service-orders", (Class<? extends ArrayList<ListServiceOrderResponseDTO>>) ArrayList.class);
//    }
//}

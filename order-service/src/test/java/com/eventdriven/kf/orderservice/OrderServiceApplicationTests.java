//package com.eventdriven.kf.orderservice;
//
//import com.eventdriven.kf.orderservice.dto.OrderEvent;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.kafka.core.KafkaTemplate;
//
//import static org.mockito.Mockito.mock;
//
//@SpringBootTest
//class OrderServiceApplicationTests {
//
//	@TestConfiguration(proxyBeanMethods = false)
//	static class TestConfig {
//
//		@Bean
//		KafkaTemplate<String, OrderEvent> kafkaTemplate() {
//			return mock(KafkaTemplate.class);
//		}
//	}
//
//	@Test
//	void contextLoads() {
//	}
//
//}

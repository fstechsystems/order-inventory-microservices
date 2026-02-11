package com.company.sales.order;

import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderServiceApplicationTests {

	@Test
	void testMainMethodWithoutException() {
		try (MockedStatic<SpringApplication> mockedSpringApplication = mockStatic(SpringApplication.class)) {
			OrderServiceApplication.main(new String[] {});
			mockedSpringApplication
					.verify(() -> SpringApplication.run(OrderServiceApplication.class, new String[] {}));
		}
	}
}
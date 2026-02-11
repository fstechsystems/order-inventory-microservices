package com.company.supply.inventory;

import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InventoryServiceApplicationTests {

    @Test
    void testMainMethodWithoutException() {
        try (MockedStatic<SpringApplication> mockedSpringApplication = mockStatic(SpringApplication.class)) {
            InventoryServiceApplication.main(new String[] {});
            mockedSpringApplication
                    .verify(() -> SpringApplication.run(InventoryServiceApplication.class, new String[] {}));
        }
    }
}
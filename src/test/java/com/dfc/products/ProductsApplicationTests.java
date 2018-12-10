package com.dfc.products;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProductsApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ProductsApplicationTests {

    @Test
    public void contextLoads() {
    }

}

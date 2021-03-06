package com.bol.reactornetty.leakdetection.leakdetection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class SampleControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void showByteBufLeak() {
        for (int i = 0; i < 250; i++) {
            System.out.println("Request: " + (i+1));
            restTemplate.getForEntity("/json", String.class);
        }
    }
}
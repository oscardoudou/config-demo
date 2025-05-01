// src/test/java/com/example/configdemo/EnvVarTest.java
package com.example.configdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("embedded")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EnvVarTest {

    @Autowired
    private MyConfig myConfig;

    @Test
    public void testEnvVarFromBean() {
//        System.out.println("Resolved MY_VAR via bean: " + myConfig.getMyVar());
        System.out.println("Resolved MY_ENV_VAR via bean: " + myConfig.getMyEnvVar());
    }
}

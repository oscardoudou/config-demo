// src/main/java/com/example/configdemo/MyConfig.java
package com.example.configdemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyConfig {
    @Value("${MY_ENV_VAR}")
    private String myEnvVar;

//    private final String myVar;
//
//    public MyConfig(@Value("${MY_VAR}") String myVar) {
//        this.myVar = myVar;
//    }
//
//    public String getMyVar() {
//        return myVar;
//    }

    public String getMyEnvVar() {
        return myEnvVar;
    }
}

// src/main/java/com/example/configdemo/MyConfig.java
package com.example.configdemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyConfig {
    @Value("${VAR_SPECIFIC_TO_DEPLOYMENT}")
    private String varSpecificToDeployment;

//    private final String myVar;
//
//    public MyConfig(@Value("${MY_VAR}") String myVar) {
//        this.myVar = myVar;
//    }
//
//    public String getMyVar() {
//        return myVar;
//    }

    public String getVarSpecificToDeployment() {
        return varSpecificToDeployment;
    }
}

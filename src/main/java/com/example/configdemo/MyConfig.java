// src/main/java/com/example/configdemo/MyConfig.java
package com.example.configdemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyConfig {
    @Value("${var.specific-to-deployment}")
    private String varSpecificToDeployment;

    @Value("${var.boundary-month-inclusive}")
    private String varWithSameValueAcrossDeployment;

    public String getVarSpecificToDeployment() {
        return varSpecificToDeployment;
    }

    public String getVarWithSameValueAcrossDeployment() {
        return varWithSameValueAcrossDeployment;
    }
}

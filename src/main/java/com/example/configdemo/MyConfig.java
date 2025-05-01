// src/main/java/com/example/configdemo/MyConfig.java
package com.example.configdemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyConfig {
    @Value("${VAR_SPECIFIC_TO_DEPLOYMENT}")
    private String varSpecificToDeployment;

    @Value("${VAR_WITH_SAME_VALUE_ACROSS_DEPLOYMENT}")
    private String varWithSameValueAcrossDeployment;

    public String getVarSpecificToDeployment() {
        return varSpecificToDeployment;
    }

    public String getVarWithSameValueAcrossDeployment() {
        return varWithSameValueAcrossDeployment;
    }
}

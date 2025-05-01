config demo
=======
this minimum project show case how config in effect changed due to spring boot version update.

Initially, the spring boot version is 1.5.3.RELEASE. if you run `mvn test`, test passed.

after we update the spring boot version to 2.7.18, `mvn test` failed now due to   
```
[ERROR] testEnvVarFromBean  Time elapsed: 0.001 s  <<< ERROR!
java.lang.IllegalStateException: Failed to load ApplicationContext
Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'myConfig': Injection of autowired dependencies failed; nested exception is java.lang.IllegalArgumentException: Could not resolve placeholder 'ENV_VAR' in value "${ENV_VAR}"
```

If you move the ./application.yml out of the project root directory. Test passed still, meaning it still recognized the embedded profile for spring boot test.  
It is just ./application.yml in project root directory is taking precedence.

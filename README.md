config demo
=======
this minimum project show case how config in effect changed due to spring boot version update.
## mvn test issue throw error regarding `VAR_SPECIFIC_TO_DEPLOYMENT`
Initially, the spring boot version is 1.5.3.RELEASE. if you run `mvn test`, test passed.

after we update the spring boot version to 2.7.18, `mvn test` failed now due to   
```
[ERROR] testEnvVarFromBean  Time elapsed: 0.001 s  <<< ERROR!
java.lang.IllegalStateException: Failed to load ApplicationContext
Caused by: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'app': Unsatisfied dependency expressed through field 'config'; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'myConfig': Injection of autowired dependencies failed; nested exception is java.lang.IllegalArgumentException: Could not resolve placeholder 'VAR_SPECIFIC_TO_DEPLOYMENT' in value "${VAR_SPECIFIC_TO_DEPLOYMENT}"
Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'myConfig': Injection of autowired dependencies failed; nested exception is java.lang.IllegalArgumentException: Could not resolve placeholder 'VAR_SPECIFIC_TO_DEPLOYMENT' in value "${VAR_SPECIFIC_TO_DEPLOYMENT}"
Caused by: java.lang.IllegalArgumentException: Could not resolve placeholder 'VAR_SPECIFIC_TO_DEPLOYMENT' in value "${VAR_SPECIFIC_TO_DEPLOYMENT}"
```
* so it complains placeholder for VAR_SPECIFIC_TO_DEPLOYMENT not provided in ./application.yml * 

If you move the ./application.yml out of the project root directory. Test passed still, meaning it still recognized the embedded profile for spring boot test.  
It is just ./application.yml in project root directory is taking precedence.

### remedy 
build with `-Dspring.config.additional-location` argument pointing to application-embedded.yml
or move ./application.yml out of project root directory(not clean, break dev current setup/process)   
or rename to sth else, eg. application.yml to application-prod.yml, so it won't pick prod profile when embedded profile used for mvn test.   


## run.sh issue
### with .env not export VAR_WITH_SAME_VALUE_ACROSS_DEPLOYMENT

#### 1.5.3
build with `mvn clean package` no issue  
```
2025-05-01 16:18:06.452  INFO 14360 --- [           main] com.example.configdemo.EnvVarTest        : Started EnvVarTest in 0.314 seconds (JVM running for 0.57)
Resolved varWithSameValueAcrossDeployment via bean: June
Resolved varSpecificToDeployment via bean: from-embedded-profile
```
```
source .env
./run.sh
```
no issue
```
yz@H1DL9Y3:~/configdemo$ env |grep VAR
VAR_SPECIFIC_TO_DEPLOYMENT=FROM_DOT_ENV_LOCAL
yz@H1DL9Y3:~/configdemo$ ./run.sh
 :: Spring Boot ::        (v1.5.3.RELEASE)
Hello World
June
FROM_DOT_ENV_LOCAL
```

if you have ./application.yml, but didn't export VAR_SPECIFIC_TO_DEPLOYMENT, it will throw error, won't use `from-default-profile` from classpath:/application.yml   
```
2025-05-01 15:54:21.815 ERROR 1842 --- [           main] o.s.boot.SpringApplication               : Application startup failed
org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'app': Unsatisfied dependency expressed through field 'config'; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'myConfig': Injection of autowired dependencies failed; nested exception is java.lang.IllegalArgumentException: Could not resolve placeholder 'VAR_SPECIFIC_TO_DEPLOYMENT' in value "${VAR_SPECIFIC_TO_DEPLOYMENT}"
```

if remove ./application.yml out of the way, and ./run.sh, it uses the value from src/main/resources/application.yml.(classpath)    
it will have:
```
yz@H1DL9Y3:~/configdemo$ java -jar target/configdemo-1.0.jar
 :: Spring Boot ::        (v1.5.3.RELEASE)
Hello World
June
from-default-profile
```

#### 2.7.18
make sure `unset VAR_SPECIFIC_FOR_LOCAL`   
build with `mvn clean package` failed due to same reason above for `mvn test` on 2.7.18: `VAR_SPECIFIC_TO_DEPLOYMENT`    
use approach 3 rename to sth else from remedy above to build instead, then do `mvn clean package`
```
source .env 
./run.sh
```
run.sh throw error regarding var.boundary-month-inclusive.
```
yz@H1DL9Y3:~/configdemo$ env |grep VAR
VAR_SPECIFIC_TO_DEPLOYMENT=FROM_DOT_ENV_LOCAL
yz@H1DL9Y3:~/configdemo$ ./run.sh
 :: Spring Boot ::               (v2.7.18)
2025-05-01 16:31:05.984 ERROR 22512 --- [           main] o.s.boot.SpringApplication               : Application run failed
org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'app': Unsatisfied dependency expressed through field 'config'; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'myConfig': Injection of autowired dependencies failed; nested exception is java.lang.IllegalArgumentException: Could not resolve placeholder 'var.boundary-month-inclusive' in value "${var.boundary-month-inclusive}"
```
2.7.18 doesn't seem recognizes the `var.boundary-month-inclusive` in classpath:/application.yml like 1.5.3


## Simple Spring Boot app to check my web app availability.



Build & run
-----------

**Prerequisites:**

* Java 21
* Apache Maven (https://maven.apache.org/)

```bash
./src/main/resources/application.properties
```

The list of websites to check can be edited in the file

```bash
./src/main/resources/data.csv
```

Build with

```bash
mvn package
```

and run with

```bash
java -jar target/check-website-state-1.0.0-SNAPSHOT.jar
```

or

```bash
mvn spring-boot:run
```

POint your browser to URL 

```bash
http://localhost:8080
```

to see the result.

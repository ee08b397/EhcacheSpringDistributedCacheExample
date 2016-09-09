# shared boots

Running multiple instances of Spring boot and be able to share cached information or sessions among all instances. This can be used to cache  single sign-on information. Users can access to different instances with the same token, e.g., Saml. 

### 1. tasks
1. ~~spring boot setup in mvn~~
2. ~~multiple instances running on different ports~~
3. ~~demo cache used~~
4. ~~try ehcache - high perf cache has a shared model. it supports replication across multiple cache instances across different physical servers~~
5. share cache by cache manager
6. share sessions
5. *try redis - distributed data store, embedded
6. *try ignite - distributed database

---

### 2. Use
Get source
```
git clone ssh://git@git.blackrock.com:9102/~sozhang/bootsandshoes.git
```
Compile by 
```
mvn clean package
```

This demo is a resful service so test in browsers. If running on the same host, use different ports by `-Dserver.port`. 
```sh
java -jar -Dserver.port=9000 target/bootsandshoes-1.0-SNAPSHOT.jar
```

Then use script to launch browsers. sample script in [script/launch_browser](script/launch_browser) for chrome or firefox. 


If request not cached (miss), there's a 3s delay in browser and all cache entries are dumped in terminal like 
```
Greeting{id=2, name='a', balance=450}
Greeting{id=3, name='5223', balance=0}
Greeting{id=1, name='World', balance=100}
```

---

### 3. Interesting things

1. Usually `@Cachable` is used in logic, such as repository or Dao implimentation. In this case, [`GreetingDaoImpl`](src/main/java/hello/GreetingDaoImpl.java). With restful, `@Cachable` needs to be in request level (`@RequestMapping` in [`GreetingController`](src/main/java/hello/GreetingController.java)

2. if context cannot load exception thrown, change `4.1.4` to `4.2.4` in [ehcache.xml](src/main/resources/ehcache.xml)
```xml
   <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>4.2.4.RELEASE</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context-support</artifactId>
        <version>4.2.4.RELEASE</version>
    </dependency>
```






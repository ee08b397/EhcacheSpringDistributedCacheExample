# EhcacheSpringDistributedCacheExample

A demo project to show how to use Ehcache (http://www.ehcache.org/) and Spring Caching
Abstractions.

Running multiple instances of Spring boot and be able to share cached information or sessions among all instances. This can be used to cache single sign-on information. Users can access to different instances with the same token, e.g., Saml.


# Install

```bash
mvn clean package
```

For each instance, change the `cacheManagerPeerListenerFactory > properties > port` in  [example-ehcache.xml](src/main/resources/example-ehcache.xml) and make sure each instance uses a different RMI listening port. Otherwise, peer discovery won't work.

```xml
<cacheManagerPeerListenerFactory  class="net.sf.ehcache.distribution.RMICacheManagerPeerListenerFactory" properties="hostName=localhost, port=40001, socketTimeoutMillis=2000" />
```

After port is changed, make another instance by `mvn clean package` also.

# Run 2 Instances on the Same Host

This demo is a resful service so test in browsers. If running on the same host, use different ports by `-Dserver.port`. `8080` as default

```bash
java -jar target/bootsandshoes-1.0-SNAPSHOT.jar
```

Then on a separate terminal

```sh
java -jar -Dserver.port=9000 target/bootsandshoes-1.0-SNAPSHOT.jar
```


# Run 2 Instances on Separate Hosts

The procedure is exactly the same, except one does not actually need to vary the ports.
Just `scp` the jar to be available on all hosts required


# Test out the distributed cache

To easily launch browsers, use sample script in [script/launch_browser](script/launch_browser) for chrome or firefox. It will launch in Chrome by default. Launch in Firefox by `./launch_browser f` or `./launch_browser firefox`.

`Songxiao` is queried by default. Try a different input in GET e.g., `localhost:8080/test?name=123` then `123` is queried. If request not cached (miss), there's a 3s delay in browser and terminal would show `loading NAME ...` where NAME is the input.

Test in terminal

```bash
# this request should take ~3s (artificially set to simulate long running operation)
curl 'localhost:8080/test'

# try again - and the response should be instantaneous
curl 'localhost:8080/test'

# try again on port 9090, response should ALSO be instantaneous
curl 'localhost:9090/test'
```


# Multicast Auto-Discovery

Ignite nodes are discovered through IP multicast messages (configured
through `example-cache.xml`)



# Interesting things

1. Usually `@Cachable` is used in logic, such as repository or Dao implimentation. In this case, [`GreetingDaoImpl`](src/main/java/hello/GreetingDaoImpl.java). With restful, `@Cachable` needs to be in request level (`@RequestMapping` in [`GreetingController`](src/main/java/hello/GreetingController.java)

2. If context cannot load exception thrown, change `4.1.4` to `4.2.4` in [example-ehcache.xml](src/main/resources/example-ehcache.xml)
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

3. Changing the `cacheManagerPeerListenerFactory > properties > port` in  [example-ehcache.xml](src/main/resources/example-ehcache.xml) and make each instance a port is no fun. Thinking about a way to deliver automatically.




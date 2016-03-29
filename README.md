# XWiki Rest Server
A minimal REST server expandable with XWiki components.

## Usage

### Standalone server

Add the following dependency to your project:

```xml
<dependency>
  <groupId>org.xwiki.contrib</groupId>
  <artifactId>xwiki-restserver-standalone</artifactId>
  <version>1.0</version>
</dependency>
```

Then, implement some REST resources by creating XWiki components (must be singleton):

```java
@Path("/hello")
@Component
@Singleton
public class HelloWorldResource implements RestResource
{
    @GET
    @Produces("application/json")
    @Formatted
    public HelloWorld getHelloWorld()
    {
        return new HelloWorld("Hello World!", 1);
    }
}
```

To finish, run a standalone server:

```java
public static void main(String[] args)
{
    try {
        XWikiRestServer server = new XWikiRestServer(PORT_NUMBER, new XWikiJaxRsApplication());
        server.run();
    } catch (ComponentLookupException e) {
        e.printStackTrace();
    }
}
```

You could find a more complete example in `xwiki-restserver-example`, with unit and functional tests.

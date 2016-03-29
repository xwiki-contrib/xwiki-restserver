# XWiki Rest Server
A minimal REST server expandable with [XWiki components](http://extensions.xwiki.org/xwiki/bin/view/Extension/Component+Module).

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
@Path(HelloWorldResource.PATH)  // URL of the resource
@Component                      // Indicate it's an XWiki component
@Singleton                      // Only singletons are supportted
@Named(HelloWorldResource.PATH) // Don't forget to give a name to your component,
                                // otherwise it will overwrite the "default" component.
public class HelloWorldResource implements org.xwiki.contrib.rest.RestResource 
{
    public static final String PATH = "/hello";
    
    @GET
    @Produces("application/json")
    @Formatted
    public HelloWorld getHelloWorld()
    {
        return new HelloWorld("Hello World!", 1);
    }
}
```
(don't forget to fill the `components.txt` file!)

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

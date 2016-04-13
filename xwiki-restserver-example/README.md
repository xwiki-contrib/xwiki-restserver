# XWiki Rest Server Example

Example of a basic standalone REST server, with 2 resources. The tests contain unit and functional tests. The `pom` uses a maven plugin to create a JAR containing all the dependencies and that you can run easily.

## How to use

Build it:
```
mvn clean install
```

Run it:
```
cd target
java -jar xwiki-restserver-example-1.0-SNAPSHOT.jar
```

Then open your browser and go to
* http://localhost:9000/isrunning
* http://localhost:9000/hello
* http://localhost:9000/restricted (you must be logged for this time, try: `curl -u myUser:abcde http://localhost:9000/restricted`).

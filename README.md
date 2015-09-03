A minimum web framework based on Servlet.

See `TestController` and `routes` for example.

    public class TestController extends BaseController {

      public Result index() {
        return ok("hello world");
      }
    }

    // routes
    GET /index  com.codemacro.webdemo.test.TestController.index

Compile and package to war:

    mvn package

Deploy a servlet server (Jetty/Tomcat).


## Deploy on Jetty

1. download jetty (v9.2)
2. put .war to `webapps` path
3. `java -jar start.jar`
4. locate: `http://localhost:8080/you-war-file-name`

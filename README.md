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



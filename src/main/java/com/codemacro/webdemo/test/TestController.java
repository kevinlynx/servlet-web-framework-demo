package com.codemacro.webdemo.test;

import java.util.Arrays;

import com.codemacro.webdemo.BaseController;
import com.codemacro.webdemo.result.Result;

public class TestController extends BaseController {

  public Result index() {
    return ok("hello world");
  }
  
  public Result code404() {
    return status(404, "not found");
  }
  
  public Result object() {
    class Obj {
      @Override
      public String toString() {
        return "object: " + hashCode();
      }
    }
    return ok(new Obj());
  }
  
  public Result template() {
    String[] langs = new String[] {"c++", "java", "python"};
    return ok(jsp("index.jsp")
        .put("name", "kevin")
        .put("langs",  langs)
        .put("langList", Arrays.asList(langs))
        );
  }
  
  public Result hello() {
    String name = getQueryString("name");
    return ok(jsp("hello.jsp").put("name", name));
  }
  
  public Result sayHello() {
    String name = getQueryString("name");
    return ok(jsp("hello.jsp").put("name", name));
  }
}

package com.codemacro.webdemo;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.codemacro.webdemo.result.JSPResult;
import com.codemacro.webdemo.result.Result;

public class BaseController {
  private HttpServletRequest request;
  private HttpServletResponse response;
  
  public void init(HttpServletRequest req, HttpServletResponse resp) {
    this.request = req;
    this.response = resp;
  }
  
  @SuppressWarnings("unchecked")
  protected Map<String, String[]> getQueryStrings() {
    return request.getParameterMap();
  }
  
  protected String getQueryString(String key) {
    return request.getParameter(key);
  }

  protected Result status(int code, String text) {
    response.setStatus(code);
    return new Result(response, text);
  }
  
  protected Result ok(Object obj) {
    return new Result(response, obj);
  }

  protected Result ok(Result result) {
    return result;
  }
  
  protected JSPResult jsp(String file) {
    return new JSPResult(request, response, file);
  }
}

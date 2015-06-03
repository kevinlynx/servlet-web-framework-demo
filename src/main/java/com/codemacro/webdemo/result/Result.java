package com.codemacro.webdemo.result;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

public class Result {
  protected HttpServletResponse response;
  protected Object result;

  public Result(HttpServletResponse resp, Object result) {
    this.response = resp;
    this.result = result;
  }

  public void render() throws IOException, ServletException {
    PrintWriter writer = response.getWriter();
    writer.append(result.toString());
    writer.close();
  }
}
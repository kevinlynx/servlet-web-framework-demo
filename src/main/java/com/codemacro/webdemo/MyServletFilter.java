package com.codemacro.webdemo;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyServletFilter implements Filter {
  private static Logger logger = LoggerFactory.getLogger(MyServletFilter.class);
  private MyWebApp app;
  
  public void destroy() {
    logger.info("MyWeb exits");
  }

  public void doFilter(ServletRequest req, ServletResponse res,
      FilterChain chain) throws IOException, ServletException {
    boolean ret = app.getActionManager().invoke((HttpServletRequest)req, (HttpServletResponse)res);
    if (!ret) {
      chain.doFilter(req, res);
    }
  }

  public void init(FilterConfig conf) throws ServletException {
    app = new MyWebApp();
    app.startup(getAppName(conf.getServletContext()));
    logger.info("MyWeb startup");
  }
  
  private String getAppName(ServletContext context) {
    return context.getServletContextName();
  }
}


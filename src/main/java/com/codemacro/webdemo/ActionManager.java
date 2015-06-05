package com.codemacro.webdemo;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codemacro.webdemo.result.Result;

public class ActionManager {
  private static Logger logger = LoggerFactory.getLogger(ActionManager.class);
  public static enum HttpMethod { GET, POST };
  
  private static class ActionKey {
    public String uri;
    public HttpMethod httpMethod;
    
    public ActionKey(String uri, HttpMethod m) {
      this.uri = uri;
      this.httpMethod = m;
    }

    @Override
    public int hashCode() {
      return httpMethod.hashCode() | uri.hashCode();
    }
    
    @Override
    public boolean equals(Object that) {
      if (that == this) {
        return true;
      }
      if (!(that instanceof ActionKey)) {
        return false;
      }
      ActionKey athat = (ActionKey) that;
      return athat.httpMethod.equals(httpMethod) && athat.uri.equals(uri);
    }
    
    @Override
    public String toString() {
      return "ActionKey[uri=" + uri + ", method=" + httpMethod + "]";
    }
  }
  
  private static class ActionValue {
    public Class<? extends BaseController> clazz;
    public Method method;
    
    public ActionValue(Class<? extends BaseController> clazz, Method m) {
      this.clazz = clazz;
      this.method = m;
    }
    
    @Override
    public String toString() {
      return "ActionValue[m=" + clazz.getName() + "." + method.getName() + "]";
    }
  }

  private Map<ActionKey, ActionValue> actions = new HashMap<ActionKey, ActionValue>();
  private String appName;
  
  public ActionManager(String appName) {
    this.appName = appName;
  }

  public String getAppName() {
    return appName;
  }

  @SuppressWarnings("unchecked")
  public void registerAction(String clazName, String methodName, String uri, String method) {
    try {
      uri = "/" + appName + uri;
      Class<? extends BaseController> clazz = (Class<? extends BaseController>) loadClass(clazName);
      Method m = clazz.getMethod(methodName, (Class<?>[])null);
      if (m.getReturnType() != Result.class) {
        throw new RuntimeException("action method return type mismatch: " + uri);
      }
      ActionKey k = new ActionKey(uri, getMethod(method));
      ActionValue v = new ActionValue(clazz, m);
      logger.debug("register action {} {} {} {}", clazName, methodName, uri, method);
      actions.put(k, v);
    } catch (Exception e) {
      throw new RuntimeException("registerAction failed: " + uri, e);
    }
  }
  
  public boolean invoke(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String uri = req.getRequestURI();
    String method = req.getMethod().toUpperCase();
    try {
      ActionValue v = getAction(uri, method);
      if (v == null) {
        return false;
      }
      BaseController ctl = (BaseController) v.clazz.newInstance();
      ctl.init(req, resp, this);
      logger.debug("invoke action {}", uri);
      Result result = (Result) v.method.invoke(ctl, (Object[]) null);
      result.render();
    } catch (Exception e) {
      PrintWriter writer = resp.getWriter();
      writer.append("action exception: " + uri);
      writer.append("\n");
      writer.append(e.getMessage());
      writer.close();
      logger.warn("action exception: {}\n{}", e, actions);
    }
    return true;
  }
  
  public ActionValue getAction(String uri, String method) {
    ActionKey k = new ActionKey(uri, getMethod(method));
    return actions.get(k);
  }
  
  public String getReverseAction(String clazzMethod, Map<String, Object> args) {
    String argStr = formatQueryParams(args);
    for (Map.Entry<ActionKey, ActionValue> action : actions.entrySet()) {
      ActionValue av = action.getValue();
      if ((av.clazz.getName() + "." + av.method.getName()).equals(clazzMethod)) {
        return action.getKey().uri + (argStr == "" ? "" : "?" + argStr);
      }
    }
    return "";
  }

  static public HttpMethod getMethod(String m) {
    switch (m) {
    case "GET": return HttpMethod.GET;
    case "POST": return HttpMethod.POST;
    }
    return HttpMethod.GET;
  }

  private Class<?> loadClass(String fullName) throws ClassNotFoundException {
    return Class.forName(fullName);
  }

  private String formatQueryParams(Map<String, Object> args) {
    if (args == null) {
      return "";
    }
    String s = "";
    for (Map.Entry<String, Object> entry : args.entrySet()) {
      String kv = encode(entry.getKey()) + "=" + encode(entry.getValue().toString());
      if (s == "") {
        s = kv;
      } else {
        s += "&" + kv;
      }
    }
    return s;
  }

  private String encode(String s) {
    try {
      return URLEncoder.encode(s, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      return "";
    }
  }
}



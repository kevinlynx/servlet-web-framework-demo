package com.codemacro.webdemo.result;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.codemacro.webdemo.ActionManager;

public class JSPRouteTag extends SimpleTagSupport implements DynamicAttributes {
  public static final String ACTION_MGR = "__actionmgr__";
  private String action;
  private Map<String, Object> attrMap = new HashMap<String, Object>();

  @Override
  public void doTag() throws IOException {
    JspContext context = getJspContext();
    ActionManager actionMgr = (ActionManager) context.findAttribute(ACTION_MGR);
    JspWriter out = context.getOut();
    String uri = actionMgr.getReverseAction(action, attrMap);
    out.println(uri);
  }

  @Override
  public void setDynamicAttribute(String uri, String name, Object value) throws JspException {
    attrMap.put(name, value);
  }
  
  public void setAction(String action) {
    this.action = action;
  }
  
}

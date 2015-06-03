package com.codemacro.webdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyWebApp {
  private static Logger logger = LoggerFactory.getLogger(MyWebApp.class);
  private ActionManager actionMgr;
  
  public void startup(String appName) {
    logger.info("MyWebApp <{}> initializing...", appName);
    actionMgr = new ActionManager(appName);
    RouteConf.load(actionMgr);
  }

  public ActionManager getActionManager() {
    return actionMgr;
  }
}

package com.codemacro.webdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteConf {
  private static final String FILE = "/routes";
	private static final Pattern LineR = Pattern.compile("(\\S+)\\s+(\\S+)\\s+(\\S+)");
  private static Logger logger = LoggerFactory.getLogger(RouteConf.class);

  public static void load(ActionManager actionMgr) {
    logger.info("load routes...");
    try (BufferedReader br = new BufferedReader(new InputStreamReader(
        RouteConf.class.getResourceAsStream(FILE)))) {
      String line;
      while ((line = br.readLine()) != null) {
        parseLine(actionMgr, line);
      }
    } catch (IOException e) {
      logger.error("load routes failed: {}", e);
    }
  }
  
  private static void parseLine(ActionManager actionMgr, String line) {
    if (line.startsWith("#")) {
      return ;
    }
    Matcher m = LineR.matcher(line);
    if (!m.find()) {
      return ;
    }
    String method = m.group(1);
    String uri = m.group(2);
    String clazzFunc = m.group(3);
    int pos = clazzFunc.lastIndexOf('.');
    String clazz = clazzFunc.substring(0, pos);
    String func = clazzFunc.substring(pos + 1);
    logger.debug("parse line {} {} {} {}", method, uri, clazz, func);
    actionMgr.registerAction(clazz, func, uri, method);
  }
}

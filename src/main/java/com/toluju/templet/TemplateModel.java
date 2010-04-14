package com.toluju.templet;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author tjungen
 */
public class TemplateModel {
  private Map<String, Node> nodes = new LinkedHashMap<String, Node>();

  public boolean hasNode(String name) {
    return nodes.containsKey(name);
  }

  public Node getNode(String name) {
    return nodes.get(name);
  }

  public void setNode(String name, Node node) {
    nodes.put(name, node);
  }

  public static class Node {
    private Map<String, Node> children = new LinkedHashMap<String, Node>();

    public boolean hasChild(String name) {
      return children.containsKey(name);
    }

    public Node getChild(String name) {
      return children.get(name);
    }

    public void setChild(String name, Node node) {
      children.put(name, node);
    }
  }
}

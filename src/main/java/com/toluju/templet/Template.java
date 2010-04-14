package com.toluju.templet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tjungen
 */
public class Template {
  public static String NAMESPACE = "http://www.toluju.com/ns/templet";

  private Element rootElement;

  public void setRootElement(Element rootElement) {
    this.rootElement = rootElement;
  }

  public Element getRootElement() {
    return rootElement;
  }

  protected static class Node {
    public boolean isElement() {
      return false;
    }

    public boolean isContent() {
      return false;
    }

    public boolean isDirective() {
      return false;
    }

    public Element asElement() {
      return (Element) this;
    }

    public Content asContent() {
      return (Content) this;
    }

    public Directive asDirective() {
      return (Directive) this;
    }
  }

  protected static class Content extends Node {
    private String value;

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    @Override public boolean isContent() {
      return true;
    }

    @Override public String toString() {
      return value;
    }
  }

  protected static class Element extends Node {
    private String nameSpace;
    private String name;
    private Map<String, String> attributes = new LinkedHashMap<String, String>();
    private List<Node> children = new ArrayList<Node>();
    private Element parent;

    public String getNameSpace() {
      return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
      this.nameSpace = nameSpace;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Map<String, String> getAttributes() {
      return attributes;
    }

    public List<Node> getChildren() {
      return children;
    }

    public void setParent(Element parent) {
      this.parent = parent;
    }

    public Element getParent() {
      return parent;
    }

    @Override public String toString() {
      StringBuilder builder = new StringBuilder();

      if (nameSpace == null || nameSpace.length() == 0) {
        builder.append("<" + name);
      }
      else {
        builder.append("<" + nameSpace + ":" + name);
      }

      for (Map.Entry attribute : attributes.entrySet()) {
        builder.append(" " + attribute.getKey() + "=\"" + attribute.getValue() + "\"");
      }

      if (children.isEmpty()) {
        builder.append("/>");
      }
      else {
        builder.append(">");

        for (Node child : children) {
          builder.append(child.toString());
        }

        if (nameSpace == null || nameSpace.length() == 0) {
          builder.append("</" + name + ">");
        }
        else {
          builder.append("</" + nameSpace + ":" + name + ">");
        }
      }

      return builder.toString();
    }
  }

  protected static class Directive extends Element {
    @Override public boolean isDirective() {
      return true;
    }
  }
}
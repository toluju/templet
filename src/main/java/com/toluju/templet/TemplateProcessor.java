package com.toluju.templet;

import java.io.Writer;
import java.util.regex.Pattern;

/**
 * @author tjungen
 */
public class TemplateProcessor {
  public TemplateProcessor() {
    
  }

  public void process(Template template, TemplateModel model, Writer writer) {
    process(template.getRootElement(), model, writer);
  }

  private static Pattern replaceRegex = Pattern.compile("\\$\\{(.*?)\\}");

  private void process(Template.Node node, TemplateModel model, Writer writer) {
    if (node.isContent()) {
    }
    else if (node.isElement()) {
    }
    else if (node.isDirective()) {
    }
  }
}

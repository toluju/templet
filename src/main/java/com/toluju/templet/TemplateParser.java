package com.toluju.templet;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author tjungen
 */
public class TemplateParser {
  public static void main(String[] args) throws Exception {
    Reader reader = new FileReader(args[0]);
    TemplateParser parser = new TemplateParser();
    Template template = parser.parse(reader);
    reader.close();
  }

  protected SAXParser xmlParser;

  public TemplateParser() {
    try {
      SAXParserFactory parserFactory = SAXParserFactory.newInstance();
      parserFactory.setNamespaceAware(true);
      xmlParser = parserFactory.newSAXParser();
    }
    catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    }
    catch (SAXException e) {
      throw new RuntimeException(e);
    }
  }

  public Template parse(Reader reader) throws IOException {
    InputSource source = new InputSource(reader);
    SAXHandler handler = new SAXHandler();

    try {
      xmlParser.parse(source, handler);
    }
    catch (SAXException e) {
      throw new IOException(e);
    }

    Template template = new Template();
    Template.Element root = handler.getRoot();
    template.setRootElement(root);
    System.out.println(root);
    return template;
  }

  private static class SAXHandler extends DefaultHandler {
    private Template.Element current;

    public Template.Element getRoot() {
      return current;
    }

    @Override public void startElement(String uri, String localName, String qName, Attributes attributes) {
      Template.Element element = null;

      if (uri.equals(Template.NAMESPACE)) {
        element = new Template.Directive();
      }
      else {
        element = new Template.Element();
      }

      element.setNameSpace(uri);
      element.setName(localName);

      for (int x = 0; x < attributes.getLength(); ++x) {
        String attributeName = attributes.getLocalName(x);
        String attributeValue = attributes.getValue(x);
        element.getAttributes().put(attributeName, attributeValue);
      }

      if (current != null) {
        current.getChildren().add(element);
        element.setParent(current);
      }
      
      current = element;
    }

    @Override public void endElement(String uri, String localName, String qName) {
      if (current.getParent() != null) {
        current = current.getParent();
      }
    }

    @Override public void characters(char[] c, int start, int length) {
      Template.Content content = new Template.Content();
      content.setValue(new String(c, start, length));
      current.getChildren().add(content);
    }
  }
}

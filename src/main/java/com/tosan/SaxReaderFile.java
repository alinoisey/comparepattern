package com.tosan;

import com.atlassian.bamboo.build.logger.BuildLogger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import java.io.File;

public class SaxReaderFile {
    private BuildLogger buildLogger=null;

    public SaxReaderFile(BuildLogger buildLogger) {
        this.buildLogger = buildLogger;
    }


    public String getLogstashPattern(String path) {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            File input = new File(path);
            SAXParser saxParser = saxParserFactory.newSAXParser();
            SaxReaderFileHandler standardHandler = new SaxReaderFileHandler();
            saxParser.parse(input, standardHandler);
            return standardHandler.Pattern;
        } catch (Exception e) {
            buildLogger.addErrorLogEntry("logback file in SaxReader has problem " + e+"===================");
        }
        return "null";
    }

//-----------------------------------------------------------------------------------------


    class SaxReaderFileHandler extends DefaultHandler {
        boolean isPattern = false;
        String Logstash = null;
        String Pattern = null;

        @Override
        public void startElement(String uri,
                                 String localName, String qName, Attributes attributes)
                throws SAXException {
            if (qName.equalsIgnoreCase("appender")) {
                String s = attributes.getValue("class");
                if (s.equalsIgnoreCase("net.logstash.logback.appender.LogstashTcpSocketAppender")) {
                    Logstash = s;
                }
            } else if (qName.equalsIgnoreCase("pattern")) {
                if (Logstash != null) {
                    isPattern = true;
                }
            }
        }

        @Override
        public void endElement(String uri,
                               String localName, String qName) throws SAXException {
            if (qName.equalsIgnoreCase("appender")) {
                Logstash = null;
            }
        }

        @Override
        public void characters(char ch[],
                               int start, int length) throws SAXException {
            if (isPattern) {
                if (Logstash != null) {
                    Pattern = new String(ch, start, length);
                    isPattern = false;
                }
            }
        }
    }

    public BuildLogger getBuildLogger() {
        return buildLogger;
    }

    public void setBuildLogger(BuildLogger buildLogger) {
        this.buildLogger = buildLogger;
    }
}



package com.tosan;

import com.atlassian.bamboo.build.logger.BuildLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.util.*;

public class ComparePattern {
    private static Logger logger = Logger.getLogger(ComparePattern.class);
    private BuildLogger buildLogger;

    public ComparePattern(BuildLogger buildLogger) {
        this.buildLogger = buildLogger;
    }

    public BuildLogger getBuildLogger() {
        return buildLogger;
    }

    public void setBuildLogger(BuildLogger buildLogger) {
        this.buildLogger = buildLogger;
    }

    public List<String> comparePattern(String path) {
        List<String> badlist = new ArrayList<>();
        buildLogger.addBuildLogEntry("comparepattern start ======================");
        SaxReaderFile saxReaderFile = new SaxReaderFile();
        Preparer preparer = new Preparer(buildLogger);
        List<String> list = preparer.prepare(path);
        buildLogger.addBuildLogEntry("list is readey to compare ============");
        for (String logback : list) {
            String pattern = saxReaderFile.getPattern(logback);
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap hashMap;
            try {
                if (!pattern.isEmpty()) {
                    String SPattern = pattern.toLowerCase();
                    hashMap = objectMapper.readValue(SPattern, HashMap.class);
                    if (hashMap.containsKey("app_name") & hashMap.containsKey("app_version") & hashMap.containsKey("raw_log")) {
                        buildLogger.addBuildLogEntry("pattern " + logback + " is ok");
                        logger.info("pattern " + logback + " is ok and contains app_name,app_version,raw_log ");
                        logger.debug("pattern " + logback + " is ok and contains app_name,app_version,raw_log " + pattern);
                    } else {
                        badlist.add("address is : "+logback+"\n");
                        buildLogger.addBuildLogEntry("pattern " + logback + " is Not ok ");
                    }
                } else {
                    badlist.add("address is : "+logback+"\n");
                    buildLogger.addBuildLogEntry("pattern " + logback + " is Not ok ");
                }
            } catch (JsonProcessingException e) {
                buildLogger.addErrorLogEntry("pattern " + logback + " is Not ok because pattern is No content " + e);
            }
        }
        return badlist;
    }
}

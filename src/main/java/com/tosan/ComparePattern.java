package com.tosan;

import com.atlassian.bamboo.build.logger.BuildLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.util.*;

public class ComparePattern {
    private BuildLogger buildLogger;
    final String key1="app_name";
    final String key2="app_version";
    final String key3="raw_log";
    public ComparePattern(BuildLogger buildLogger) {
        this.buildLogger = buildLogger;
    }

    public List<String> comparePattern(List<String> listAddressFiles) {
        buildLogger.addBuildLogEntry("comparepattern start ======================");
        List<String> listProblemFiles = new ArrayList<>();
        SaxReaderFile saxReaderFile = new SaxReaderFile(buildLogger);
        ObjectMapper objectPattern = new ObjectMapper();
        for (String addressfile : listAddressFiles) {
            String pattern = saxReaderFile.getPattern(addressfile);
            try {
                if (!pattern.equals("null")) {
                    String Pattern = pattern.toLowerCase();
                    HashMap logstashPattern = objectPattern.readValue(Pattern, HashMap.class);
                    if (logstashPattern.containsKey(key1) && logstashPattern.containsKey(key2) && logstashPattern.containsKey(key3)) {
                    } else {
                        listProblemFiles.add("address is : "+addressfile+"\n");
                    }
                } else {
                    listProblemFiles.add("address is : "+addressfile+"\n");
                }
            } catch (JsonProcessingException e) {
                buildLogger.addErrorLogEntry("pattern " + addressfile + " is Not ok because pattern is No content " + e);
            }
        }
        return listProblemFiles;
    }
    public BuildLogger getBuildLogger() {
        return buildLogger;
    }

    public void setBuildLogger(BuildLogger buildLogger) {
        this.buildLogger = buildLogger;
    }

}

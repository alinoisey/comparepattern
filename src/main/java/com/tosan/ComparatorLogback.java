package com.tosan;

import com.atlassian.bamboo.build.logger.BuildLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.*;

public class ComparatorLogback {
    private BuildLogger buildLogger;
    private static final String APP_NAME="app_name";
    private static final String APP_VERSION="app_version";
    private static final String RAW_LOG="raw_log";
    private List<String> addressList = new ArrayList();
    private final String fileName="logback.xml";
    private List<String> blackList=Arrays.asList(new String[]{"target", "values"});

    public ComparatorLogback(BuildLogger buildLogger) {
        this.buildLogger = buildLogger;
    }

    private List<String> getListLogback(String path) {

        File searchPath = new File(path);
        File[] listFiles = searchPath.listFiles();
        for (File file : listFiles) {
            if (file.isDirectory()) {
                if(!blackList.contains(file.getName()))
                    getListLogback(file.getAbsolutePath());

            } else {
                if (file.getName().matches(fileName)) {
                    addressList.add(file.getAbsolutePath());
                }
            }
        }
        return addressList;
    }
    public String comparePattern(String path) {
        buildLogger.addBuildLogEntry("comparepattern start ======================");
        SaxReader logstashReader = new SaxReader(buildLogger);
        ObjectMapper objectPattern = new ObjectMapper();
        StringBuilder listProblemLogbackFiles=new StringBuilder();

        for (String logbackAddress : getListLogback(path)) {
            String logstashPatternFile = logstashReader.getLogstashPattern(logbackAddress);
            try {
                if (!logstashPatternFile.equals("null")) {
                    String Pattern = logstashPatternFile.toLowerCase();
                    HashMap logstashPattern = objectPattern.readValue(Pattern, HashMap.class);
                    if (logstashPattern.containsKey(APP_NAME) && logstashPattern.containsKey(APP_VERSION) && logstashPattern.containsKey(RAW_LOG)) {
                    } else {
                        listProblemLogbackFiles.append("address is : "+logbackAddress+"<br>");
                    }
                } else {
                    listProblemLogbackFiles.append("address is : "+logbackAddress+"<br>");
                }
            } catch (JsonProcessingException e) {
                buildLogger.addErrorLogEntry("pattern " + logbackAddress + " is Not ok because pattern is No content " + e);
            }
        }
        return listProblemLogbackFiles.toString();
    }
    public BuildLogger getBuildLogger() {
        return buildLogger;
    }

    public void setBuildLogger(BuildLogger buildLogger) {
        this.buildLogger = buildLogger;
    }

}

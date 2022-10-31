package com.tosan;

import com.atlassian.bamboo.build.logger.BuildLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

public class Comparator {
    private BuildLogger buildLogger;
    private static final String APP_NAME="app_name";
    private static final String APP_VERSION="app_version";
    private static final String RAW_LOG="raw_log";
    public Comparator(BuildLogger buildLogger) {
        this.buildLogger = buildLogger;
    }

    public List<String> comparePatternLogback(List<String> listLogbackAddresses) {
        buildLogger.addBuildLogEntry("comparepattern start ======================");
        List<String> listProblemLogbackFiles = new ArrayList<>();
        SaxReader logstashReaderFile = new SaxReader(buildLogger);
        ObjectMapper objectPattern = new ObjectMapper();
        for (String addressLogbackFile : listLogbackAddresses) {
            String logstashPatternFile = logstashReaderFile.getLogstashPattern(addressLogbackFile);
            try {
                if (!logstashPatternFile.equals("null")) {
                    String Pattern = logstashPatternFile.toLowerCase();
                    HashMap logstashPattern = objectPattern.readValue(Pattern, HashMap.class);
                    if (logstashPattern.containsKey(APP_NAME) && logstashPattern.containsKey(APP_VERSION) && logstashPattern.containsKey(RAW_LOG)) {
                    } else {
                        listProblemLogbackFiles.add("address is : "+addressLogbackFile+"\n");
                    }
                } else {
                    listProblemLogbackFiles.add("address is : "+addressLogbackFile+"\n");
                }
            } catch (JsonProcessingException e) {
                buildLogger.addErrorLogEntry("pattern " + addressLogbackFile + " is Not ok because pattern is No content " + e);
            }
        }
        return listProblemLogbackFiles;
    }
    public BuildLogger getBuildLogger() {
        return buildLogger;
    }

    public void setBuildLogger(BuildLogger buildLogger) {
        this.buildLogger = buildLogger;
    }

}

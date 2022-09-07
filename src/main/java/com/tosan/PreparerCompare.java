package com.tosan;

import com.atlassian.bamboo.build.logger.BuildLogger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PreparerCompare {
    private BuildLogger buildLogger;

    public PreparerCompare(BuildLogger buildLogger) {
        this.buildLogger = buildLogger;
    }

    public List<String> prepare(String path){
        Properties properties = new Properties();
        buildLogger.addBuildLogEntry("config in Prepare is loded ===============");
        try {
            InputStream inputStream=getClass().getClassLoader().getResourceAsStream("location.properties");
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            buildLogger.addErrorLogEntry("file config notFound "+e);
        } catch (IOException e) {
            buildLogger.addErrorLogEntry("Exception: I/O Exception for reade file location.properties"+e);
        }
        List<String> blacklist = new ArrayList(Arrays.asList(properties.getProperty("BlackList").split(",")));
        String filename=properties.getProperty("FileName");
        buildLogger.addBuildLogEntry("start search logback");
        SearchFile searchFile=new SearchFile();
        List<String> list=searchFile.getlogback(path,filename,blacklist);
        return list;
    }

    public BuildLogger getBuildLogger() {
        return buildLogger;
    }

    public void setBuildLogger(BuildLogger buildLogger) {
        this.buildLogger = buildLogger;
    }
}

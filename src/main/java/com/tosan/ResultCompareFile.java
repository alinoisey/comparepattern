package com.tosan;

import com.atlassian.bamboo.build.logger.BuildLogger;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class ResultCompareFile {
    private BuildLogger buildLogger;

    public ResultCompareFile(BuildLogger buildLogger) {
        this.buildLogger = buildLogger;
    }
    public OutputStreamWriter createFile(List<String> ListProblemFiles, String path){

        StringBuilder stringBuilder=new StringBuilder();
        File resultCompareFile = new File(path + "ResultCompare.txt");

        for (String ErrorList:ListProblemFiles) {
            stringBuilder.append("--- "+ErrorList+"\n");
        }
       FileWriter resultCompare=null;
        try {
            resultCompare=new FileWriter(resultCompareFile);
            resultCompare.write(stringBuilder.toString());
            resultCompare.close();
        } catch (IOException e) {
            buildLogger.addBuildLogEntry("I/O Exception in ResultCompreFile =============================");
            e.printStackTrace();
        }
        buildLogger.addBuildLogEntry("resultCompare writed into a file in the WorkDirectory ==================");
        return resultCompare;
    }
    public BuildLogger getBuildLogger() {
        return buildLogger;
    }
    public void setBuildLogger(BuildLogger buildLogger) {
        this.buildLogger = buildLogger;
    }
}
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

    public OutputStreamWriter createResultCompareFile(List<String> listProblemLogbackFiles, String path) {

        File resultCompareFile = new File(path + "ResultCompare.txt");
        StringBuilder bodyResualtCompareFile = new StringBuilder();

        for (String problemLogbackFile : listProblemLogbackFiles) {
            bodyResualtCompareFile.append("--- " + problemLogbackFile + "\n");
        }
        FileWriter resultCompare = null;
        try {
            resultCompare = new FileWriter(resultCompareFile);
            resultCompare.write(bodyResualtCompareFile.toString());
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

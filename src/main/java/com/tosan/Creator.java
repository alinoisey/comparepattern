package com.tosan;

import com.atlassian.bamboo.build.logger.BuildLogger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class Creator {
    private BuildLogger buildLogger;

    public Creator(BuildLogger buildLogger) {
        this.buildLogger = buildLogger;
    }

    public OutputStreamWriter createResultCompareFile(List<String> listProblemLogbackFiles, String path) {

        File resultFile = new File(path + "ResultCompare.txt");
        StringBuilder bodyResultFile = new StringBuilder();

        for (String problemLogbackFile : listProblemLogbackFiles) {
            bodyResultFile.append("--- " + problemLogbackFile + "\n");
        }
        FileWriter resultCompare = null;
        try {
            resultCompare = new FileWriter(resultFile);
            resultCompare.write(bodyResultFile.toString());
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

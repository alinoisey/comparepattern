package com.tosan;

import com.atlassian.bamboo.build.logger.BuildLogger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Generator {
    private BuildLogger buildLogger;

    public Generator(BuildLogger buildLogger) {
        this.buildLogger = buildLogger;
    }

    public void createResultCompareFile(List<String> listProblemLogbackFiles, String path) {

        File resultFile = new File(path + "ResultCompare.txt");
        StringBuilder bodyResultFile = new StringBuilder();

        for (String problemLogbackFile : listProblemLogbackFiles) {
            bodyResultFile.append("--- " + problemLogbackFile + "\n");
        }
        try (FileWriter resultCompare=new FileWriter(resultFile) ){
            resultCompare.write(bodyResultFile.toString());
            resultCompare.flush();
        } catch (IOException e) {
            buildLogger.addBuildLogEntry("I/O Exception in ResultCompreFile =============================");
            e.printStackTrace();
        }
        buildLogger.addBuildLogEntry("resultCompare writed into a file in the WorkDirectory ==================");
    }

    public BuildLogger getBuildLogger() {
        return buildLogger;
    }

    public void setBuildLogger(BuildLogger buildLogger) {
        this.buildLogger = buildLogger;
    }
}

package com.tosan;

import com.atlassian.bamboo.build.logger.BuildLogger;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class PrepareFile {
    private BuildLogger buildLogger;

    public PrepareFile(BuildLogger buildLogger) {
        this.buildLogger = buildLogger;
    }
    public OutputStreamWriter getBodyMail(List<String> badlist, File file){

        StringBuilder stringBuilder=new StringBuilder();

        for (String list:badlist) {
            stringBuilder.append("--- "+list+"\n");
        }
        buildLogger.addBuildLogEntry("this is list of files with problem ----> "+stringBuilder.toString());
       FileWriter outputStreamWriter=null;
        try {
            outputStreamWriter=new FileWriter(file);
            outputStreamWriter.write(stringBuilder.toString());
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        buildLogger.addBuildLogEntry("badlist writed in file ==================");
        return outputStreamWriter;
    }
    public BuildLogger getBuildLogger() {
        return buildLogger;
    }
    public void setBuildLogger(BuildLogger buildLogger) {
        this.buildLogger = buildLogger;
    }
}

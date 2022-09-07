package com.tosan;

import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.task.*;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.List;


public class TosanStart implements TaskType {
    @NotNull
     @Override
    public TaskResult execute(@NotNull TaskContext taskContext) throws TaskException {
       final BuildLogger buildLogger =taskContext.getBuildLogger();
        ComparePattern comparePattern=new ComparePattern(buildLogger);
        List<String> badlist=comparePattern.comparePattern(taskContext.getRootDirectory().getAbsolutePath());
        File file=new File(taskContext.getWorkingDirectory().getAbsolutePath()+"outcompare.txt");
        PrepareFile adabter=new PrepareFile(buildLogger);
        adabter.getBodyMail(badlist,file);
        buildLogger.addBuildLogEntry("task plugin finished ====================");
        return TaskResultBuilder.newBuilder(taskContext).success().build();
    }
}

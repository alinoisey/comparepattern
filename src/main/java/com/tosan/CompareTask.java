package com.tosan;

import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.task.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class CompareTask implements TaskType {
    @NotNull
    @Override
    public TaskResult execute(@NotNull TaskContext taskContext) throws TaskException {
        final BuildLogger buildLogger = taskContext.getBuildLogger();
        String path = taskContext.getWorkingDirectory().getAbsolutePath();
        buildLogger.addBuildLogEntry("task plugin started ===============");
        SearchFile searchFile = new SearchFile();
        List<String> listFiles = searchFile.getListFiles(path, "logback.xml");
        buildLogger.addBuildLogEntry("search files finished ================");
        ComparePattern comparePattern = new ComparePattern(buildLogger);
        List<String> listProblemFiles = comparePattern.comparePattern(listFiles);
        buildLogger.addBuildLogEntry("compare finished ===================");
        ResultCompareFile creatorOutFile = new ResultCompareFile(buildLogger);
        creatorOutFile.createFile(listProblemFiles, path);
        buildLogger.addBuildLogEntry("task plugin finished ====================");
        return TaskResultBuilder.newBuilder(taskContext).success().build();
    }
}

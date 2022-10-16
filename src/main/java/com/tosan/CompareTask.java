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
        String pathProject = taskContext.getWorkingDirectory().getAbsolutePath();
        buildLogger.addBuildLogEntry("task plugin started ===============");
        SearchFile searchFile = new SearchFile();
        List<String> listLogbackAddresses = searchFile.searchLogbackFiles(pathProject, "logback.xml");
        buildLogger.addBuildLogEntry("search Logback files finished ================");
        ComparePattern comparePattern = new ComparePattern(buildLogger);
        List<String> listProblemFiles = comparePattern.comparePatternLogback(listLogbackAddresses);
        buildLogger.addBuildLogEntry("compare pattern for logback files finished ===================");
        ResultCompareFile resultCompare = new ResultCompareFile(buildLogger);
        resultCompare.createResultCompareFile(listProblemFiles, pathProject);
        buildLogger.addBuildLogEntry("task plugin finished ====================");
        return TaskResultBuilder.newBuilder(taskContext).success().build();
    }
}

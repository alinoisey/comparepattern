package com.tosan;

import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.task.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class ComparatorTask implements TaskType {
    @NotNull
    @Override
    public TaskResult execute(@NotNull TaskContext taskContext) throws TaskException {
        final BuildLogger buildLogger = taskContext.getBuildLogger();
        String pathProject = taskContext.getWorkingDirectory().getAbsolutePath();
        buildLogger.addBuildLogEntry("task plugin started ===============");
        Searcher searchFile = new Searcher();
        List<String> listLogbackAddresses = searchFile.searchLogbackFiles(pathProject, "logback.xml");
        buildLogger.addBuildLogEntry("search Logback files finished ================");
        Comparator comparePattern = new Comparator(buildLogger);
        List<String> listProblemFiles = comparePattern.comparePatternLogback(listLogbackAddresses);
        buildLogger.addBuildLogEntry("compare pattern for logback files finished ===================");
        Creator resultCompare = new Creator(buildLogger);
        resultCompare.createResultCompareFile(listProblemFiles, pathProject);
        buildLogger.addBuildLogEntry("task plugin finished ====================");
        return TaskResultBuilder.newBuilder(taskContext).success().build();
    }
}

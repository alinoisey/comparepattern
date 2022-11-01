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
        Searcher searcher = new Searcher();
        List<String> listLogbackAddresses = searcher.getListLogback(pathProject);
        buildLogger.addBuildLogEntry("search Logback files finished ================");
        Comparator comparator = new Comparator(buildLogger);
        List<String> listProblemLogback = comparator.comparePatternLogback(listLogbackAddresses);
        buildLogger.addBuildLogEntry("compare pattern for logback files finished ===================");
        CreatorFile resultCompare = new CreatorFile(buildLogger);
        resultCompare.createResultCompareFile(listProblemLogback, pathProject);
        buildLogger.addBuildLogEntry("task plugin finished ====================");
        return TaskResultBuilder.newBuilder(taskContext).success().build();
    }
}

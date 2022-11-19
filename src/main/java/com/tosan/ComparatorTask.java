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
        buildLogger.addBuildLogEntry("compare pattern for logback files started ================");
        ComparatorLogback comparator = new ComparatorLogback(buildLogger);
        List<String> listLogbackAddresses =comparator.getListLogback(pathProject);
        List<String> listProblemLogback = comparator.comparePattern(listLogbackAddresses);
        buildLogger.addBuildLogEntry("compare pattern for logback files finished ===================");
        Generator resultCompare = new Generator(buildLogger);
        resultCompare.createResultCompareFile(listProblemLogback, pathProject);
        buildLogger.addBuildLogEntry("task plugin finished ====================");
        return TaskResultBuilder.newBuilder(taskContext).success().build();
    }
}

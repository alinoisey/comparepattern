package com.tosan;

import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.task.*;
import org.jetbrains.annotations.NotNull;


public class ComparatorTask implements TaskType {
    @NotNull
    @Override
    public TaskResult execute(@NotNull TaskContext taskContext) throws TaskException {
        final BuildLogger buildLogger = taskContext.getBuildLogger();
        String pathProject = taskContext.getWorkingDirectory().getAbsolutePath();
        buildLogger.addBuildLogEntry("compare pattern for logback files started ================");
        ComparatorLogback comparator = new ComparatorLogback(buildLogger);
        String listProblemLogback = comparator.comparePattern(pathProject);
        buildLogger.addBuildLogEntry("compare pattern for logback files finished ===================");
        MailSender mail=new MailSender(taskContext,listProblemLogback);
        mail.sendMail();
        buildLogger.addBuildLogEntry("send mail finished ===================");

        return TaskResultBuilder.newBuilder(taskContext).success().build();
    }
}

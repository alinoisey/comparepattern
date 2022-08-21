package com.tosan;

import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.task.*;
import com.atlassian.mail.Email;
import com.atlassian.mail.MailException;
import com.atlassian.mail.MailFactory;
import com.atlassian.mail.server.SMTPMailServer;
import org.jetbrains.annotations.NotNull;
import org.springframework.mail.javamail.MimeMailMessage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class TosanStart implements TaskType {
    @NotNull
     @Override
    public TaskResult execute(@NotNull TaskContext taskContext) throws TaskException {
       final BuildLogger buildLogger =taskContext.getBuildLogger();
        ComparePattern comparePattern=new ComparePattern(buildLogger);
        List<String> badlist=comparePattern.comparePattern(taskContext.getRootDirectory().getAbsolutePath());

        try {
            FileWriter fileWriter=new FileWriter("badlist.txt");
            for (String list:badlist) {
                fileWriter.write(list);

            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        buildLogger.addBuildLogEntry("compre start");
        comparePattern.getBuildLogger();
        buildLogger.addBuildLogEntry("Hello, World!!!");

//        SMTPMailServer server = MailFactory.getServerManager().getDefaultSMTPMailServer();
//        Email email = new Email("alinoisey@gmail.com");
////        Email email = new Email(taskContext.getConfigurationMap().get("to"));
////        buildLogger.addBuildLogEntry(taskContext.getConfigurationMap().get("to")+"===========");
//        email.setSubject("report plugin bamboo");
//        email.setBody("this is test plugin bamboo");
//        email.setMimeType("text/html");
//        email.setFrom("mypligin@noisey.com");
//
//
//
//        try {
//            server.send(email);
//        } catch (MailException e) {
//            e.printStackTrace();
//        }

        return TaskResultBuilder.newBuilder(taskContext).success().build();
    }
}

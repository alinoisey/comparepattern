package com.tosan;

import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.task.*;
import com.atlassian.mail.Email;
import com.atlassian.mail.MailException;
import com.atlassian.mail.MailFactory;
import com.atlassian.mail.server.SMTPMailServer;

public class MailSender {
    private TaskContext taskContext=null;
    private String listProblemLogback=null;
    public MailSender(TaskContext taskContext,String listProblemLogback) {
       this.taskContext=taskContext;
       this.listProblemLogback=listProblemLogback;
    }

    public void sendMail() {
        final BuildLogger buildLogger = taskContext.getBuildLogger();
        final SMTPMailServer mailServer = MailFactory.getServerManager().getDefaultSMTPMailServer();

        try {
            Email email = new Email(taskContext.getConfigurationMap().get("to"), taskContext.getConfigurationMap().get("cc"), null);
            email.setFrom(mailServer.getDefaultFrom());
            email.setFromName(mailServer.getUsername());
            email.setSubject("Report ComparatorLogback");
            email.setEncoding("UTF-8");
            email.setMimeType("text/html");
            email.setBody("These addresses are related to logback files that have problems \n"+listProblemLogback);
            mailServer.send(email);

        } catch (MailException e) {
            buildLogger.addErrorLogEntry("Error: " + e.getMessage());
        }
    }
}
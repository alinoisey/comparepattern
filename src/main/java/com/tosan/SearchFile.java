package com.tosan;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SearchFile {
    private static Logger logger = Logger.getLogger(SearchFile.class);
    List<String> list = new ArrayList();
    public List<String> getlogback(String path, String filename, List blacklist) {
//        URL u=getClass().getClassLoader().getResource("log4j.xml");
//        DOMConfigurator.configure(u);
        File file = new File(path);
        File[] listfiles = file.listFiles();
        for (File file1 : listfiles) {
            if (file1.isDirectory()) {
                Boolean blacklistof = false;
                for (int i = 0; i < blacklist.size(); i++) {
                    if (file1.getName().equalsIgnoreCase((String) blacklist.get(i))){
//                        logger.info("The "+file1.getName()+" directory is one of the blacklist directories");
                        blacklistof = true;
                        break;
                    }
                }
                if (!blacklistof) {
                    getlogback(file1.getAbsolutePath(), filename, blacklist);
                }
            } else {
                if (file1.getName().matches(filename)) {
                    list.add(file.getAbsolutePath() + "\\" + filename);
                }
            }
        }
        return list;
    }
}
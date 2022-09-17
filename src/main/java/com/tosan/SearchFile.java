package com.tosan;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SearchFile {
    List<String> list = new ArrayList();
    CompareConfig blackListConfig= new CompareConfig();
     public List<String> getListFiles(String path, String filename) {
        List <String> blacklist=blackListConfig.getBlacklist();
        File file = new File(path);
        File[] listfiles = file.listFiles();
        for (File file1 : listfiles) {
            if (file1.isDirectory()) {
                Boolean blacklistof = false;
                for (int i = 0; i < blacklist.size(); i++) {
                    if (file1.getName().equalsIgnoreCase((String) blacklist.get(i))){
                        blacklistof = true;
                        break;
                    }
                }
                if (!blacklistof) {
                    getListFiles(file1.getAbsolutePath(), filename);
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
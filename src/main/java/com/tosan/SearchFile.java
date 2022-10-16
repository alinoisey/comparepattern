package com.tosan;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SearchFile {
    private List<String> addressList = new ArrayList();
    private CompareConfig blackListConfig = new CompareConfig();

    public List<String> searchLogbackFiles(String path, String fileName) {
        List<String> blackList = blackListConfig.getBlackList();
        File searchPath = new File(path);
        File[] listFiles = searchPath.listFiles();
        for (File file : listFiles) {
            if (file.isDirectory()) {
                boolean blackListOf = false;
                for (int i = 0; i < blackList.size(); i++) {
                    if (file.getName().equalsIgnoreCase(blackList.get(i))) {
                        blackListOf = true;
                        break;
                    }
                }
                if (!blackListOf) {
                    searchLogbackFiles(file.getAbsolutePath(), fileName);
                }
            } else {
                if (file.getName().matches(fileName)) {
                    addressList.add(file.getAbsolutePath() + "\\" + fileName);
                }
            }
        }
        return addressList;
    }
}
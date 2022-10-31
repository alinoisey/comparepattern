package com.tosan;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Searcher {
    private List<String> addressList = new ArrayList();

    public List<String> searchLogbackFiles(String path, String fileName) {
        List<String> blackListConfig =Config.getBlackListDirecrctory();
        File searchPath = new File(path);
        File[] listFiles = searchPath.listFiles();
        for (File file : listFiles) {
            if (file.isDirectory()) {
                boolean blackListOf = false;
                for (int i = 0; i < blackListConfig.size(); i++) {
                    if (file.getName().equalsIgnoreCase(blackListConfig.get(i))) {
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
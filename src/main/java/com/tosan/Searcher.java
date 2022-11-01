package com.tosan;

import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Searcher {
    private List<String> addressList = new ArrayList();
    private final String fileName="logback.xml";

    private List<String> getBlackList(){
        final List<String> BLACKLIST=new ArrayList<>();
        BLACKLIST.add("target");
        BLACKLIST.add("values");

        return BLACKLIST;
    }

    public List<String> getListLogback(String path) {
//        List<String> blackListConfig =Config.getBlackListDirecrctory();
        File searchPath = new File(path);
        File[] listFiles = searchPath.listFiles();
        for (File file : listFiles) {
            if (file.isDirectory()) {
                boolean blackListOf = false;
                for (int i = 0; i < getBlackList().size(); i++) {
                    if (file.getName().equalsIgnoreCase(getBlackList().get(i))) {
                        blackListOf = true;
                        break;
                    }
                }
                if (!blackListOf) {
                    getListLogback(file.getAbsolutePath());
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
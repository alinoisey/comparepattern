package com.tosan;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class CompareConfig {

    public List<String> getBlackList(){
        Properties properties=new Properties();
        InputStream input=getClass().getClassLoader().getResourceAsStream("aplication.properties");
        List<String> blackList=null;
//        String fileName=null;
        try {
            properties.load(input);
            blackList= Arrays.asList(properties.getProperty("blacklist").split(","));
//            fileName= properties.getProperty("filename");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return blackList;
    }
}

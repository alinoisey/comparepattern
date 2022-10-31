package com.tosan;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Config {

    private static List<String> blackListDirecrctory;

    public static List<String> getBlackListDirecrctory() {
        blackListDirecrctory.add("target");
        blackListDirecrctory.add("values");
        return blackListDirecrctory;
    }
}

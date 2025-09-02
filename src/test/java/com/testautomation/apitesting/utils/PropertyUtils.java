package com.testautomation.apitesting.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {

    private static Properties properties = new Properties();

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream(FrameworkConstants.getConfigFilePath());
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}

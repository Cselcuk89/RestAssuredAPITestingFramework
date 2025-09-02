package com.testautomation.apitesting.utils;

public final class FrameworkConstants {

    private FrameworkConstants() {
        // private constructor to prevent instantiation
    }

    private static final String RESOURCES_PATH = System.getProperty("user.dir") + "/src/test/resources/";
    private static final String CONFIG_FILE_PATH = RESOURCES_PATH + "config.properties";

    public static String getConfigFilePath() {
        return CONFIG_FILE_PATH;
    }

    private static final String TEST_DATA_CSV_PATH = RESOURCES_PATH + "testdatacsv.csv";

    public static String getTestDataCsvPath() {
        return TEST_DATA_CSV_PATH;
    }

    private static final String EXCEL_TEST_DATA_PATH = RESOURCES_PATH + "exceltestdata.xlsx";

    public static String getExcelTestDataPath() {
        return EXCEL_TEST_DATA_PATH;
    }

    private static final String JSON_TEST_DATA_PATH = RESOURCES_PATH + "testdatajson.json";

    public static String getJsonTestDataPath() {
        return JSON_TEST_DATA_PATH;
    }
}

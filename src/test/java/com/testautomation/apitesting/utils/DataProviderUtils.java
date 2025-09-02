package com.testautomation.apitesting.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.testng.annotations.DataProvider;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.jayway.jsonpath.JsonPath;
import java.io.File;
import org.apache.commons.io.FileUtils;
import net.minidev.json.JSONArray;

public class DataProviderUtils {

    @DataProvider(name = "getJsonTestData")
    public static Object[] getJsonTestData() {
        Object[] obj = null;
        try {
            String jsonTestData = FileUtils.readFileToString(new File(FrameworkConstants.getJsonTestDataPath()), "UTF-8");
            JSONArray jsonArray = JsonPath.read(jsonTestData, "$");
            obj = new Object[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                obj[i] = jsonArray.get(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @DataProvider(name = "getExcelTestData")
    public static Object[][] getExcelTestData() {
        String query = "select * from Sheet1 where Run='Yes'";
        Object[][] objArray = null;
        Map<String, String> testData = null;
        List<Map<String, String>> testDataList = null;
        Fillo fillo = new Fillo();
        Connection connection = null;
        Recordset recordset = null;

        try {
            connection = fillo.getConnection(FrameworkConstants.getExcelTestDataPath());
            recordset = connection.executeQuery(query);
            testDataList = new ArrayList<>();

            while (recordset.next()) {
                testData = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
                for (String field : recordset.getFieldNames()) {
                    testData.put(field, recordset.getField(field));
                }
                testDataList.add(testData);
            }

            objArray = new Object[testDataList.size()][1];
            for (int i = 0; i < testDataList.size(); i++) {
                objArray[i][0] = testDataList.get(i);
            }
        } catch (FilloException e) {
            e.printStackTrace();
        } finally {
            if (recordset != null) {
                recordset.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return objArray;
    }

    @DataProvider(name = "getCsvTestData")
    public static Object[][] getCsvTestData() {
        CSVReader csvReader = null;
        List<Map<String, String>> testDataList = null;
        Object[][] objArray = null;

        try {
            csvReader = new CSVReader(new FileReader(FrameworkConstants.getTestDataCsvPath()));
            testDataList = new ArrayList<>();
            String[] line;
            String[] headers = csvReader.readNext(); // Read header row

            while ((line = csvReader.readNext()) != null) {
                Map<String, String> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
                for (int i = 0; i < headers.length; i++) {
                    map.put(headers[i], line[i]);
                }
                testDataList.add(map);
            }

            objArray = new Object[testDataList.size()][1];
            for (int i = 0; i < testDataList.size(); i++) {
                objArray[i][0] = testDataList.get(i);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        } finally {
            if (csvReader != null) {
                try {
                    csvReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return objArray;
    }
}

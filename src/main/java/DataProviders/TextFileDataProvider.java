package main.java.DataProviders;

import org.testng.Reporter;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TextFileDataProvider {

    @DataProvider(name = "getExpectedCarOutputs")
    public static Object[][] getExpectedCarOutputs(String testName) {

        Object[][] returnData = null;

        try {


            Object[] Lines = Files.readAllLines(Paths.get("datafiles/" + testName + "_output.txt")).toArray();
            String[] parameterNames = null;


            List <HashMap<String, String>> arrayMapList = new ArrayList<>();
            HashMap<String, String> hashMapItems;

            for (int i = 0; i < Lines.length; i++) {

                String line = (String) Lines[i];
                if ( line.trim().length() == 0)
                    continue;


                hashMapItems = new HashMap<String, String>();


                if (i == 0) {

                    parameterNames =line.split(",");


                } else {
                    String[] values = line.split(",");


                    for (int j = 0; j < values.length; j++) {

                        if (values[j].endsWith("km")){
                            hashMapItems.put(parameterNames[j], values[j] + values[j + 1]);
                        j = j + 1;
                    }
                        else
                            hashMapItems.put(parameterNames[j], values[j]);
                    }


                    arrayMapList.add(hashMapItems);
                }


            }


            returnData = new Object[arrayMapList.size()][1];

            for (int i = 0; i < arrayMapList.size(); i++) {

                returnData[i][0] = arrayMapList.get(i);
            }

        } catch (Exception e) {

            Reporter.log("<span>Dataprovider Error: Please use the right data file format.</span>"
                    + "<span>File format must be .txt and File name must be same as the test method name  </span>"
                    + "<span>Parameters ad Values must be seperated by comma(,) and number of parameters and values must be equal. </span>", true);

            System.out.println("Dataprovider Exception: " + e.getMessage());


        }
        return returnData;
    }

    @DataProvider(name = "getRegistrationNumbers")
    public static Object[][] getRegistrationNumbers(Method testName) throws IOException {

        Scanner fileScanner = new Scanner(new File("datafiles/" + testName.getName()+"_input.txt"));


        List<HashMap<String, String>> regNumbers = new ArrayList<>();
        String strPattern = "[A-Z]{2}[0-9]{2}[ ]?[A-Z]{3}";
        Pattern pattern =  Pattern.compile(strPattern);
        Matcher matcher = null;
        while(fileScanner.hasNextLine()){
            String line = fileScanner.nextLine();

            if ( line.trim().length() == 0)
                continue;
            matcher = pattern.matcher(line);
            int start=0;

            while (matcher.find(start)) {
                    HashMap<String, String> regNumber = new HashMap();
                    regNumber.put("RegNumber", matcher.group(0));
                    regNumbers.add(regNumber);
                    start = matcher.end();


            }


        }



        Object [][] o = new Object[regNumbers.size()][1];


        for (int i=0; i<regNumbers.size(); i++){
            o[i][0] =regNumbers.get(i);
        }
        return o;
    }

}

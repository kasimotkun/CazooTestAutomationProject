package main.java.Utils;

import com.opencsv.CSVReader;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.apache.poi.ss.usermodel.CellType.*;


public class CsvToExcel {

    public static final char FILE_DELIMITER = ',';
    public static final String FILE_EXTN = ".xlsx";
    public static final String FILE_NAME = "EXCEL_DATA_"+Helper.getRandomNumberInRange(100000,200000);


    private static Logger logger = Logger.getLogger(CsvToExcel.class);

    public static Object[] convertCsvToXls(String xlsFileLocation, String csvFilePath) {
        ArrayList<String> eanIdList = new ArrayList<String>();
        XSSFSheet sheet = null;
        CSVReader reader = null;
        Workbook workBook = null;
        String generatedXlsFilePath = "";
        FileOutputStream fileOutputStream = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {

            /**** Get the CSVReader Instance & Specify The Delimiter To Be Used ****/
            String[] nextLine;
            reader = new CSVReader(new FileReader(csvFilePath), FILE_DELIMITER);

            workBook = new XSSFWorkbook();
            sheet = (XSSFSheet) workBook.createSheet("Sheet");
            DataFormat fmt = workBook.createDataFormat();
            CellStyle textStyle = workBook.createCellStyle();
            textStyle.setDataFormat(fmt.getFormat("@"));

            int sameSaleId=0;
            int VERLENINGSALE=0;
            int rowNum = 0;
            logger.info("Creating New .Xls File From The Already Generated .Csv File");
            while((nextLine = reader.readNext()) != null) {

                Row currentRow = sheet.createRow(rowNum++);
                for(int i=0; i < nextLine.length; i++) {
                  //  sheet.setDefaultColumnStyle(i, textStyle);
                   if( nextLine[i].equals("SSSSS") || nextLine[i].equals("CCCCC") )
                        currentRow.createCell(i).setCellValue(Integer.parseInt(Helper.getRandomNumberInRange(200001,300000)));
                    else if( nextLine[i].equals("SAMESALEID1")) {
                        sameSaleId = Integer.parseInt(Helper.getRandomNumberInRange(200001, 300000));
                        currentRow.createCell(i).setCellValue(sameSaleId);
                    }
                   else if( nextLine[i].equals("SAMESALEID2"))
                       currentRow.createCell(i).setCellValue(sameSaleId);
                   else if( nextLine[i].equals("VERLENINGSALE1")) {
                       VERLENINGSALE = Integer.parseInt(Helper.getRandomNumberInRange(200001, 300000));
                       currentRow.createCell(i).setCellValue(VERLENINGSALE);
                   }
                   else if( nextLine[i].equals("VERLENINGSALE2"))
                       currentRow.createCell(i).setCellValue(VERLENINGSALE);
                    else if( nextLine[i].equals("NEWDATE"))
                          currentRow.createCell(i).setCellValue(simpleDateFormat.format(new Date()));
                    else if( nextLine[i].startsWith("1112")) {
                       currentRow.createCell(i).setCellValue(nextLine[i]);
                       eanIdList.add(nextLine[i]);
                   }
                      else if( nextLine[i].equals(""))
                          currentRow.createCell(i).setCellType(Cell.CELL_TYPE_BLANK);
                      else{
                          currentRow.createCell(i).setCellValue(nextLine[i]);

                     }

                }
            }

            generatedXlsFilePath = xlsFileLocation + FILE_NAME + FILE_EXTN;
            logger.info("The File Is Generated At The Following Location?= " + generatedXlsFilePath);

            fileOutputStream = new FileOutputStream(generatedXlsFilePath.trim());
            workBook.write(fileOutputStream);
        } catch(Exception exObj) {
            logger.error("Exception In convertCsvToXls() Method?=  " + exObj);
        } finally {
            try {

                /**** Closing The Excel Workbook Object ****/
                workBook.close();

                /**** Closing The File-Writer Object ****/
                fileOutputStream.close();

                /**** Closing The CSV File-ReaderObject ****/
                reader.close();
            } catch (IOException ioExObj) {
                logger.error("Exception While Closing I/O Objects In convertCsvToXls() Method?=  " + ioExObj);
            }
        }

        return new Object[]{generatedXlsFilePath,eanIdList};
    }
}
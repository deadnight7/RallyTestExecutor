/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rallyTestExecutorPkg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author cpandit
 */
public class ExcelFileReadWrite {


    public void readXLSXFile(File file) {

        //InputStream XlsxFileToRead = null;
        XSSFWorkbook workbook = null;
        try {
            //XlsxFileToRead = new FileInputStream(fileName);
            
            //Getting the workbook instance for xlsx file
            workbook = new XSSFWorkbook(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        //getting the first sheet from the workbook using sheet name. 
        // We can also pass the index of the sheet which starts from '0'.
        XSSFSheet sheet = workbook.getSheet("Main");
        XSSFRow row;
        XSSFCell cell;

        String strWorkspaceName = sheet.getRow(0).getCell(1).getStringCellValue();//"Cisco Information Technology";
        String strProjectName = sheet.getRow(1).getCell(1).getStringCellValue(); //"PSA-DPM";
        String strUserStoryNumber = sheet.getRow(2).getCell(1).getStringCellValue();//"US89611";
        String strTestFolder = sheet.getRow(4).getCell(1).getStringCellValue();//"TF15468";

        String strPriority = sheet.getRow(5).getCell(1).getStringCellValue();//Important
        String strMethod = sheet.getRow(6).getCell(1).getStringCellValue();//Manual
        String strType = sheet.getRow(7).getCell(1).getStringCellValue();//Progression
        String strOwner = sheet.getRow(8).getCell(1).getStringCellValue();//Progression
        
        
        /*
        //Iterating all the rows in the sheet
        Iterator rows = sheet.rowIterator();
        while (rows.hasNext()) {
            row = (XSSFRow) rows.next();

            //Iterating all the cells of the current row
            Iterator cells = row.cellIterator();

            while (cells.hasNext()) {
                cell = (XSSFCell) cells.next();
           
                if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                    System.out.print(cell.getStringCellValue() + " ");
                } else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                    System.out.print(cell.getNumericCellValue() + " ");
                } else if (cell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
                    System.out.print(cell.getBooleanCellValue() + " ");

                } else { // //Here if require, we can also add below methods to
                    // read the cell content
                    // XSSFCell.CELL_TYPE_BLANK
                    // XSSFCell.CELL_TYPE_FORMULA
                    // XSSFCell.CELL_TYPE_ERROR
                }
            }
            System.out.println();
        }*/

    }

    public static void main(String[] args) {
        ExcelFileReadWrite readXlsx = new ExcelFileReadWrite();
        String fileName = "C:\\Users\\cpandit\\Documents\\Cisco\\Rally\\RallyTestExecutor\\RallyTestExecutor\\Test_Uploader_ExcelTemplate\\template.xlsx";
        File file = new File(fileName);
        readXlsx.readXLSXFile(file);

    }
}

package com.itl.pns.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.itl.pns.entity.CustomerEntity;



public class ExcelHelper {
	
	static Logger LOGGER = Logger.getLogger(ExcelHelper.class);

  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  static String[] HEADERs = { "Id", "Title", "Description", "Published" };
  static String SHEET = "Customers";
  
  public static void main(String argh[]){
	  
	  FileInputStream fileIp;
	try {
		fileIp = new FileInputStream("D:/customerData.xlsx");
		  excelToCustEntity(fileIp);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		LOGGER.info("Exception:", e);
	}
	
	
  }

  public static boolean hasExcelFormat(MultipartFile file) {

    if (!TYPE.equals(file.getContentType())) {
      return false;
    }

    return true;
  }

  public static List<CustomerEntity> excelToCustEntity(InputStream inputStream) {
    try {
    	
      Workbook workbook = new XSSFWorkbook(inputStream);
      Sheet firstSheet = workbook.getSheetAt(0);
    
      Iterator<Row> rows = firstSheet.iterator();

      List<CustomerEntity> custData = new ArrayList<CustomerEntity>();

      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();

        // skip header
        if (rowNumber == 0) {
          rowNumber++;
          continue;
        }

        Iterator<Cell> cellsInRow = currentRow.iterator();

        CustomerEntity custEntity = new CustomerEntity();

        int cellIdx = 0;
        while (cellsInRow.hasNext()) {
          Cell currentCell = cellsInRow.next();
          int columnIndex = currentCell.getColumnIndex();
          switch (columnIndex) {
          case 0:
        	  custEntity.setCif(currentCell.getStringCellValue());
            break;
            
          case 1:
        	  custEntity.setSalutation(currentCell.getStringCellValue());
            break;

          case 2:
        	  custEntity.setCustomername(currentCell.getStringCellValue());
            break;

          case 3:
        	  custEntity.setUsername(currentCell.getStringCellValue());
            break;
            
          case 4:
        	  custEntity.setMpin(currentCell.getStringCellValue());
            break;

          case 5:
        	  custEntity.setUserpassword(currentCell.getStringCellValue());
            break;

          case 6:
        	  custEntity.setEmail(currentCell.getStringCellValue());
            break;
            
          case 7:
        	  custEntity.setMobile(currentCell.getStringCellValue());
            break;

          case 8:
        	  custEntity.setAppid(Integer.valueOf(currentCell.getStringCellValue()));
            break;

          case 9:
        	  custEntity.setPreferedlanguage(currentCell.getStringCellValue());
            break;
            
          case 10:
        	  custEntity.setDob(currentCell.getStringCellValue());
            break;
            
          case 11:
        	  String mpinEnb= currentCell.getStringCellValue();
        	  custEntity.setIsmpinenabled(mpinEnb.charAt(0));
            break;
            
          case 12:
        	  String webEnb= currentCell.getStringCellValue();
        	  custEntity.setIswebenabled(webEnb.charAt(0));
            break;
            
   

          default:
            break;
          }

          cellIdx++;
        }

        custData.add(custEntity);
      }

 

      return custData;
    } catch (IOException e) {
    	LOGGER.info("Exception:", e);
      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
    }
  }
}
package utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import au.com.bytecode.opencsv.CSVReader;

public class ExcelData {
	public static XSSFSheet sheet;
	public static Map<String,String> testInputData = getAllMapTestData();
	/**
	 * This method implements how to get the specified sheetname.
	 * @param firepath
	 * @param sheetname
	 */
	public static void setPath(String firepath,String sheetname){
		FileInputStream fs;
		try {
			fs = new FileInputStream(firepath);
			XSSFWorkbook workbook = new XSSFWorkbook(fs);
			sheet = workbook.getSheet(sheetname);
		} catch (IOException e) {
			Log.error("Package utilly||Class ExcelData||Method setPath " + e.getMessage());
			e.printStackTrace();
		}
				
	}
	/**
	 * This method implements how to get the specified cell value based on the row number and column number.
	 * @param iTestCaseRow
	 * @param column
	 * @return
	 */
	public static String getCellData(int iTestCaseRow ,int column){
		
		XSSFCell cell = sheet.getRow(iTestCaseRow).getCell(column);
		String cellvalue = null;
		switch(cell.getCellType()){
		case XSSFCell.CELL_TYPE_NUMERIC:			
			cellvalue = cell.getRawValue();
			break;
		case XSSFCell.CELL_TYPE_STRING:
			cellvalue = cell.getStringCellValue();
			break;
		case XSSFCell.CELL_TYPE_BLANK:
			cellvalue = "";
			break;
		default:
			Log.warn("Excel data type does not exist. Cell type is: " +cell.getCellType());
		}
	    return cellvalue;	
		
	}
	
	public static int getRowContains(String key, int column){
		
		int i;
		int rowcount = sheet.getLastRowNum();
		for(i=1;i<rowcount;i++){
			if(ExcelData.getCellData(i, column).equalsIgnoreCase(key)){
				break;
			}
		}
		if(i>=rowcount){
			Log.error("[getRowContains]: Can not find "+ key);
		}
		return i;
	}
	
	public static Map<String,String> getAllMapTestData(){
		Map<String,String> mapData = new HashMap<String,String>();
		setPath(Constants.path+Constants.filename, Constants.sheetname);
		int rowNumber = sheet.getPhysicalNumberOfRows();
		XSSFCell cell1=null;
		XSSFCell cell2=null;
		for(int i=1;i<rowNumber;i++){
			cell1=sheet.getRow(i).getCell(Constants.keycolumn);
			if(cell1.getCellType()!=XSSFCell.CELL_TYPE_BLANK){
				String keyValue = cell1.getStringCellValue().trim().toLowerCase();
				String value;
				cell2 = sheet.getRow(i).getCell(Constants.column);
				switch(cell2.getCellType()){
				case XSSFCell.CELL_TYPE_NUMERIC:			
					value = cell2.getRawValue().toString().trim().toLowerCase();
					mapData.put(keyValue, value);
					break;
				case XSSFCell.CELL_TYPE_STRING:
					value = cell2.getStringCellValue().trim().toLowerCase();
					mapData.put(keyValue, value);
					break;
				case XSSFCell.CELL_TYPE_BLANK:
					value = "";
					mapData.put(keyValue, value);
					break;
				default:
					Log.warn("Excel data type does not exist. Cell type is: " +cell2.getCellType());
				}
				
				
			}
		}
		return mapData;
		
	}
	public static String getMapData (String skey){
	//	Map<String,String> data = getAllMapTestData();
		return testInputData.get(skey.toLowerCase());
		
	}  
	
	public static List<String[]> getTestDataFromCSVFile(){
		CSVReader csvReader;
		List<String[]> lists =new ArrayList<>();
				
		try {
				csvReader = new CSVReader(new FileReader(Constants.path + Constants.csvFileName));
				lists = csvReader.readAll();
				csvReader.close();
			} catch (IOException e) {
				Log.error("Fail to get the web locators from ObjectRepository file. ");
				e.printStackTrace();
			} 
				
		return lists;
	}
	
	public static List<String[]> getLocatorsFromCSVFile(){
		CSVReader csvReader;
		List<String[]> lists =new ArrayList<>();
				
		try {
				csvReader = new CSVReader(new FileReader(Constants.path + Constants.objectFile));
				lists = csvReader.readAll();
				csvReader.close();
			} catch (IOException e) {
				Log.error("Fail to get the web locators from ObjectRepository file. ");
				e.printStackTrace();
			} 
				
		return lists;
	}
}

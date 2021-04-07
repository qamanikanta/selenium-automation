package src.com.utilities;
/**
 * This class for find content related Operations
 * 
 * @author Manikanta
 * @version 1.0
 *
 */



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellRange;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import src.com.core.HybridConstants;
import src.com.test.TestBase;
import com.spire.xls.Worksheet;
import com.spire.xls.collections.AutoFiltersCollection;

import src.com.core.HybridConstants;

public class XLUtils {

	public static FileInputStream fileinputstream;
	public static FileOutputStream fileoutputstream;
	public static XSSFWorkbook workbook;
	public static XSSFSheet worksheet;
	public static XSSFRow row;
	public static XSSFCell cell;

	/* This method is to get excel row count */
	public static int getRowCount(String Xlfile, String Xlsheet) throws IOException {
		fileinputstream = new FileInputStream(Xlfile);
		workbook = new XSSFWorkbook(fileinputstream);
		worksheet = workbook.getSheet(Xlsheet);
		int rowcount = worksheet.getLastRowNum();
		workbook.close();
		fileinputstream.close();
		return rowcount;
	}

	/* This method is to get excel cell count */
	public static int getCellCount(String Xlfile, String Xlsheet, int rownum) throws IOException {
		fileinputstream = new FileInputStream(Xlfile);
		workbook = new XSSFWorkbook(fileinputstream);
		worksheet = workbook.getSheet(Xlsheet);
		row = worksheet.getRow(rownum);
		int cellcount = row.getLastCellNum();
		workbook.close();
		fileinputstream.close();
		return cellcount;
	}

	/* This method is to get excel cell data */
	public static String getCellData(String Xlfile, String Xlsheet, int rownum, int colnum) throws IOException {
		fileinputstream = new FileInputStream(Xlfile);
		workbook = new XSSFWorkbook(fileinputstream);
		worksheet = workbook.getSheet(Xlsheet);
		row = worksheet.getRow(rownum);
		cell = row.getCell(colnum);
		String data;
		try {
			DataFormatter formatter = new DataFormatter();
			String cellData = formatter.formatCellValue(cell);
			return cellData;
		} catch (Exception e) {
			data = " ";
		}
		workbook.close();
		fileinputstream.close();
		return data;
	}

	/* This method is to set excel cell data */
	public static void setCellData(String Xlfile, String Xlsheet, int rownum, int colnum, String data)
			throws IOException {
		fileinputstream = new FileInputStream(Xlfile);
		workbook = new XSSFWorkbook(fileinputstream);
		worksheet = workbook.getSheet(Xlsheet);
		row = worksheet.getRow(rownum);
		cell = row.createCell(colnum);
		cell.setCellValue(data);
		fileoutputstream = new FileOutputStream(Xlfile);
		workbook.write(fileoutputstream);
		workbook.close();
		fileinputstream.close();
		fileoutputstream.close();
	}

	/*
	 * This method used for check XLSX files in directory return filename
	 */

	public static File isFileDownloaded(String dirPath, String ext, String filename) {
		File file = null;
		try {
			File dir = new File(dirPath);
			File[] files = dir.listFiles();

			/*
			 * if (files == null || files.length == 0) { flag = false; }
			 */
			if (dir.isDirectory() && files.length > 0) {
				for (int i = 1; i < files.length; i++) {
					if (files[i].getName().contains(ext)) {
						// flag = true;
						if (files[i].getName().equals(filename)) {
							file = files[i];
						}
					}
				}
			} else {
				System.out.println("File not found");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	/*
	 * This method use for delete file if exist from directory
	 */
	public static void deleteIfFileExist(String filename) {
		try {
			// File file = isFileDownloaded(filepath, ext, filename);
			File file = new File(filename);

			if (file.exists()) {

				if (file.delete()) {
					System.out.println("File deleted: " + file);
				} else {
					System.out.println("unable to delete file" + file);
				}
			} else {
				System.out.println("File not found");
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	/*
	 * This method is used for return sheet name from excel/csv file Return
	 * Sheetname
	 */
	public static String getSheetName(String filepath, String sheetname) {
		FileInputStream fileInputStream = null;
		String sheet = null;
		try {
			fileInputStream = new FileInputStream(filepath);

			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
			// for each sheet in the workbook
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {

				sheet = workbook.getSheetName(i);
				if (sheet == sheetname) {
					return sheet;
				} else if (sheet == sheetname) {
					return sheet;
				} else {
					return "Sheet Name not found";
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sheetname;
	}

	/* This method is to get excel data */
	public Object[][] testData() throws IOException {

		String filepath1 = System.getProperty("user.home");
		String filepath = filepath1
				+ "\\Downloads\\reimb_tech_comp_Atezolizumab-TECENTRIQ-and-comparators_km2_20201027";
		System.out.println("Filepath is:" + filepath);
		String sheet = getSheetName(filepath, HybridConstants.SHEETREIMBTECHCOMP);
		Object[][] excelData = null;
		// int rownum = XLUtils.getRowCount(filepath, sheet);
		// int colnum = XLUtils.getCellCount(filepath, sheet, 1);
		int rownum = 1;
		int colnum = 3;
		excelData = new String[rownum][colnum];
		System.out.println("Row num is:" + colnum);

		for (int rowindex = 1; rowindex <= rownum; rowindex++) {
			for (int colindex = 0; colindex < colnum; colindex++) {
				excelData[rowindex - 1][colindex] = XLUtils.getCellData(filepath, sheet, rowindex, colindex);
				System.out.println(excelData[rowindex - 1][colindex]);
			}
		}

		return excelData;
	}

	/*
	 * method defined for reading a cell
	 */
	public static String readCellData(int vRow, int vColumn, String sheetname) {
		String value = null; // variable for storing the cell value
		Workbook wb = null; // initialize Workbook null
		String filename = excelFileName();
		String downloadptah = downloadPath();
		String filepath = downloadptah + filename;

		try {
			// reading data from a file in the form of bytes
			FileInputStream fis = new FileInputStream(filepath);
			// constructs an XSSFWorkbook object, by buffering the whole stream into the
			// memory
			wb = new XSSFWorkbook(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Sheet sheet = wb.getSheet(sheetname);
		// Sheet sheet = wb.getSheetAt(0); // getting the XSSFSheet object at given
		// index
		Row row = sheet.getRow(vRow); // returns the logical row
		Cell cell = row.getCell(vColumn); // getting the cell representing the given column
		value = cell.getStringCellValue(); // getting cell value
		return value; // returns the cell value
	}

	/* This method is to get excel cell data */
	public static String readCellData1(int vRow, int vColumn, String sheetname, String ContentComponent,
			String ProductINN) {
		String value = null; // variable for storing the cell value
		Workbook wb = null; // initialize Workbook null
		String filename = excelFileName1(ContentComponent, ProductINN);
		String downloadpath = downloadPath();
		String filepath = downloadpath + filename;

		try {
			// reading data from a file in the form of bytes
			FileInputStream fis = new FileInputStream(filepath);
			// constructs an XSSFWorkbook object, by buffering the whole stream into the
			// memory
			wb = new XSSFWorkbook(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Sheet sheet = wb.getSheet(sheetname);
		// Sheet sheet = wb.getSheetAt(0); // getting the XSSFSheet object at given
		// index
		Row row = sheet.getRow(vRow); // returns the logical row
		Cell cell = row.getCell(vColumn); // getting the cell representing the given column
		value = cell.getStringCellValue(); // getting cell value
		return value; // returns the cell value
	}

	/* This method is to get excel cell data */
	public static String readCellData_comparators(int vRow, int vColumn, String sheetname, String ContentComponent,
			String ProductINN) {
		String value = null; // variable for storing the cell value
		Workbook wb = null; // initialize Workbook null
		String filename = excelFileName_comparators(ContentComponent, ProductINN);
		String downloadpath = downloadPath();
		String filepath = downloadpath + filename;

		try {
			// reading data from a file in the form of bytes
			FileInputStream fis = new FileInputStream(filepath);
			// constructs an XSSFWorkbook object, by buffering the whole stream into the
			// memory
			wb = new XSSFWorkbook(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Sheet sheet = wb.getSheet(sheetname);
		// Sheet sheet = wb.getSheetAt(0); // getting the XSSFSheet object at given
		// index
		Row row = sheet.getRow(vRow); // returns the logical row
		Cell cell = row.getCell(vColumn); // getting the cell representing the given column
		value = cell.getStringCellValue(); // getting cell value
		return value; // returns the cell value
	}

	/*
	 * Return current date format
	 */
	public static String YYYYMMDDDateFormat() {
		Date date = new Date();
		SimpleDateFormat DateFor = new SimpleDateFormat("yyyyMMdd");
		String stringDate = DateFor.format(date);
		return stringDate;

	}
	/*
	 * Return download complete path
	 */

	public static String downloadPath() {
		String dirpath = HybridConstants.DIRPATH + HybridConstants.BACK_SLASH + HybridConstants.DOWNLOADFOLDER
				+ HybridConstants.BACK_SLASH;
		return dirpath;
	}

	/*
	 * getZipFile name return filename
	 */
	public static String zipFileName() {
		String zipfilename = HybridConstants.ZIPFILENAME + YYYYMMDDDateFormat() + HybridConstants.DOT
				+ HybridConstants.ZIPEXT;
		return zipfilename;
	}

	/*
	 * getDocx file name return filename
	 */
	public static String docxFileName() {
		String docxfilename = HybridConstants.DOCXFILENAME + YYYYMMDDDateFormat() + HybridConstants.DOT
				+ HybridConstants.DOCXEXT;
		return docxfilename;
	}

	/*
	 * Excel file name return filename
	 */
	public static String excelFileName() {
		String xlfilename = HybridConstants.EXCELFILENAME + HybridConstants.UNDER_SCORE + HybridConstants.SYSTEMUSER
				+ HybridConstants.UNDER_SCORE + YYYYMMDDDateFormat() + HybridConstants.DOT + HybridConstants.XLEXT;
		return xlfilename;
	}

	/* This method is to get excel filename */
	public static String excelFileName1(String ContentComponent, String ProductINN) {
		String xlfilename = ContentComponent + HybridConstants.UNDER_SCORE + ProductINN + HybridConstants.UNDER_SCORE
				+ HybridConstants.SYSTEMUSER + HybridConstants.UNDER_SCORE + YYYYMMDDDateFormat()
				+ HybridConstants.DOT + HybridConstants.XLEXT;
		return xlfilename;
	}

	/* This method is to get word filename */
	public static String wordFileName(String abbreviation, String TechnologyINN_RocheProduct, String Indication,
			String LineOfTherapy, String Histology) {
		String xlfilename = abbreviation + HybridConstants.UNDER_SCORE + TechnologyINN_RocheProduct
				+ HybridConstants.UNDER_SCORE + Indication + HybridConstants.UNDER_SCORE + LineOfTherapy
				+ HybridConstants.UNDER_SCORE + Histology + HybridConstants.UNDER_SCORE + YYYYMMDDDateFormat()
				+ HybridConstants.DOT + HybridConstants.Docx;
		return xlfilename;
	}
	

	/* This method is to get excel filename */
	public static String excelFileName_comparators(String ContentComponent, String ProductINN) {
		String xlfilename = ContentComponent + HybridConstants.UNDER_SCORE + ProductINN + "-and-comparators"
				+ HybridConstants.UNDER_SCORE + HybridConstants.SYSTEMUSER + HybridConstants.UNDER_SCORE
				+ YYYYMMDDDateFormat() + HybridConstants.DOT + HybridConstants.XLEXT;
		return xlfilename;
	}
	
	/* This method is to get word filename */
	public static String wordFileName_comparators(String ContentComponent, String ProductINN) {
		String xlfilename = ContentComponent + HybridConstants.UNDER_SCORE + ProductINN + "-and-comparators"
				+ HybridConstants.UNDER_SCORE + HybridConstants.SYSTEMUSER + HybridConstants.UNDER_SCORE
				+ YYYYMMDDDateFormat() + HybridConstants.DOT + HybridConstants.Docx;
		return xlfilename;
	}
	

	/*
	 * Check file name basis on ext Return filename
	 */
	public static String checkFile(String dirpath, String filenames, String ext) {

		File f = new File(dirpath);
		File[] listOfFiles = f.listFiles();
		String filename1 = null;
		if (f.isDirectory() && listOfFiles.length > 0) {

			for (File file : listOfFiles) {
				if (file.isFile()) {

					if (file.getName().endsWith(ext)) {

						if (file.getName().equals(filenames)) {
							filename1 = file.getName();
						}
					} else {

					}
				}
			}

		} else {
			System.out.println("Empty dir");
		}
		return filename1;
	}

	/*
	 * This method used for read zip file returns list of files present in zipfile
	 */
	public static List<String> readZipFile(String dirpath, String filename, String ext) {
		List<String> list = new ArrayList<String>();
		try {
			String filename1 = XLUtils.checkFile(dirpath, filename, HybridConstants.ZIPEXT);
			ZipFile zipFile = new ZipFile(dirpath + filename1);
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				list.add(entry.getName());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/* This method is to take screenshot */
	public static String takeScreenShot(ITestResult result, String reportsPath) {

		// logger.info("*** Taking screenshot for " + result.getMethod().getMethodName()
		// + "...");
		// Reporter.setCurrentTestResult(result);
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// time stamp
		String targetLocation = null;
		String testClassName = getTestClassName(result.getInstanceName()).trim();
		String testMethodName = result.getName().trim();
		String screenShotName = testMethodName + "_" + timeStamp + ".png";
		// logger.info("screenShotName reports path - " + screenShotName);
		// logger.info("Screenshots reports path - " + reportsPath);
		try {
			// Screen shot folder
			File file = new File(reportsPath + testClassName);
			File screenshotFile = ((TakesScreenshot) src.com.test.TestBase.driver).getScreenshotAs(OutputType.FILE);
			targetLocation = reportsPath + testClassName + "\\" + screenShotName;// define location
			File targetFile = new File(targetLocation);
			FileHandler.copy(screenshotFile, targetFile);
		} catch (FileNotFoundException e) {
			// logger.info("File not found exception occurred while taking screenshot " +
			// e.getMessage());
		} catch (Exception e) {
			// logger.info("An exception occurred while taking screenshot " + e.getCause());
		}
		return targetLocation;

	}

	/* This method is to take screenshot */
	public static String takeScreenshot_Step(String step, String reportPathStep) {
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String targetLocation = null;
		String screenShotName = timeStamp + "_" + step + ".png";

		try {
			File file = new File(step);
			File screenshotFile = ((TakesScreenshot) src.com.test.TestBase.driver).getScreenshotAs(OutputType.FILE);
			targetLocation = reportPathStep + "\\" + screenShotName;// define location
			File targetFile = new File(targetLocation);
			FileHandler.copy(screenshotFile, targetFile);
		} catch (FileNotFoundException e) {
		} catch (Exception e) {
		}

		return targetLocation;
	}

	/* This method is to get test class name */
	public static String getTestClassName(String testName) {
		String[] reqTestClassname = testName.split("\\.");
		int i = reqTestClassname.length - 1;
		// logger.info("Required Test Name : " + reqTestClassname[i]);
		return reqTestClassname[i];
	}

	/* This method is to set filter on excel file */
	public static void setFilterExcel(String sheetname, String ProductINN, String filterColumnRowStart,
			String filterColumnRowEnd) throws IOException {
		String value = null; // variable for storing the cell value
		Workbook wb = null; // initialize Workbook null
		String filename = excelFileName1(sheetname, ProductINN);
		String downloadpath = downloadPath();
		String filepath = downloadpath + filename;

		try {
			// reading data from a file in the form of bytes
			FileInputStream fis = new FileInputStream(filepath);
			// constructs an XSSFWorkbook object, by buffering the whole stream into the
			// memory
			wb = new XSSFWorkbook(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Sheet sheet = wb.getSheet(sheetname);
		sheet.setAutoFilter(CellRangeAddress.valueOf(filterColumnRowStart + ":" + filterColumnRowEnd));
		FileOutputStream out = new FileOutputStream(new File(filepath));
		wb.write(out);
		out.close();

	}

	/* This method is to set filter on excel file */
	public static void setFilterExcel_comparators(String sheetname, String ProductINN, String filterColumnRowStart,
			String filterColumnRowEnd) throws IOException {
		String value = null; // variable for storing the cell value
		Workbook wb = null; // initialize Workbook null
		String filename = excelFileName_comparators(sheetname, ProductINN);
		String downloadpath = downloadPath();
		String filepath = downloadpath + filename;

		try {
			// reading data from a file in the form of bytes
			FileInputStream fis = new FileInputStream(filepath);
			// constructs an XSSFWorkbook object, by buffering the whole stream into the
			// memory
			wb = new XSSFWorkbook(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Sheet sheet = wb.getSheet(sheetname);
		sheet.setAutoFilter(CellRangeAddress.valueOf(filterColumnRowStart + ":" + filterColumnRowEnd));
		FileOutputStream out = new FileOutputStream(new File(filepath));
		wb.write(out);
		out.close();

	}

	/* This method is to set specific filter on excel file */
	public static void setSpecificFilterExcel(String sheetname, String filter, String ProductINN) throws IOException {
		String filename = excelFileName1(sheetname, ProductINN);
		String downloadpath = downloadPath();
		String filepath = downloadpath + filename;

		com.spire.xls.Workbook wb = new com.spire.xls.Workbook(); // initialize Workbook null
		wb.loadFromFile(filepath);
		Worksheet sheet = wb.getWorksheets().get(sheetname);
		AutoFiltersCollection filters = sheet.getAutoFilters();
		filters.addFilter(0, filter);
		filters.filter();

		FileOutputStream out = new FileOutputStream(new File(filepath));
		wb.save();
		out.close();

	}

	/* This method is to set specific filter on excel file */
	public static void setSpecificFilterExcel_comparators(String sheetname, String filter, String ProductINN)
			throws IOException {
		String filename = excelFileName_comparators(sheetname, ProductINN);
		String downloadpath = downloadPath();
		String filepath = downloadpath + filename;

		com.spire.xls.Workbook wb = new com.spire.xls.Workbook(); // initialize Workbook null
		wb.loadFromFile(filepath);
		Worksheet sheet = wb.getWorksheets().get(sheetname);
		AutoFiltersCollection filters = sheet.getAutoFilters();
		filters.addFilter(0, filter);
		filters.filter();

		FileOutputStream out = new FileOutputStream(new File(filepath));
		wb.save();
		out.close();
	}

}
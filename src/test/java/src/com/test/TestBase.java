
/**
 * This class for find content related Operations
 * 
 * @author Manikanta
 * @version 1.0
 *
 */

package src.com.test;
import src.com.utilities.XLUtils;
import src.com.utilities.ReadConfig;

import src.com.core.HybridConstants;
import src.com.core.PageBase;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.asserts.Assertion;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class TestBase {

	public static final String PROJECTDIR = System.getProperty("user.dir");

	private static final By By = null;

	ReadConfig readconfig = new ReadConfig();
	//EncryptPassword encyt=new EncryptPassword();
	
	public String baseURL = readconfig.getApplicationURL();
	public String usernameDecrypt = readconfig.getprojectLeadUserName();
	public String username_ProjectLead = readconfig.getprojectLeadUserName();
	public String username_DataEntry = readconfig.getDataEntryUserName();
	public String username_Reviewer = readconfig.getReviewerUserName();
	public String username_Approvar = readconfig.getApprovarUserName();
	public String password = readconfig.getPassword();

	/*String projectLead_UsernameDecrypt=encyt.decrypt(username_ProjectLead);
	String datEntry_UsernameDecrypt=encyt.decrypt(username_DataEntry);
	String reviewer_UsernameDecrypt=encyt.decrypt(username_Reviewer);
	String approvar_UsernameDecrypt=encyt.decrypt(username_Approvar);
	String passwordDecrypt = encyt.decrypt(password);*/	
	public static WebDriver driver;
	PageBase pbase = new PageBase(driver);
	private String destfile;
	public static Logger logger;
	Assertion assertText = new Assertion();

	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	public String testReportPath = HybridConstants.USERDIR;
	public String reportsPathFail = testReportPath + "\\screenshots\\failed\\";
	public String reportsPathSkip = testReportPath + "\\screenshots\\skip\\";
	public String reportsPathPass = testReportPath + "\\screenshots\\passed\\";
	public String reportsPathStep = testReportPath + "\\Screenshots\\step";
		
	public MediaEntityModelProvider ScreenshotType;

	public WebDriver getDriver() {
		return driver;
	}

	@BeforeSuite
	public void beforeSuite() {

		//deleteReportFile();
		//deleteStepScreenshotFile();
		
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
		String repName = "TestReport-" + timeStamp + ".html";
		
		//htmlReporter = new ExtentHtmlReporter(HybridConstants.USERDIR + "\\target\\" + repName);
		
		htmlReporter = new ExtentHtmlReporter(HybridConstants.USERDIR + HybridConstants.ReportPath + repName);
		
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Name", "Hybrid Automation");
		extent.setSystemInfo("OS", "WINDOWS");
		extent.setSystemInfo("Host name", "localhost");
		extent.setSystemInfo("environment", "QA");
		extent.setSystemInfo("user", "Mani");

		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle("Hybrid Test Automation Project");// Title of report
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName("Hybrid Test Automation Report");// name of the report
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);// location of the chart
		htmlReporter.config().setTheme(Theme.DARK);

	}

	@BeforeMethod
	public void beforeMethod(Method method) {
		// In before method we are collecting the current running test case name
		String className = this.getClass().getSimpleName();
		test = extent.createTest(className + " - " + method.getName());
	}

	@Parameters("browser")
	@BeforeClass(alwaysRun = true)
	public void setUp(String br) {
		logger = Logger.getLogger("pocgear");
		PropertyConfigurator.configure("log4j.properties");
		if (br.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", readconfig.getChromePath());
           // ChromeOptions options = new ChromeOptions().setHeadless(readconfig.executeHeadless());
           //options.addArguments("--no-sandbox","--headless","--disable-dev-shm-usage", "--ignore-certificate-errors");
			//driver = new ChromeDriver(options);
            driver=new ChromeDriver();
		} else if (br.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", readconfig.getFirefoxPath());
			driver = new FirefoxDriver();
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		driver.get(baseURL);
		logger.info("Driver initialization completed.");

	}

	@AfterMethod
	public void getResult(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " Test case FAILED due to below issues:",
					ExtentColor.RED));
			test.fail(result.getThrowable());
			//test = extent.createTest(result.getName());
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName(), ExtentColor.RED));
			test.log(Status.FAIL,
					result.getMethod().getMethodName() + "Test is failed : " + result.getThrowable().getMessage());
			String screenshotPath = XLUtils.takeScreenShot(result, reportsPathFail);
			try {
				test.fail("Screenshot is below:" + test.addScreenCaptureFromPath(screenshotPath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (result.getStatus() == ITestResult.SUCCESS) {

			String screenshotPath = XLUtils.takeScreenShot(result, reportsPathPass);
			try {
				test.addScreenCaptureFromPath(screenshotPath);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			test.log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + " Test Case SKIPPED", ExtentColor.ORANGE));
			test.skip(result.getThrowable());
		}
	}

	

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		// Close the browser window that the driver has been focused.
		if (driver != null) {
			driver.quit();

		}
	}
	
	@AfterSuite
	public void endReport() {
		// In after suite stopping the object of ExtentReports and ExtentTest
		extent.flush();
		// driver.quit();
	}

	/* This method is to capture the screenshot */
	public String capturescreenshot(WebDriver driver, String testcasename, String path) throws IOException {
		try {
			TakesScreenshot screen = (TakesScreenshot) driver;
			File source = screen.getScreenshotAs(OutputType.FILE);
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());// time stamp
			String destfile = HybridConstants.USERDIR + "\\screenshots\\" + path + "\\" + testcasename + "-"
					+ timeStamp + ".png";
			File target = new File(destfile);
			FileUtils.copyFile(source, target);
			logger.info("Screen shot taken");
		} catch (IOException e) {
			logger.info("Capture failed" + e.getMessage());

		}
		return destfile;
	}

	/* This method is to login GEAR application */
	public void login() {
		pbase.closeFeedbackWindow();
		pbase.setUserName(usernameDecrypt);
		logger.info("user name provided");
		//pbase.setPassword(passwordDecrypt);
		logger.info("password provided");
		pbase.clickLogin();
		logger.info("logged in");
		logger.info("Login Success");

	}

	/* This method is to login GEAR application */
	public void login1() {
		pbase.setUserName(usernameDecrypt);
		logger.info("user name provided");
		//pbase.setPassword(passwordDecrypt);
		logger.info("password provided");
		pbase.clickLogin();
		logger.info("logged in");

	}
	
	/* This method is to login HEIDI application */
	public void heidilogin_ProjectLead() {
		pbase.setHeidiUserName(username_ProjectLead);
		logger.info("user name provided");
		//pbase.setHeidiPassword(passwordDecrypt);
		logger.info("password provided");
		pbase.clickHeidiLogin();
		logger.info("logged in");

	}
	
	/* This method is to login HEIDI application */
	public void heidilogin_Reviewer() {
		//pbase.setHeidiUserName(reviewer_UsernameDecrypt);
		logger.info("Reviewer user name provided");
		//pbase.setHeidiPassword(passwordDecrypt);
		logger.info("Reviewer password provided");
		pbase.clickHeidiLogin();
		logger.info("logged in");

	}
	
	/* This method is to login HEIDI application */
	public void heidilogin_DataEntryUser() {
		//pbase.setHeidiUserName(datEntry_UsernameDecrypt);
		logger.info("Data Entry user name provided");
		//pbase.setHeidiPassword(passwordDecrypt);
		logger.info("Data Entry password provided");
		pbase.clickHeidiLogin();
		logger.info("logged in");

	}

	/* This method is ensure element is present */
	public boolean isElementPresent(WebElement elementName, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.stalenessOf(elementName));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/* This method is click all the checkbox */
	public List clickAllCheckbox(String locator) throws InterruptedException {
		List<WebElement> allCheckBoxes = driver.findElements(By.xpath(locator));

		for (int i = 0; i < allCheckBoxes.size(); i++) {

			if (allCheckBoxes.get(i).getText().equals("Select all")) {
				allCheckBoxes.get(1).click();
				allCheckBoxes.get(2).click();

				break;
			} else {
				Thread.sleep(10000);
				allCheckBoxes.get(i).click();

			}
		}
		return allCheckBoxes;
	}

	/* This method is to return all checked checkbox */
	public List clickAllCheckbox1(String locator, String optionLocator) {
		List<WebElement> allCheckBoxes = driver.findElements(By.xpath(locator));

		System.out.println("Checkbox size:" + allCheckBoxes.size());
		for (int i = 0; i < allCheckBoxes.size(); i++) {
			System.out.println(allCheckBoxes.get(i).getText());

			if (allCheckBoxes.get(i).getText().equals(optionLocator)) {
				allCheckBoxes.get(i).click();
				logger.info("Select all present");
			} else if (allCheckBoxes.get(i).getText().equals(optionLocator)) {
				allCheckBoxes.get(i).click();
				break;
			}

			else {
				allCheckBoxes.get(i).click();
			}
		}
		return allCheckBoxes;
	}

	/* This method is click the element and wait */
	public boolean clickOn(String locator, int timeout) {
		new WebDriverWait(driver, timeout).ignoring(StaleElementReferenceException.class)
				.until(ExpectedConditions.elementToBeClickable(org.openqa.selenium.By.xpath(locator)));
		driver.findElement(org.openqa.selenium.By.xpath(locator)).click();
		return true;
	}
	
	
	/* This method is click the element and wait */
	public boolean clickOn1(String locator,String locatorType, int timeout) {
		if(locatorType=="xpath")
		{
			new WebDriverWait(driver, timeout).ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.elementToBeClickable(org.openqa.selenium.By.xpath(locator)));
			driver.findElement(org.openqa.selenium.By.xpath(locator)).click();
			return true;
		}
		else if(locatorType=="css")
		{
			new WebDriverWait(driver, timeout).ignoring(StaleElementReferenceException.class)
			.until(ExpectedConditions.elementToBeClickable(org.openqa.selenium.By.cssSelector(locator)));
	driver.findElement(org.openqa.selenium.By.cssSelector(locator)).click();
	return true;
		}
		else
		{
			logger.info("Locator not good");
			return false;
		}
	}
	
/* This method is click the element */
	public void click(String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		element.click();
	}

	/* This method is get text the element */
	public String getText(String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		String text = element.getText();
		return text;
	}

	/* This method is to ensure element is enable */
	public boolean isElementEnable(String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		boolean butnStatus = element.isEnabled();
		return butnStatus;

	}
		/* 
	 * This method is to ensure element is display 
	 */
	public boolean isDisplayed(WebElement webElement) {
		if (webElement.isDisplayed() == true) {
			return true;
		} else {
			Assert.fail("Web Element is Displayed? " + webElement.isDisplayed());
			return false;
		}
	}
	
	/* 
	 * This method is get text the element 
	 */
	public String webElement_getText(WebElement webElement) {
		return webElement.getText().toString();
	}
	
	/* This method is to ensure file is downloaded */
	public File isXlFileDownloaded() {
		XLUtils xl = new XLUtils();
		File file = XLUtils.isFileDownloaded(HybridConstants.FILEPATH, HybridConstants.FILEEXT,
				HybridConstants.FILENAME);
		System.out.println(file);
		return file;
	}

	/* This method is delete existing excel file */
	public void deleteExcelFileIfExists(String ContentComponent, String ProductINN) {
		String path1 = XLUtils.downloadPath();
		String filename1 = XLUtils.excelFileName1(ContentComponent, ProductINN);
		String filePath1 = path1 + filename1;
		XLUtils.deleteIfFileExist(filePath1);
	}

	/* This method is delete existing word file */
	public void deleteWordFileIfExists(String abbreviation, String TechnologyINN_RocheProduct, String Indication,
			String LineOfTherapy, String Histology) {
		String path1 = XLUtils.downloadPath();
		String filename1 = XLUtils.wordFileName(abbreviation, TechnologyINN_RocheProduct, Indication, LineOfTherapy,
				Histology);
		String filePath1 = path1 + filename1;
		XLUtils.deleteIfFileExist(filePath1);
	}

	/* This method is delete existing zip file */
	public void deleteZipFileIfExists() {
		String path1 = XLUtils.downloadPath();
		String filename1 = XLUtils.zipFileName();
		String filePath1 = path1 + filename1;
		XLUtils.deleteIfFileExist(filePath1);
	}

	/* This method is delete existing excel file */
	public void deleteExcelFileIfExists_comparators(String ContentComponent, String ProductINN) {
		String path1 = XLUtils.downloadPath();
		String filename1 = XLUtils.excelFileName_comparators(ContentComponent, ProductINN);
		String filePath1 = path1 + filename1;
		XLUtils.deleteIfFileExist(filePath1);
	}

	/* This method is delete existing word file */
	public void deleteWordFileIfExists_comparators(String ContentComponent, String ProductINN) {
		String path1 = XLUtils.downloadPath();
		String filename1 = XLUtils.wordFileName_comparators(ContentComponent, ProductINN);
		String filePath1 = path1 + filename1;
		XLUtils.deleteIfFileExist(filePath1);
	}

	/* This method is to get filename */
	public String getFileName() {
		String filename = HybridConstants.DIRPATH + HybridConstants.BACK_SLASH + HybridConstants.DOWNLOAD;
		return filename;
	}

	/* This method is to hover the element */
	public void hoverAction(WebElement element) {

		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();

	}

	/* This method is to hover and click the element */
	public void hoverAndClick(WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).click().build().perform();
	}

	/* This method is to hover and click the element */
	public String hoverAndClickSingle(WebElement hoverelement, WebElement elementclick) {

		Actions action = new Actions(driver);
		action.moveToElement(hoverelement).click(elementclick).build().perform();
		return elementclick.getText();

	}

	/* This method is to refresh the page */
	public void refreshDriver() {
		driver.navigate().refresh();
	}

	/* This method is to return screenshot to step */
	public MediaEntityModelProvider screenshotStep(String step) throws IOException {
		MediaEntityModelProvider mediaModel = MediaEntityBuilder.createScreenCaptureFromPath(step).build();
		return mediaModel;
	}

	/* This method is to return wait time */
	public void waitTime(int wait) {
		switch (wait) {
		case 1:
			try {
				Thread.sleep(3000);
			} catch (Exception e) {
				System.out.println(e);
			}
			break;
		case 2:
			try {
				Thread.sleep(5000);
			} catch (Exception e) {
				System.out.println(e);
			}
		case 3:
			try {
				Thread.sleep(10000);
			} catch (Exception e) {
				System.out.println(e);
			}
		case 4:
			try {
				Thread.sleep(120000);
			} catch (Exception e) {
				System.out.println(e);
			}
		case 5:
			try {
				Thread.sleep(1500);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	/* This method is to read data from word file */
	public String readWordParagraph(String filePath, int paragraphNo) throws IOException {
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		XWPFDocument doc = new XWPFDocument(fis);
		XWPFTable table = doc.getTableArray(0);
		XWPFTableRow row = table.getRow(0);

		XWPFParagraph paragraph = doc.getParagraphArray(paragraphNo);

		String paragraphText = paragraph.getParagraphText().toString();

		doc.close();

		return paragraphText;
	}

	/* This method is to read data from word file */
	public String readWordTableCell(String filePath, int column) throws IOException {
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		XWPFDocument doc = new XWPFDocument(fis);
		XWPFTable table = doc.getTableArray(0);
		XWPFTableRow row = table.getRow(0);

		String header = row.getCell(column).getText().toString();

		doc.close();
		return header;
	}

	/* This method is to screenshot at step level */
	public void ScreenshotStepPass(String step, String step1) {
		test.pass(step, ScreenshotType);
	}
	
	/* This method is to return screenshot type */
	public MediaEntityModelProvider returnScreenshotType(String step) throws IOException {
		ScreenshotType = screenshotStep(step);
		return ScreenshotType;
	}
	
	/* This method is to send PASS to report */
	public void testPassToReport(String message) {
		test.log(Status.PASS, message);
	}
	
	/* This method is to send screenshot */
	public void testPass(String msg, String ss) throws IOException {
		test.pass(msg, screenshotStep(ss));
	}
	
	/* This method is to delete report file */
	public void deleteReportFile() {
		String filePath = HybridConstants.USERDIR + HybridConstants.ReportPath;
		
		File file = new File(filePath);
		File fileList[] = file.listFiles();

		for (int i = 0; i < fileList.length; i++) {
		    File allFile = fileList[i];
		    if (allFile.getName().endsWith(".html")) {
		    	allFile.delete();
		    }
		}
	}
	
	/* This method is to delete step screenshot file */
	public void deleteStepScreenshotFile() {
		String filePath = HybridConstants.USERDIR + HybridConstants.StepScreenshotPath;
		
		File file = new File(filePath);
		File fileList[] = file.listFiles();

		for (int i = 0; i < fileList.length; i++) {
		    File allFile = fileList[i];
		    if (allFile.getName().endsWith(".png")) {
		    	allFile.delete();
		    }
		}
	}

		
}

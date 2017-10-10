package utility;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Utils {
public static WebDriver driver;	
	
/**
 * This method implements how to switch windows	
 * @param cwindow
 */
public static void switchWindows(String cwindow){
		
		Set<String> Ahandles = driver.getWindowHandles();
		Iterator<String> Ait = Ahandles.iterator();
		while(Ait.hasNext()){
			String crWindowString = Ait.next();
			System.out.println("next = " + crWindowString);
			if(cwindow==crWindowString){
				continue;
			}
			driver.switchTo().window(crWindowString); 
		}
	}
/**
 * This method implemenets how to get the specified test data based on the column name(key).
 * @param key
 * @return
 */
public static String getTestData(String key){
	ExcelData.setPath(Constants.path+Constants.filename, Constants.sheetname);
	int rowNum = ExcelData.getRowContains(key, Constants.keycolumn);
	String cellValue = ExcelData.getCellData(rowNum, Constants.column);
	return cellValue;
	
}

public static String getCSVTestData(String key){
	String vlaue = null;
	for(String[] row:ExcelData.getTestDataFromCSVFile()){
		if(row[0].equalsIgnoreCase(key)){
			vlaue = row[1];

		}
	}
	return vlaue;
}
/**
 * This method implements how to get a By type locator.
 * @param key
 * @return
 */
public static By getLocator(String key){
	System.out.println("key="+key);
	By locat = null;
	String locatorType = null;
	String locator = null;
	for(String[] row:ExcelData.getLocatorsFromCSVFile()){
		if(row[0].equalsIgnoreCase(key)){
			locatorType = row[1];
			locator=row[2];
			break;
		}
	}
	System.out.println(locatorType +" ~~ "+ locator);
	switch(locatorType){
	case "id":
		locat =By.id(locator);
		break;
	case "name":
		locat = By.name(locator);
		break;
	case "xpath":
	    locat = By.xpath(locator);
	    break;
	case "linkText":
		locat = By.linkText(locator);
		break;
	default:
		Log.warn("Can not find the locator type. locator type is: "+locatorType);
		break;
	}
	
	return locat;
}
/**
 * This method implements how to get an element based on the key value.
 * @param key
 * @return
 */
public static WebElement getElement(String key)throws NoSuchElementException{
	WebElement element ;
	try {
		By locator =getLocator(key);
		element = driver.findElement(locator);
		if(!element.isDisplayed()){
			Log.error("Can not find the element: " +element);
		}
		return element;
	} catch (NoSuchElementException e) {	
		Log.error("Package:utility|| class:Utils|| Method:getElement || Unable to find element.");	
		throw (e);
	}
}

public static WebDriver openBrowser(String browser)throws Exception{
	try {
		switch(browser){
		case "firefox":
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("security.mixed_content.block_active_content", false);
			profile.setPreference("security.mixed_content.block_display_content", true);
			driver = new FirefoxDriver(profile);
			
			break;
		case "ie":
			DesiredCapabilities  ieCapabilities = DesiredCapabilities.internetExplorer();
			ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			ieCapabilities.setCapability("ignoreProtectedModeSettings", true);
			
			System.setProperty("webdriver.ie.driver", Constants.path+Constants.ieDriverServer);
			driver = new InternetExplorerDriver(ieCapabilities);
			break;
		default:
			Log.warn("Can not find the browser type. and the browser type is: "+browser);
			break;
		}
		Log.info("Browser type is "+browser);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		return driver;
		} catch (Exception e) {	
			Log.error("Package:utility|| class:Utils|| Method:openBrowser || Unable to Open Browser.");	
			throw (e);
		}
}

public static void waitforElementVisible(String logical_name) throws Exception {	
	(new WebDriverWait(driver, 80)).until(ExpectedConditions.visibilityOf(getElement(logical_name))); 
}


public static void waitforJSLoad()throws Exception {	
	(new WebDriverWait(driver, 80)).until(new ExpectedCondition<Boolean>() {
		  @Override 
    	  public Boolean apply(WebDriver dr) {
			  try {				
				  	Boolean value = ((JavascriptExecutor) dr).executeScript("return document.readyState").equals("complete");
				  	 return value;
				 } catch (Exception e) {
					 return Boolean.FALSE;
					}
    	   }
    }); 
}

public static void waitForJSResult()throws Exception {
	@SuppressWarnings("unused")
	JavascriptExecutor jse = (JavascriptExecutor) driver;
	if((Boolean) ((JavascriptExecutor) driver)
            .executeScript("return window.jQuery != undefined && jQuery.active === 0")){
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(new ExpectedCondition<Boolean>(){
			public Boolean apply(WebDriver d){
				return (boolean)((JavascriptExecutor) d).executeScript("return jQuery.active == 0");
			}
		});
	}
}
	
//public static void elementClick(String locator) throws Exception{
//	WebElement element ;
//	element = getElement(locator);
//	element.click();
//	Log.info(element+" is clicked.");
//	waitforJSLoad();	
//}

public static void elementClick(String logical_name) throws Exception{
	try {
		WebElement element ;
		element = getElement(logical_name);
		if(element.isDisplayed()){
			element.click();
			Log.info(element+" is clicked.");
			waitforJSLoad();
		}else{
			Log.info("Could not found ["+logical_name+"] element.");
		}
	} catch (Exception e) {
		Log.error("Method [ElementClick] "+e.getMessage());
		throw(e);
	}
}

/**
 * This method implements that input test data based on two parameters.
 * @param value
 * @param locator
 */
public static void inputValue(String value,String locator) throws Exception{
	String inputData ="";
	try{
		inputData = Utils.getTestData(value) ;
		if(inputData!=null){
				getElement(locator).clear();
				getElement(locator).sendKeys(inputData);
				Log.info("test data: "+inputData + " is input.");
		}
	}catch(Exception e){
		Log.error("[inputValue]: fail to input value @"+locator);
	}
}
/**
 * This method implements that input test data for a field and 
 * determine whether the test data need to be got from testdata 
 * file through the flag.
 * @param flag
 * @param value
 * @param locator
 */
public static void inputValue(boolean flag,String value,String locator) throws Exception{
	String inputData ="";
	try{
		if(flag){
			inputData = value;
		}else{
			inputData = Utils.getTestData(value) ;
		}
		if(inputData!=null){
			getElement(locator).clear();
			getElement(locator).sendKeys(inputData);
			Log.info("test data: "+inputData + " is input.");
		 }
	}catch(Exception e){
		Log.error("[inputValue]: fail to input value @"+locator);
		throw (e);
		}
}


public static void switchFrame(String id){
	driver.switchTo().frame(id);
	
}



public static void selectDropDown(String key_value,String key_locator,String flag){
	WebElement element = getElement(key_locator);
	Select select = new Select(element);
	String dataValue =getTestData(key_value);
	if(flag.equalsIgnoreCase("byvalue")){		
		select.selectByValue(dataValue);
	}else if(flag.equalsIgnoreCase("byindex")){
		select.selectByIndex(Integer.parseInt(dataValue));
	}else{
		select.selectByVisibleText(dataValue);
	}
	Log.info(dataValue +" is selected.");
}



public static void assertElementPresent(String locator){
    try {
    	getElement(locator);
      Assert.assertTrue(true);
    } catch (NoSuchElementException e) {
    	Log.error(e.getMessage());
    	Assert.fail(e.getMessage());
    }
}

public static void assertAlertText(boolean acceptNextAlert,String value) {
	String actualValue = "";
	String expectedValue = "";
	try{
      Alert alert = driver.switchTo().alert();
      actualValue = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
      }
  		expectedValue = getTestData(value).trim().toLowerCase();
  		Assert.assertEquals(actualValue,expectedValue,"Assert is Fail with Actual data is ["+actualValue+"] And Expected Result is ["+expectedValue+"].");
  	}catch(AssertionError e){
  		Log.error(e.getMessage());
  		Assert.fail();
  	} finally {
      acceptNextAlert = true;
    }
}

public static void assertAlertPresent(String locator){
    try {
        driver.switchTo().alert();
        Assert.assertTrue(true);
    } catch (NoAlertPresentException e) {
    	Log.error(e.getMessage());
    	Assert.fail(e.getMessage());
    }
}


public static void assertText(String value,String locator){
	String actualValue = "";
	String expectedValue = "";
	actualValue=getElement(locator).getText().trim().toLowerCase();
	expectedValue = getTestData(value).trim().toLowerCase();
	Assert.assertEquals(actualValue,expectedValue,"Assert is Fail with Actual data is ["+actualValue+"] And Expected Result is ["+expectedValue+"].");
}

public static void assertText(String value,String locator,String sTestCaseName){
	String actualValue = "";
	String expectedValue = "";
	try{		
	actualValue=getElement(locator).getText().trim().toLowerCase();
	expectedValue = getTestData(value).trim().toLowerCase();
	Assert.assertEquals(actualValue,expectedValue,"Assert is Fail with Actual data is ["+actualValue+"] And Expected Result is ["+expectedValue+"].");
	}catch(AssertionError e){
		Log.error(e.getMessage());
		takeScreenshot(sTestCaseName);
		Assert.assertFalse(true);
	}
}

public static void assertValue(String value,String locator,String sTestCaseName){
	String actualValue = "";
	String expectedValue = "";
	try{		
	actualValue=getElement(locator).getAttribute("value").trim().toLowerCase();
	expectedValue = getTestData(value).trim().toLowerCase();
	Assert.assertEquals(actualValue,expectedValue,"Assert is Fail with Actual data is ["+actualValue+"] And Expected Result is ["+expectedValue+"].");
	}catch(AssertionError e){
		Log.error(e.getMessage());
		takeScreenshot(sTestCaseName);
		Assert.assertFalse(true);
	}
}


public static void assertSelected(String locator,String sTestCaseName){
	boolean actualValue = false;
	WebElement element = getElement(locator);
	try{
	actualValue =getElement(locator).isSelected();
	Assert.assertTrue(actualValue, "Assert is Fail with "+ element+" is not checked.");
	}catch(AssertionError e){
		Log.error(e.getMessage());
		takeScreenshot(sTestCaseName);
		Assert.assertFalse(true);
	}
}

public static void assertOption(String value,String locator,String sTestCaseName){
	String actualValue = "";
	String expectedValue = "";
	try{		
		WebElement opt = new Select(getElement(locator)).getFirstSelectedOption();
		actualValue = opt.getText().trim().toLowerCase();
		expectedValue = getTestData(value).trim().toLowerCase();
		Assert.assertEquals(actualValue,expectedValue,"Assert is Fail with Actual data is ["+actualValue+"] And Expected Result is ["+expectedValue+"].");
	}catch(AssertionError e){
		Log.error(e.getMessage());
		takeScreenshot(sTestCaseName);
		Assert.assertFalse(true);
	}
}

public static void assertElementCheckPoint(String elementType, String logical_name,
		String actual_locator) throws Exception {
String actual_data = "[ERROR_VALUE]";
try {
	switch(elementType.toLowerCase()){
		case "select":
			// check whether expected value is selected
			WebElement opt = new Select(getElement(actual_locator)).getFirstSelectedOption();
			actual_data = opt.getText().trim().toLowerCase();
			break;
		case "radiobutton":
		// check whether expected value is selected
			if(getElement(actual_locator).isSelected()){
				actual_data = getElement(actual_locator).getText().trim().toLowerCase();
				}
			break;
		case "textbox":
			if (getElement(actual_locator).getText().trim().isEmpty()) {
				actual_data = getElement(actual_locator).getAttribute("value").trim().toLowerCase();
			}else{
				actual_data = getElement(actual_locator).getText().trim().toLowerCase();
				}
			break;
		default:
			Log.info("[assertElementCheckPoint]: Could not recognize element type!");
		}
		String excel_logical_value = getTestData(logical_name).trim().toLowerCase();
		Assert.assertEquals(actual_data.equals(excel_logical_value),
				true, " Assert is Fail with Actual selected data is ["
						+ actual_data + "] And Expected Result is [" + excel_logical_value + "].");
		excel_logical_value = null;
	} catch (Exception e) {
		Log.warn("[assertElementCheckPoint]: Package utility || Class Utills || Method assertElementCheckPoint() "
				+ e.getMessage());
		throw (e);
	}
}



public static void takeScreenshot(String sTestCaseName) {
	DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh_mm_ss");
	Date date = new Date();	
	File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	try {
		FileUtils.copyFile(file, new File(Constants.snapshotPath
						+ sTestCaseName +"/"+sTestCaseName+ " # " + dateformat.format(date)
						+ ".png"));
	} catch (Exception e) {
		Log.error("Package Utility|| class Utills|| Method takeScreenshot || issue in Taking Screenshot");
	}
}

public static void takeScreenshotCheckPoint(String testCaseName, boolean flag, String checkPointNum) throws Exception {
	Log.info("[takeScreenshotCheckPoint]: " + checkPointNum);
	File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	String picName = "";
	try {
		if (flag) {
			picName = Constants.snapshotPath + testCaseName + "/" + testCaseName + "_" + checkPointNum + "_pass.png";
			FileUtils.copyFile(file, new File(picName));
		} else {
			picName = Constants.snapshotPath + testCaseName + "/" + testCaseName + "_" + checkPointNum + "_fail.png";
			FileUtils.copyFile(file, new File(picName));
		}
	} catch (Exception e) {
		Log.error("[takeScreenshotCheckPoint]: Package Utility|| class Utills|| Method takeScreenshot || issue in Taking Screenshot");
		throw e;
	}
}

//
//public static void takeScreenshotCheckPoint(String testCaseName, boolean flag, String checkPointNum) throws Exception {
//	Log.info("[takeScreenshotCheckPoint]: " + checkPointNum);
//	File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//	String picName = "";
//	try {
//		if (flag) {
//			picName = Constants.snapshotPath + testCaseName + "/" + testCaseName + "_" + checkPointNum + "_pass.png";
//			FileUtils.copyFile(file, new File(picName));
//		} else {
//			picName = Constants.snapshotPath + testCaseName + "/" + testCaseName + "_" + checkPointNum + "_fail.png";
//			FileUtils.copyFile(file, new File(picName));
//		}
//
//	} catch (Exception e) {
//		Log.error("[takeScreenshotCheckPoint]: Package Utility|| class Utills|| Method takeScreenshot || issue in Taking Screenshot");
//		throw e;
//	}
//}
}

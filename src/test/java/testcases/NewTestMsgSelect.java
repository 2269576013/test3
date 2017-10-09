package testcases;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.*;

import static org.testng.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import utility.ExcelData;
import utility.Log;
import utility.Utils;

public class NewTestMsgSelect {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
	Log.info("Try to start Firefox");
	driver = Utils.openBrowser("firefox");   
    Log.info("Firefox started");
    baseUrl = "http://change-this-to-the-site-you-are-testing/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testMsg() throws Exception {
    driver.get("file:///E:/Selenium/Selenium1/example/message.html");
    Log.info("Page opened");
//    driver.findElement(Utils.getLocator("name_editbox_locator")).clear();
//    driver.findElement(Utils.getLocator("name_editbox_locator")).sendKeys(Utils.getTestData("name"));
    Utils.inputValue(false,"name", "name_editbox_locator");
//    driver.findElement(Utils.getLocator("email_editbox_locator")).clear();
//    driver.findElement(Utils.getLocator("email_editbox_locator")).sendKeys(Utils.getTestData("email"));
    Utils.inputValue(false,"email", "email_editbox_locator");
//    driver.findElement(Utils.getLocator("msg_editbox_locator")).clear();
//    driver.findElement(Utils.getLocator("msg_editbox_locator")).sendKeys(Utils.getTestData("msg"));
    Utils.inputValue("msg", "msg_editbox_locator");
    Log.info("Input test data");
    Utils.elementClick("done_button_locator");
    Log.info("click done button");
    assertEquals(driver.getTitle(), "留言簿");
    Log.info("Assert page title");
  }
  
  @Test
  public void testSelect() throws Exception {
    driver.get("file:///E:/Selenium/Selenium1/example/select.html");
    Log.info("Page opened");
    Utils.selectDropDown("selContent","settings_dropdown_locator","byText");
    Log.info("selectDropDown done");
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() throws Exception {
    driver.quit();
    Log.info("Firefox quit");
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}

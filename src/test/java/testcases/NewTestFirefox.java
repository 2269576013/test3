package testcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.testng.Assert.*;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utility.Log;

public class NewTestFirefox {
	WebDriver driver;
  @Test
  public void f() {
	  System.out.println("fffffffffffffffffffffff");
	  Log.info("11111111111111111111111111");
	  assertTrue(true);
  }
  @BeforeMethod
  public void beforeMethod() {
	  driver=new FirefoxDriver();
  }

  @AfterMethod
  public void afterMethod() {
	  driver.quit();
  }

}

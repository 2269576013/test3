package testcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import static org.testng.Assert.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class NewTestFirefox {
	WebDriver driver;
  @Test
  public void f() {
	  System.out.println("fffffffffffffffffffffff");
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

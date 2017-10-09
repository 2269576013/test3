package testcases;


import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import utility.Utils;

public class Test2{
	WebDriver driver;
	@Test
	public void f() throws Exception {
		//打开ECShop后台首页
		driver.get("http://localhost/ws/ecshop/upload/admin/index.php");
     //输入用户名admin
		driver.findElement(By.name("username")).sendKeys("admin");
		//输入密码admin123
		driver.findElement(By.name("password")).sendKeys("admin123");
		//点击“进入管理中心”
		driver.findElement(By.className("button")).click();
		//等待5秒
		Thread.sleep(5000);
		//截图
     Utils.takeScreenshotCheckPoint("Test2",true,"1");

	}
	@BeforeTest
	public void beforeTest() throws Exception {
		//启动Firefox
		driver = new FirefoxDriver();
	}

	@AfterTest
	public void afterTest() throws Exception {
		//关闭浏览器
		driver.quit();
	}
}
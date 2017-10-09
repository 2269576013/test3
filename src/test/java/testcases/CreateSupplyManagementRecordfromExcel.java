package testcases;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import utility.ExcelData;
import utility.Log;
import utility.Utils;

public class CreateSupplyManagementRecordfromExcel {
	static WebDriver driver = new FirefoxDriver();
	@Test
	public static void CreateRecord() throws Exception {
		Log.info("-----------------------------Start----------------------------------");
		driver.get("http://172.166.100.103:85/");
		
		Log.info("78OA is open");
		//登录78OA
		driver.findElement(By.xpath(".//*[@id='account']/div/input")).sendKeys(ExcelData.getMapData("valid_username"));
		Log.info("username is input");
		driver.findElement(By.xpath(".//*[@id='password']/div/input")).sendKeys(Utils.getTestData("valid_password"));
		Log.info("password is input");
		driver.findElement(By.id("submit")).click();
		Log.info("登录 button is clicked ");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		Thread.sleep(2000);
		// click 行政办公标签
		driver.findElement(By.xpath(".//*[@id='r1']/ul/li[5]/table/tbody/tr/td[2]")).click();
		Log.info("行政办公标签 is clicked");
		// click 办公用品菜单
		driver.findElement(By.xpath(".//*[@id='m09']/ul/li[1]/div[1]")).click();
		Log.info("办公用品菜单 is clicked");
		// click 办公用品管理选项
		driver.findElement(By.xpath(".//*[@id='m09']/ul/li[1]/div[2]/ul/li[4]/span")).click();
		Log.info("办公用品选项 is clicked");
		// switch to frame with id context
		driver.switchTo().frame("context");
		Log.info("切换到Frame context");
		// click 新增button
		driver.findElement(By.xpath("html/body/div[1]/div[1]/div/div[2]/span[1]")).click();
		Log.info("新增button is clicked");
		// input 办公用品名称
		driver.findElement(By.xpath(".//*[@id='_2_']/div/input")).sendKeys("铅笔");
		Log.info("铅笔 is input for 办公用品名称区域");
		// input 计量单位
		driver.findElement(By.xpath(".//*[@id='_6_']/div/div[2]/div[1]/div[2]/div/div/input")).sendKeys("个");
		Log.info("个 is input for 计量单位");
		// select 办公用品类型
		WebElement element = driver.findElement(By.id("_3_"));
		Select select = new Select(element);
		select.selectByValue("12");
		Log.info("select 桌面用品类");
		// 输入供应商
		driver.findElement(By.xpath(".//*[@id='_6_']/div/div[3]/div[1]/div[2]/div/div/input")).sendKeys("达内");
		Log.info("input 达内 for 供应商区域");
		// 清楚数据
		driver.findElement(By.xpath(".//*[@id='_6_']/div/div[4]/div[1]/div[2]/div/div/input")).clear();
		// input 当前库存
		driver.findElement(By.xpath(".//*[@id='_6_']/div/div[4]/div[1]/div[2]/div/div/input")).sendKeys("5");
		Log.info("input 5 for 当前库存");
		// 清楚数据
		driver.findElement(By.xpath(".//*[@id='_6_']/div/div[4]/div[2]/div[2]/div/div/input")).clear();
		// input 警戒库存
		driver.findElement(By.xpath(".//*[@id='_6_']/div/div[4]/div[2]/div[2]/div/div/input")).sendKeys("2");
		Log.info("input 2 for警戒库存");
		// select管理人员
		driver.findElement(By.xpath(".//*[@id='_4_']/div[1]/span[1]")).click();
		Log.info("添加 button is clicked");
		String currentWindow = driver.getWindowHandle();
		System.out.println("current = " + currentWindow);
//		Set<String> handles = driver.getWindowHandles();
//		Iterator<String> it = handles.iterator();
//		while(it.hasNext()){
//			String cruWindowString = it.next();
//			System.out.println("next = " + cruWindowString);
//			if(currentWindow==cruWindowString){
//				continue;
//			}
//			driver.switchTo().window(cruWindowString); 
//		}
		//swindow(currentWindow);
		Utils.driver=driver;
		Utils.switchWindows(currentWindow);
		driver.switchTo().frame("right");
		driver.findElement(By.xpath(".//*[@id='admin']/td/span")).click();
		Log.info("保存 button is clicked");
		driver.close();
		driver.switchTo().window(currentWindow);
		driver.switchTo().frame("context");
		// select归属部门
		driver.findElement(By.xpath(".//*[@id='_5_']/div[1]/span[1]")).click();
		Log.info("添加 button is clicked");
		Utils.switchWindows(currentWindow);
		//swindow(currentWindow);
		driver.switchTo().frame("head");
		driver.findElement(By.xpath(".//*[@id='D127']/a")).click();
		// 关闭窗口
		driver.close();
		driver.switchTo().window(currentWindow);
		driver.switchTo().frame("context");
		// input 办公用品描述
		driver.findElement(By.xpath(".//*[@id='_6_']/div/div[7]/div[1]/div[2]/div/div/textarea")).sendKeys("新建办公用品类型");
		Log.info("input 新建办公用品类型 for 办公用品描述区域");
		// click 保存 button
		driver.findElement(By.xpath("html/body/div[1]/div[1]/div/div[2]/span[1]")).click();	
		Log.info("保存 button is clicked");
		// switch to default window
		driver.switchTo().defaultContent();
		// logout 
		driver.findElement(By.xpath(".//*[@id='r3']/span[4]"));
		Log.info("退出 button is clicked");
		//关闭浏览器
		driver.quit();
		Log.info("-----------------------------End------------------------------------");
	}
	
	
	public static void swindow(String cwindow){
		
		Set<String> Ahandles = driver.getWindowHandles();
		Iterator<String> Ait = Ahandles.iterator();
		//String preWindowString = currentWindow; 
		while(Ait.hasNext()){
			String crWindowString = Ait.next();
			System.out.println("next = " + crWindowString);
			if(cwindow==crWindowString){
				continue;
			}
			driver.switchTo().window(crWindowString); 
		}
	}
	
	

}

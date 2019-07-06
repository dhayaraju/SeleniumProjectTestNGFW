package tests;

import org.testng.annotations.Test;

import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;

import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import multiScreenShot.MultiScreenShot;

public class NewTest {
	
	WebDriver driver;
	Logger log;
	ChromeOptions options;
	ATUTestRecorder recorder;
	MultiScreenShot screens;
	
  @Test(priority=1,enabled=false)
  public void Sign_Up() throws InterruptedException, IOException{
	  
	  Actions action = new Actions(driver);
	  //action.moveToElement(driver.findElement(By.className("dropdown-toggle deskview"))).build().perform();
	  //action.moveToElement(driver.findElement(By.xpath("//*[@id=\"myNavbar\"]/ul/li[5]/a"))).perform();
	  driver.findElement(By.xpath("//*[@id=\"myNavbar\"]/ul/li[5]/a")).click();
	  action.moveToElement(driver.findElement(By.linkText("Sign In")));
	  action.click();
	  action.perform();
	  
	  action.moveToElement(driver.findElement(By.id("signuppopup")));
	  action.click();
	  action.perform();
	  
	  log.info("SIGN UP CLICKED");
	  Thread.sleep(2000);
	  
	  driver.findElement(By.id("eM")).sendKeys("dhayadiv@gmail.com");
	  driver.findElement(By.id("npass")).sendKeys("Dhaya123");
	  driver.findElement(By.id("cpass")).sendKeys("Dhaya123");
	  driver.findElement(By.id("mO")).sendKeys("1234567890");
	  driver.findElement(By.id("promoCode")).sendKeys("");
	  screens.multiScreenShot(driver);
	  
	  driver.findElement(By.id("register")).click();
	  Thread.sleep(2000);
	  
	  log.info("DETAILS ENTERED");
	  screens.multiScreenShot(driver);
	  
	  driver.navigate().to("https://www.ticketgoose.com");
  }
  
  @Test(priority=2)
  public void Sign_In() throws InterruptedException, IOException
  {
	  Actions action = new Actions(driver);
	  driver.findElement(By.xpath("//*[@id=\"myNavbar\"]/ul/li[5]/a")).click();
	  action.moveToElement(driver.findElement(By.linkText("Sign In")));
	  action.click();
	  action.perform();
	  
	  driver.findElement(By.id("username")).sendKeys("dhayadiv@gmail.com");
	  driver.findElement(By.id("password")).sendKeys("Dhaya123");
	  screens.multiScreenShot(driver);
	  
	  action.moveToElement(driver.findElement(By.id("tg-signin")));
	  action.click();
	  action.perform();
	  
	  log.info("SIGN IN CLICKED");
	  screens.multiScreenShot(driver);
	  Thread.sleep(2000);
  }
  
  private String getCurrentDay (){
      //Create a Calendar Object
      Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

      //Get Current Day as a number
      int todayInt = calendar.get(Calendar.DAY_OF_MONTH);
      System.out.println("Today Int: " + todayInt +"\n");

      //Integer to String Conversion
      String todayStr = Integer.toString(todayInt);
      System.out.println("Today Str: " + todayStr + "\n");

      return todayStr;
  }
  
  @Test(priority=3)
  public void Bus_Ticket_Booking()
  {
	  driver.findElement(By.id("fs")).sendKeys("coimbatore");
	  driver.findElement(By.id("ts")).sendKeys("chennai");
	  
	//Get Today's number
      String today = getCurrentDay();
      System.out.println("Today's number: " + today + "\n");
      
	  driver.findElement(By.id("tdate")).click();	  
	  
	  WebElement table = driver.findElement(By.className("ui-datepicker-calendar"));
	  List<WebElement> columns = table.findElements(By.tagName("td"));
	  System.out.println(columns);
	  
	  for(WebElement cell: columns) 
		{
			String date = cell.getText();
			if(date.equalsIgnoreCase("27"))
			{
				System.out.println(date);
				cell.click();
				break;
			}

		}
	  driver.findElement(By.id("hsearch")).click();
	  
	  driver.findElement(By.xpath("//*[@id=\"sort-container-1\"]/div[4]")).click();
	  
	  
	  WebElement resulttable = driver.findElement(By.id("results-table"));
	  List<WebElement> namerows = resulttable.findElements(By.tagName("tbody"));
	  System.out.println(namerows.size());
	  
	  for(WebElement cell: namerows) 
		{
		  List<WebElement> namecolumns = cell.findElements(By.tagName("td"));
		  
		  for(WebElement cell1: namecolumns)
		  {
			String busname = cell1.getText();
			if(busname.equals("Jahan Travels"))
			{
				System.out.println(busname);
				//book-button
				//cell.click();
				break;
			}

		}
		}

		
  }
  
  @BeforeTest
  public void beforeTest() throws InterruptedException, IOException, ATUTestRecorderException {
	  	//Setting Driver location
	  	options = new ChromeOptions();
	  	options.addArguments("--disable-notifications");
		System.setProperty("webdriver.chrome.driver", "D:\\eclipse_workspace\\Library\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver(options);
		log = Logger.getLogger(NewTest.class.getName());
		PropertyConfigurator.configure("Log4jf.properties");
		screens = new MultiScreenShot("D:\\Dhaya\\eclipse_workspace\\Screenshots\\","TestScreen");
		recorder = new ATUTestRecorder("D:\\Dhaya\\eclipse_workspace\\ScriptVideos\\","TestVideo", false);
		recorder.start();
		Thread.sleep(2000);
		driver.get("https://www.ticketgoose.com");
		log.info("URL LOADED");
		driver.manage().window().maximize();
		screens.multiScreenShot(driver);
		Thread.sleep(2000);		
		
  }

  @AfterTest
  public void afterTest() throws ATUTestRecorderException {
	  //driver.quit();
	  recorder.stop();
  }

}

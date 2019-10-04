package com.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;


public class TestRunner implements ITestListener {
	
	public static Logger log = Logger.getLogger(TestRunner.class);
	public static Properties config = null;
	public static WebDriver driver = null;	
	protected SoftAssert softAssert = new SoftAssert();
	
	
	public static void initialize(String browser)
	{
		
		try{
			config = new Properties();
			FileInputStream fin = new FileInputStream(System.getProperty("user.dir")+"/configurations/config.properties");
			config.load(fin);
			String testUrl = config.getProperty("testAppUrl");
			
		if(browser.equalsIgnoreCase("chrome"))	
		{
			String osName = System.getProperty("os.name");
			log.info("Tests running on OS: " + osName);
			String currentDir = System.getProperty("user.dir");
			String pathToFile = currentDir + File.separator + "Browsers" + File.separator + ((osName.equalsIgnoreCase("Mac OS X")) ? "chromedriver" : "chromedriver.exe");
			System.setProperty("webdriver.chrome.driver",pathToFile); 
			DesiredCapabilities capability = DesiredCapabilities.chrome();
			capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			driver = new ChromeDriver(capability);			
//			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		}
		else if(browser.equalsIgnoreCase("firefox"))
		{
			FirefoxProfile profile=new FirefoxProfile();
			profile.setAcceptUntrustedCertificates(true);
			profile.setAssumeUntrustedCertificateIssuer(true);
			//profile.setAssumeUntrustedCertificateIssuer(false);
			driver = new FirefoxDriver(profile);
//			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		}
			log.info("Getting URl: "+testUrl+" in browser");
			driver.manage().window().setPosition(new Point(0, 0));
			driver.manage().window().setSize(new Dimension(1280, 800));
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			driver.get(testUrl);
					
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void ationSendKeys(WebElement ele, String input)
	{
		Actions actions = new Actions(driver);
		actions.moveToElement(ele);
		actions.click();
		actions.sendKeys(input).build().perform();
	}
	
	public static void wait(int sec){
        try {
               Thread.sleep(sec * 1000);
        } catch (Exception e) {
               e.printStackTrace();
        }
        
  }

	public void takeScreenShot(ITestResult result) {
		/*//get the driver
		String base64str = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
		log.info("Failed test screenshot! data:image/jpeg;base64," + base64str);*/ 
	    File imageFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String failureImageFilename = "target/screenshots/" + result.getMethod().getMethodName()
                + new SimpleDateFormat("MM-dd-yyy_HH-ss").format(new GregorianCalendar()
                        .getTime()) + ".png";
        File failureImageFile = new File(failureImageFilename);
        try {
            FileUtils.moveFile(imageFile, failureImageFile);
        }
        catch (IOException e) {
            log.error("Failed to take screenshot");
        }
        log.info("Failed test screenshot at " +  failureImageFilename);
    }

	@BeforeSuite
	public void beforeSuite()
	{
		//log.info("Inside before suite");
	}
	
	@BeforeMethod
	public void beforeMethod()
	{
		//log.info("Inside before test");
		//initialize("ie");
	}
	
	@AfterMethod
	public void afterMethod()
	{
		//log.info("Inside after test");
		//driver.close();
	}
	
	@AfterSuite
	public void afterSuite()
	{
		//log.info("Inside after suite");
	}
	
	@AfterTest
	public void afterTest()
	{
		//log.info("Exiting test case execution...");
	//	driver.close();
	}

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		log.error("***** Error "+result.getName()+" test has failed *****");
//		String methodName=result.getName().toString().trim();
		takeScreenShot(result);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}
	
}

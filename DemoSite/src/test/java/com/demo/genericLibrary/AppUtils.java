package com.demo.genericLibrary;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;

public class AppUtils {

	public static WebElement waitForElement(WebDriver driver, int seconds, By by) {
		WebElement element = null;
		try {
			WebDriverWait wdWait = new WebDriverWait(driver, seconds);
			element = wdWait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return element;
	}
	
	public static List<WebElement> waitForElements(WebDriver driver, int seconds, By by) {
		List<WebElement> element = null;
		try {
			WebDriverWait wdWait = new WebDriverWait(driver, seconds);
			element = wdWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return element;
	}

    public static WebElement waitForElementToBeClickable(WebDriver driver, int seconds, By by) {
    	WebElement element = null;
        try {
            WebDriverWait wdWait = new WebDriverWait(driver, seconds);
            element = wdWait.until(ExpectedConditions.elementToBeClickable(by));
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
        return element;
    }

    public static String getTextOnElement(WebDriver driver, String xPath) {
        try {
            return driver.findElement(By.xpath(xPath)).getText();
        }
        catch (NoSuchElementException err) {
            System.out.println("WebDriverUtils.getText failed");
            System.out.println("Error is " + err);
            return "Fail";
        }
    }

    public static boolean isElementPresent(WebDriver driver, By by) {
        try {
        	return AppUtils.waitForElement(driver, 20, by).isDisplayed();
        }
        catch (Exception e) {
            return false;
        }
    }

    public static void selectDropDownValueByVisibleText(WebDriver driver, WebElement element,
            String value) {
        Select select = new Select(element);
        select.selectByVisibleText(value);
    }

    public static void checkAlert(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 3);
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            alert.accept();
            System.out.println("**************** AN ALERT WINDOW WAS CLOSED ******************");
        }
        catch (Exception e) {
            // exception handling
        }
    }

    public static void selectDropDownValue(WebDriver driver, WebElement element, String value) {
        try {
            Thread.sleep(1000);
            if (element.isEnabled()) {

                element.click();

                List<WebElement> list = driver.findElements(By.xpath("//a[@role='menuitem']"));

                for (int j = 0; j < list.size(); j++) {
                    if ((list.get(j).getText()).equalsIgnoreCase(value)) {
                        list.get(j).click();
                        break;
                    }
                }
                Thread.sleep(1000);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void takeScreenShot(WebDriver driver, ITestResult result) throws IOException {
        File imageFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String failureImageFilename = result.getMethod().getMethodName()
                + new SimpleDateFormat("MM-dd-yyy_HH-ss").format(new GregorianCalendar().getTime())
                + ".png";
        File failureImageFile = new File(System.getProperty("user.dir") + File.separator
                + "screenshots", failureImageFilename);
        FileUtils.moveFile(imageFile, failureImageFile);
    }

    public static void clickButton(WebDriver driver, By buttonLocator) {
        try {
            WebElement button = new WebDriverWait(driver, 20).until(ExpectedConditions
                    .elementToBeClickable(buttonLocator));
            button.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void waitForElementToLoad(WebDriver driver, By elementLocator) {
        try {
            new WebDriverWait(driver, 20).until(ExpectedConditions
                    .visibilityOfAllElementsLocatedBy(elementLocator));
        } catch (Exception e) {
            Assert.fail("Unable to load element " + elementLocator + " with exception " + e.getMessage());
        }
    }
    
    public static void enterText(WebDriver driver, By textboxLocator, String text) {
        try {
            WebElement textbox = waitForElement(driver, 3, textboxLocator);
            textbox.clear();
            textbox.sendKeys(text);
        }
        catch (Exception e) {
            Assert.fail("Could not enter text " + text + " in text box " + textboxLocator + " with exception " + e.getMessage());
        }
    }
    
    public static void validateElementIsDisabled(WebDriver driver, By elementLocator) {
        try {
            WebElement element = driver.findElement(elementLocator);
            Assert.assertFalse(element.isEnabled(), "Element " + elementLocator + " is NOT disabled.");
        }
        catch (NoSuchElementException e) {
            Assert.fail("Unable to find element " + elementLocator + " with exception " + e.getMessage());
        }
    }
    
    

}

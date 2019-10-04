package com.demo.appSpecific;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.demo.TestRunner;
import com.demo.genericLibrary.AppUtils;

public class Authentication extends TestRunner {

    public static Logger log = Logger.getLogger(Authentication.class);

    public static By userNameLocator = By.id("user_login");

    public static By passwordLocator = By.id("user_pass");

    public static By loginButtonLocator = By.id("wp-submit");

    public static By logoutIconLocator = By
            .cssSelector("span.fi-setting.header-settings-icon.dropdown-toggle");

    public static By logoutLinkLocator = By.linkText("Logout");

    public static By userNameDivLocator = By.cssSelector("div.header-username>span");

    public static By loginPageFormlocator = By.id("login");
    
    public static By landingPageUserNameLocator = By.className("display-name");

    public static boolean login(String username, String password) {
        try {
            driver.manage().window().maximize();
            WebElement userNameField = AppUtils.waitForElement(driver, 20, userNameLocator);
            userNameField.clear();
            log.info("Entering username : " + username + " in to Username textbox");
            userNameField.sendKeys(username);
            WebElement passwordField = AppUtils.waitForElement(driver, 20, passwordLocator);
            passwordField.clear();
            log.info("Entering password: " + password + " in to password textbox");
            passwordField.sendKeys(password);
            WebElement loginButton = AppUtils.waitForElementToBeClickable(driver, 20, loginButtonLocator);
            loginButton.click();
            return AppUtils.isElementPresent(driver, landingPageUserNameLocator);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void waitForLoginpagetoLoad() {
        try {
            new WebDriverWait(driver, 30).until(ExpectedConditions
                    .visibilityOfElementLocated(loginPageFormlocator));
        }
        catch (TimeoutException e) {
            log.info("Login page failed to load.   :" + e.getMessage());
        }
    }

    public static void logout() {
        try {
            WebElement logoutIcon = new WebDriverWait(driver, 20).until(ExpectedConditions
                    .visibilityOfElementLocated(logoutIconLocator));
            logoutIcon.click();
            WebElement logoutLink = new WebDriverWait(driver, 20).until(ExpectedConditions
                    .visibilityOfElementLocated(logoutLinkLocator));
            logoutLink.click();
            log.info("Browser was closed after logging out!!");
            waitForLoginpagetoLoad();
        }
        catch (Exception e) {
            log.info("Browser was closed without logging out!!");
            driver.quit();
        }
        driver.quit();

    }

}

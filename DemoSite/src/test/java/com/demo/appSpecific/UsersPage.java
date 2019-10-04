package com.demo.appSpecific;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.demo.TestRunner;
import com.demo.genericLibrary.AppUtils;

public class UsersPage extends TestRunner {
	
	private static By usersMenuLocator = By.cssSelector(".dashicons-admin-users");
	
	private static By addNewLocator = By.linkText("Add New");
	
	private static By userNameLocator = By.id("user_login");
	
	private static By emailLocator = By.id("email");
	
	private static By addNewUserLocator = By.id("createusersub");
	
	private static By userNameColumnLocator = By.cssSelector(".username strong");
	
	private static String selectUserCheckBoxLocator = "//label[.='Select %s']/following-sibling::input";
	
	private static By bulkActionLocator = By.id("bulk-action-selector-bottom");
	
	private static By deleteOptionLocator = By.xpath("(//option[@value='delete'])[2]");
	
	private static By applyButtonLocator = By.id("doaction2");
	
	private static By confirmDeletionLocator = By.id("submit");
	
	public static void hoverOverUsersMenu() {
		Actions action = new Actions(driver);
        action.moveToElement(AppUtils.waitForElement(driver, 5, usersMenuLocator)).build().perform();
	}
	
	public static void addNew() {
		AppUtils.clickButton(driver, addNewLocator);
	}
	
	public static void enterUserName(String text) {
		AppUtils.enterText(driver, userNameLocator, text);
	}
	
	public static void enterEmail(String text) {
		AppUtils.enterText(driver, emailLocator, text);
	}
	
	public static void addNewUser() {
		AppUtils.clickButton(driver, addNewUserLocator);
	}
	
	public static boolean verifyUserNameDisplayed(String userName) {
		boolean isUserNamePresent = false;
		List<WebElement> listOfUserName = AppUtils.waitForElements(driver, 10, userNameColumnLocator);
		for (WebElement webElement : listOfUserName) {
			if (webElement.getText().equalsIgnoreCase(userName)) {
				isUserNamePresent = true;
				break;
			}
		}
		return isUserNamePresent;
	}
	
	public static void selectUser(String userName) {
		AppUtils.waitForElement(driver, 3, By.xpath(String.format(selectUserCheckBoxLocator, userName))).click();
	}
	
	public static void clickBulkActionLocator() {
		AppUtils.clickButton(driver, bulkActionLocator);
	}
	
	public static void selectDeleteOption() {
		AppUtils.clickButton(driver, deleteOptionLocator);
	}
	
	public static void clickApply() {
		AppUtils.clickButton(driver, applyButtonLocator);
	}
	
	public static void clickConfirmDeletion() {
		AppUtils.clickButton(driver, confirmDeletionLocator);
	}

}

package com.demo.tests;

import org.testng.annotations.Test;

import com.demo.TestRunner;
import com.demo.appSpecific.Authentication;
import com.demo.appSpecific.UsersPage;

import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class TestUserPage extends TestRunner {
	

  @BeforeClass
  public void login() throws InterruptedException
  {
      TestRunner.initialize("chrome");
      Assert.assertTrue(Authentication.login(config.getProperty("username"),config.getProperty("password")));
  }
  
  @Test
  public void test01_addUserData() throws Exception {
	  String userName = config.getProperty("Username_required");
	  String email = config.getProperty("Email_required");
	  UsersPage.hoverOverUsersMenu();
	  UsersPage.addNew();
	  UsersPage.enterUserName(userName);
	  UsersPage.enterEmail(email);
	  Thread.sleep(3000);
	  UsersPage.addNewUser();
	  Assert.assertTrue(UsersPage.verifyUserNameDisplayed(userName));
  }
  
  @Test
  public void test02_deleteUserData() {
	  String userName = config.getProperty("Username_required");
	  UsersPage.selectUser(userName);
	  UsersPage.clickBulkActionLocator();
	  UsersPage.selectDeleteOption();
	  UsersPage.clickApply();
	  UsersPage.clickConfirmDeletion();
	  Assert.assertFalse(UsersPage.verifyUserNameDisplayed(userName));
  }

  @AfterClass
  public void logout()
  {
     // Authentication.logout();
  }

}

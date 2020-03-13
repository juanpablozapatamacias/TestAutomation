package ui.automation.selenium.tests;

import org.testng.annotations.Test;

import java.lang.reflect.Method;

import org.testng.Assert;

import ui.automation.selenium.base.BaseTest;

import ui.automation.selenium.utilities.Log;
import ui.automation.selenium.utilities.reports.ExtentTestManager;

public class DemoTest extends BaseTest {
	
	@Test(description="Test Demo to create reports")
	public void demoTest(Method method) {
		Log.info("Test Demo");
		
		//ExtentTestManager.startTest(method.getName(), "Test Demo to create reports");
		//ExtentTestManager.getTest().setDescription("Test Demo to create reports");
		
		Assert.assertTrue(true);
	}

}

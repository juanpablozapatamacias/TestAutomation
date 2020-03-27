package ui.tests;

import java.lang.reflect.Method;

import org.testng.annotations.Test;
import org.testng.Assert;

import ui.automation.selenium.base.BaseTest;
import ui.automation.selenium.utilities.Log;

public class DemoTest extends BaseTest {
	
	@Test(description="Test Demo to create reports")
	public void demoTest(Method method) {
		Log.info("Test Demo");
		Assert.assertTrue(true);
	}

}

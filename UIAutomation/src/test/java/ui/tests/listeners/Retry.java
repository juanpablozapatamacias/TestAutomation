package ui.tests.listeners;

import com.relevantcodes.extentreports.LogStatus;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import ui.automation.selenium.base.BaseTest;

import ui.automation.selenium.utilities.reports.ExtentTestManager;

public class Retry implements IRetryAnalyzer {
	
	private int count = 0;
	private static int maxTry = 1;
	
	WebDriver driver;
	String base64Screenshot;

	@Override
	public boolean retry(ITestResult iTestResult) {
		// TODO Auto-generated method stub
		if(!iTestResult.isSuccess()) {
			if(count < maxTry) {
				count++;
				iTestResult.setStatus(iTestResult.FAILURE);
				extendsReportsFailOperations(iTestResult);
				return true;
			}
		}
		else iTestResult.setStatus(iTestResult.SUCCESS);
		
		return false;
	}

	public void extendsReportsFailOperations(ITestResult iTestResult) {
		// TODO Auto-generated method stub
		Object testClass = iTestResult.getInstance();
		driver = ((BaseTest) testClass).getDriver();
		
		base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
		
		ExtentTestManager.getTest().log(LogStatus.FAIL, "Test Failed",
				ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
		
	}
	
}
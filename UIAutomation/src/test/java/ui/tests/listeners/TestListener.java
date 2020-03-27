package ui.tests.listeners;

import com.relevantcodes.extentreports.LogStatus;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import ui.automation.selenium.base.BasePage;
import ui.automation.selenium.base.BaseTest;

import ui.automation.selenium.utilities.Log;
import ui.automation.selenium.utilities.reports.ExtentManager;
import ui.automation.selenium.utilities.reports.ExtentTestManager;

public class TestListener extends BaseTest implements ITestListener{
	
	String base64Screenshot;

	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}
	
	@Override
	public void onStart(ITestContext iTestContext) {
		Log.info("Start Method: " + iTestContext.getName());
		iTestContext.setAttribute("WebDriver", this.driver);
	}
	
	@Override
	public void onFinish(ITestContext iTestContext) {
		Log.info("Finish Method " + iTestContext.getName());
		// Do tier ops for extent reports reporting
		ExtentTestManager.endTest();
		ExtentManager.getReporter().flush();
	}
	
	@Override
	public void onTestStart(ITestResult iTestResult) {
		Log.info("Start Test Method " + getTestMethodName(iTestResult) + " start");
	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		// TODO Auto-generated method stub
		Log.info("Test Method Success: " + getTestMethodName(iTestResult) + " succeeded");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult iTestResult) {
		// TODO Auto-generated method stub
		Log.info("Test Method Failed: " + getTestMethodName(iTestResult) + " failed");
		
		Object testClass = iTestResult.getInstance();
		driver = ((BaseTest) testClass).getDriver();
		
		base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
		
		ExtentTestManager.getTest().log(LogStatus.FAIL, "Test Failed",
				ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		// TODO Auto-generated method stub
		Log.info("Test Method Skipped: " + getTestMethodName(iTestResult) + " skipped");
		ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		// TODO Auto-generated method stub
		Log.info("Test Failed but it is in defined success ratio "+ getTestMethodName(iTestResult));
	}
	
}
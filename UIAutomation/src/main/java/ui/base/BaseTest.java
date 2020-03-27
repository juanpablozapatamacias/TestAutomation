package ui.base;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.Assert;

import ui.pageobjects.LoginPage;
import ui.utilities.CommonUtilities;
import ui.utilities.Log;

public class BaseTest {
	
	protected WebDriver driver;
	protected String projectPath = System.getProperty("user.dir");
	protected String pathScreenshots = projectPath + "/screenshots/";
	
	protected Properties propi = CommonUtilities.loadProperties(projectPath + "/src/main/resources/sample.properties");
	
	private LoginPage loginPage = new LoginPage(getDriver());
	
	
	@BeforeSuite
	public void setup() {
		Log.startLog("Test Suite is starting");
		
		driver= getDriver();
		
		Log.info("Deleting Cookies");
		driver.manage().deleteAllCookies();
		
		Log.info("Maximizing the browser window");
		driver.manage().window().maximize();
		
		Log.info("Applying the implicit wait during 10 seconds");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		Log.info("Open the web site to test");
		driver.navigate().to(propi.getProperty("webapp.website"));
		
		Log.info("Prepare the credentials to access...");
		Assert.assertTrue(!login(propi.getProperty("webapp.username"),propi.getProperty("webapp.password")));
		
		Log.info("Taking first screenshot");
		CommonUtilities.takeScreenshot(driver, projectPath + "/screenshots/", "OpenURL");
	}
	
	@AfterSuite
	public void teardown() {
		try {
			Log.endLog("Test is ending");
		}
		catch(Exception e) {
			
		}
		finally {
			if(driver !=null) {
				propi = null;
				driver.quit();
				driver = null;
			}
		}
	}

	/*
	@BeforeMethod
	public void beforeMethod(Method method) {
		Test test = method.getAnnotation(Test.class);
		ExtentTestManager.startTest(method.getName(), test.description());
	}
	
	
	@AfterMethod
	public void takeScreenshotOnFailure(ITestResult testResult) {
		if(testResult.getStatus() == ITestResult.FAILURE) {
			Log.fatal("Test Result Status " + testResult.getStatus());
			CommonUtilities.takeScreenshot(getDriver(), pathScreenshots, 
					testResult.getName() + Arrays.toString(testResult.getParameters()));
			
		}
	}
	*/
	
	public WebDriver getDriver() {
		if (driver==null)
			driver = connectDriver();
		return driver;
	}
		
	public WebDriver connectDriver() {
		String propBrowser = propi.getProperty("webapp.browser").toLowerCase();
		
		Properties propSystem = System.getProperties();
		Enumeration e = propSystem.propertyNames();
		
		Map<String,String> propMap = new HashMap<String, String>();
		propMap = CommonUtilities.putSysProps(e, propSystem);
		projectPath = propMap.get("user.dir");
		String os = propMap.get("os.name").trim().toLowerCase();
				 
		if (propBrowser.isEmpty() || propBrowser == null){
			throw new IllegalArgumentException("Missing parameter value for browser");
		}
		else {
			String http_proxy = System.getProperty("http_proxy", "");
			String https_proxy = System.getProperty("https_proxy", "");
			
			if(os.indexOf("win")>=0) {
				
				if(propBrowser.contentEquals("chrome")) {
					
					ChromeOptions op = new ChromeOptions();
					
					/*
					Proxy proxy = new Proxy();
					proxy.setHttpProxy(http_proxy);
					proxy.setSslProxy(https_proxy);
					
					op.setCapability(CapabilityType.PROXY, proxy);
					*/
					System.setProperty("webdriver.chrome.driver", 
							projectPath + "\\drivers\\chromedriver\\chromedriver.exe");
					
					driver = new ChromeDriver(op);	
				}
				else if(propBrowser.contentEquals("ff") || 
						propBrowser.contentEquals("firefox")) {
					FirefoxOptions dc = new FirefoxOptions();
					
					/*
					Proxy proxy = new Proxy();
					proxy.setHttpProxy(http_proxy);
					proxy.setSslProxy(https_proxy);
					
					dc.setCapability(CapabilityType.PROXY, proxy);
					*/
					
					FirefoxProfile profile = new FirefoxProfile();
					profile.setAcceptUntrustedCertificates(false);
					profile.setAssumeUntrustedCertificateIssuer(true);
					profile.setPreference("browser.download.folderList", 2);
					profile.setPreference("browser.helperApps.alwaysAsk.force", false);
					
					dc.setCapability(FirefoxDriver.PROFILE, profile);
					dc.setCapability("marionette", true);
					
					System.setProperty("webdriver.gecko.driver", 
							projectPath + "\\drivers\\firefox\\geckodriver.exe");
					//System.setProperty("webdriver.firefox.marionette", 
					//		projectPath + "\\drivers\\firefox\\geckodriver.exe");
					driver = new FirefoxDriver(dc);
				}
				else if (propBrowser.contentEquals("ie") || 
						propBrowser.contentEquals("internetexplorer")) {
					System.setProperty("webdriver.ie.driver", 
							projectPath + "\\drivers\\ie\\IEDriverServer.exe");
					driver = new InternetExplorerDriver();
				}
				else {
					throw new IllegalArgumentException("Browser name is not correct or is not valid...");
				}	
			}
			else if((os.indexOf("nux")>=0) || (os.indexOf("nix")>=0)) {
				
				if(propBrowser.contentEquals("chrome")) {
					ChromeOptions op = new ChromeOptions();
					
					/*
					Proxy proxy = new Proxy();
					proxy.setHttpProxy(http_proxy);
					proxy.setSslProxy(https_proxy);
					
					op.setCapability(CapabilityType.PROXY, proxy);
					*/
					
					System.setProperty("webdriver.chrome.driver", 
							projectPath + "/drivers/chromedriver/chromedriver");
					
					driver = new ChromeDriver(op);	
				}
				else if(propBrowser.contentEquals("ff") || 
						propBrowser.contentEquals("firefox")) {
					FirefoxOptions dc = new FirefoxOptions();
					
					/*
					Proxy proxy = new Proxy();
					proxy.setHttpProxy(http_proxy);
					proxy.setSslProxy(https_proxy);
					
					dc.setCapability(CapabilityType.PROXY, proxy);
					*/
					
					FirefoxProfile profile = new FirefoxProfile();
					profile.setAcceptUntrustedCertificates(false);
					profile.setAssumeUntrustedCertificateIssuer(true);
					profile.setPreference("app.update.enabled", false);
					profile.setPreference("browser.download.folderLisy", 2);
					profile.setPreference("browser.helperApps.alwaysAsk.force", false);
					
					dc.setCapability(FirefoxDriver.PROFILE, profile);
					dc.setCapability("marionette", true);
					
					System.setProperty("webdriver.gecko.driver", 
							projectPath + "/drivers/firefox/geckodriver");
					
					//System.setProperty("webdriver.firefox.marionette", 
					//		projectPath + "\\drivers\\firefox\\geckodriver.exe");
					driver = new FirefoxDriver(dc);
				}
				else {
					throw new IllegalArgumentException("Browser name is not correct or is not valid...");
				}
			}
			else if((os.indexOf("mac")>=0) || (os.indexOf("darwin")>=0)) {
				
				if(propBrowser.trim().contentEquals("chrome")) {
					ChromeOptions op = new ChromeOptions();
					
					/*
					Proxy proxy = new Proxy();
					proxy.setHttpProxy(http_proxy);
					proxy.setSslProxy(https_proxy);
					
					op.setCapability(CapabilityType.PROXY, proxy);
					*/
					System.setProperty("webdriver.chrome.driver", 
							projectPath + "/drivers/chromedriver/chromedriver-77");
					
					driver = new ChromeDriver(op);	
				}
				else if(propBrowser.contentEquals("ff") || 
						propBrowser.contentEquals("firefox")) {
					FirefoxOptions dc = new FirefoxOptions();
					
					/*
					Proxy proxy = new Proxy();
					proxy.setHttpProxy(http_proxy);
					proxy.setSslProxy(https_proxy);
					
					dc.setCapability(CapabilityType.PROXY, proxy);
					*/
					
					FirefoxProfile profile = new FirefoxProfile();
					profile.setAcceptUntrustedCertificates(false);
					profile.setAssumeUntrustedCertificateIssuer(true);
					profile.setPreference("browser.download.folderList", 2);
					profile.setPreference("browser.helperApps.alwaysAsk.force", false);
					
					dc.setCapability(FirefoxDriver.PROFILE, profile);
					dc.setCapability("marionette", true);
					
					System.setProperty("webdriver.gecko.driver", 
							projectPath + "/drivers/firefox/geckodriver");
					//System.setProperty("webdriver.firefox.marionette", 
					//		projectPath + "/drivers/firefox/geckodriver");
					driver = new FirefoxDriver(dc);
				}
				else {
					throw new IllegalArgumentException("Browser name is not correct or is not valid...");
				}
			}
			else {
				throw new IllegalArgumentException("OS out of scope ...");
			}
		}
		return driver;
	}
	
	public boolean login(String username, String password) {
		Log.info("Authenticate to the web application ...");
		loginPage.fillUsername(username);
		loginPage.fillPassword(password);
		loginPage.clickLoginButton();
		
		return loginPage.isUserAccountNotFoundDisplayed();
	}
}

package ui.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ui.base.BasePage;
import ui.utilities.CommonUtilities;
import ui.utilities.Log;

public class LoginPage extends BasePage{
	
	WebElement ele;
	By byUsernameField = By.name("email");
	By byPasswordField = By.cssSelector("#password");
	By bySubmitButton = By.xpath("//button[contains(text(),'Login')]");
	By byUserAccountNotfound = By.cssSelector(".help-block");
	
	
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	public void fillUsername(String username) {
		Log.info("Fill the username");
		ele = getElementPresenceOfElementLocated(byUsernameField, 3);
		ele.sendKeys(username);
		CommonUtilities.sleepByNSeconds(1);
	}
	
	public void fillPassword(String password) {
		Log.info("Fill the password");
		ele = getElementPresenceOfElementLocated(byPasswordField, 3);
		ele.sendKeys(password);
		CommonUtilities.sleepByNSeconds(1);
	}
	
	public void clickLoginButton() {
		Log.info("Click on Login button");
		ele = getElementPresenceOfElementLocated(bySubmitButton, 3);
		ele.submit();
		CommonUtilities.sleepByNSeconds(1);
	}
	
	public boolean isUserAccountNotFoundDisplayed() {
		Log.info("Validate creds authentication");
		// ele = getElementPresenceOfElementLocated(byUserAccountNotfound,3);
		return isElementVisible(byUserAccountNotfound);
	}
	

}

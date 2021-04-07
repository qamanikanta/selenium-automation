package src.com.core;

/*
 * Author  : Wan Muhammad Asyrulnizam Wan Mustaffa Kamel
 * Description : This class contains all require web elements and functionality in Page Base class.
 * Creation Date : 26.11.2020
 * Last modification date : 08.12.2020
 */

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageBase {

	WebDriver ldriver;
	public WebDriverWait wait;
	public static Logger logger;

	/* This method is to initialize Page Factory web element */
	public PageBase(WebDriver rdriver) {
		ldriver = rdriver;
		PageFactory.initElements(rdriver, this);
	}

	/* List of locators */
	@FindBy(id = "username")
	@CacheLookup
	WebElement txtUserName;

	@FindBy(id = "password")
	@CacheLookup
	WebElement txtPassword;

	@FindBy(xpath = "//button[@type='submit']//span[@class='mat-button-wrapper'][contains(text(),'LOG IN')]")
	@CacheLookup
	WebElement buttonLogin;

	@FindBy(xpath = "//span[@class='material-icons close']")
	@CacheLookup
	WebElement feedbackwindowclose;

	@FindBy(xpath = "//mat-icon[@class='icon power mat-icon notranslate material-icons mat-icon-no-color']")
	@CacheLookup
	WebElement buttonLogout;

	/* This method is to set username */
	public void setUserName(String uname) {
		txtUserName.sendKeys(uname);
	}

	/* This method is to set password */
	public void setPassword(String pwd) {
		txtPassword.sendKeys(pwd);
	}

	/* This method is to click on Login button */
	public void clickLogin() {
		buttonLogin.click();
	}

	/* This method is to close feedback window */
	public boolean processFeedBackWindow()

	{
		if (feedbackwindowclose.isDisplayed()) {
			closeFeedbackWindow();
			return true;
		} else {
			return false;
		}
	}

	/* This method is to close feedback window */
	public void closeFeedbackWindow() {
		feedbackwindowclose.click();
	}

	/* This method is to click on logout button */
	public void clickLogout() {
		buttonLogout.click();
	}

	/* This method is to get page title */
	public String getPageTitle() {
		return ldriver.getTitle();
	}

	/* This method is to read text */
	public String readText(By elementLocation) {
		return ldriver.findElement(elementLocation).getText();
	}

	/* This method is to wait for element */
	public void waitVisibility(By by) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	/* This method is to ensure element is displayed */
	public boolean isElementPresent(WebElement elementName, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(ldriver, timeout);
			wait.until(ExpectedConditions.visibilityOf(elementName));
			logger.info("Wait is " + wait);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
		}
	}
	
	/*
	 * This methods for DataEntry User HEDI login locators
	 */
	@FindBy(css = "div > label:nth-of-type(1)")
	WebElement HEIDIUsername;
	
	@FindBy(css = "div > label:nth-of-type(2)")
	WebElement HEIDIPassword;
	
	@FindBy(css = "button[role='button'] > .col.q-anchor--skip.q-btn__wrapper.row")
	WebElement HEIDILoginbutton;
	
	/*
	 * This method for HEIDI Reviewer locator
	 */
/*	@FindBy(css="label:nth-of-type(1) .q-field__native.q-placeholder")
	WebElement reviewer_username;
	@FindBy(css="label:nth-of-type(2) .q-field__native.q-placeholder")
	WebElement reviewer_password;*/
	
	
	
	/* This method is to set username */
	public void setHeidiUserName(String uname) {
		HEIDIUsername.sendKeys(uname);
	}
	
	/* This method is to set password */
	public void setHeidiPassword(String pwd) {
		HEIDIPassword.sendKeys(pwd);
	}

	/* This method is to click on Login button */
	public void clickHeidiLogin() {
		HEIDILoginbutton.click();
	}

}

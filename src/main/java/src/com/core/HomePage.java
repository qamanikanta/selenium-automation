package src.com.core;

/*
 * Author  : Wan Muhammad Asyrulnizam Wan Mustaffa Kamel
 * Description : This class contains all require web elements and functionality in Home Page.
 * Creation Date : 27.11.2020
 * Last modification date : 08.12.2020
 */

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.pocgear.testCases.TestBase;

public class HomePage extends TestBase{

	static WebDriver ldriver;
	
	/* This method is to initialize Page Factory web element */
	public HomePage(WebDriver rdriver) {
		ldriver = rdriver;
		PageFactory.initElements(rdriver, this);
	}
	
	/* List of locators */
	@FindBy(xpath = "//button[contains(.,'Search forRegulatory & Reimbursement content components')]")
	WebElement RNRBtn;
	
	@FindBy(xpath = "//button[contains(.,'Search for AEDcontent components')]")
	WebElement AEDBtn;
	
	@FindBy(xpath = "//a[@class='ng-star-inserted'][contains(.,'power_settings_new')]")
	WebElement LogoutBtn;
	
	@FindBy(css = "button[ng-reflect-router-link='/findInformation'] .button-text")
	WebElement AEDContentComponent;
	
	
	
	/* This method is to ensure RNR button is displayed */
	public boolean RNRButtonIsDisplayed() {
		boolean result = RNRBtn.isDisplayed();
		logger.info("RNR Button is Displayed");
		return result;
	}
	
	/* This method is to ensure AED button is displayed */
	public boolean AEDButtonIsDisplayed() {
		boolean result = AEDBtn.isDisplayed();
		logger.info("AED Button is Displayed");
		return result;
	}
	
	/* This method is to Click RNR button */
	public boolean RNRButtonClick() {
		if (RNRBtn.isDisplayed() == true) {
			RNRBtn.click();
			logger.info("RNR Button is clicked");
			return true;
		} else {
			return false;
		}
	}
	/* This method is to Click Logout button */
	public void LogoutBtnClick() {
		LogoutBtn.click();
		logger.info("Logout Button is clicked");
	}
	
	/* This method is to click on Content Component */
	public void AEDContentComponentClick() {
		AEDContentComponent.click();
		logger.info("AED Button Clicked");
	}
	
}

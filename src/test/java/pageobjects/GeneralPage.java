package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import constant.Constant;

/**
 * contain commonly used elements between pages
 * @author thanhdol
 *
 */
public abstract class GeneralPage {

	private By loginButton = By.id("login");
	
	protected WebElement getLoginButton() {
		return Constant.WEBDRIVER.findElement(loginButton);
	}
	public void gotoLoginPage() {
		this.getLoginButton().click();
	}

}

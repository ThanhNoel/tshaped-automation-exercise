package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import constant.Constant;

public class LoginPage extends GeneralPage {
	
	private By usernameTextBox = By.id("userName");
	private By passwordTextBox = By.id("password");
	
	protected WebElement getUsernameTextBox() {
		return Constant.WEBDRIVER.findElement(usernameTextBox);
	}
	protected WebElement getPasswordTextBox() {
		return Constant.WEBDRIVER.findElement(passwordTextBox);
	}
	public void login(String username, String password) {
		this.getUsernameTextBox().sendKeys(username);
		this.getPasswordTextBox().sendKeys(password);
		this.getLoginButton().click();
	}

}

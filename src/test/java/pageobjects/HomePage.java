package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import constant.Constant;

public class HomePage extends GeneralPage {

	private By usernameLabel = By.id("userName-value");
	

	protected WebElement getUsernameLabel() {
		return Constant.WEBDRIVER.findElement(usernameLabel);
	}

	public String getUsernameLabelValue() {
		return this.getUsernameLabel().getText();
	}
	
	public void open() {
		Constant.WEBDRIVER.get(Constant.APPLICATION_URL);
	}

}

package testcases;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import constant.Constant;
import io.github.bonigarcia.wdm.WebDriverManager;
import pageobjects.BooksHomePage;
import pageobjects.BooksLoginPage;

public class LoginPageTest {
	
	@BeforeEach
	public void beforeMethod() {
		WebDriverManager.chromedriver().browserVersion(null).setup();
		Constant.WEBDRIVER = new ChromeDriver();
		Constant.WEBDRIVER.manage().window().maximize();
		Constant.WEBDRIVER.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		Constant.WEBDRIVER.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@AfterEach
	public void afterMethod() {
		Constant.WEBDRIVER.quit();
	}
	
	@Test
	public void loginTest() {
		BooksHomePage homePage = new BooksHomePage();
		homePage.open();
		homePage.gotoLoginPage();
		
		BooksLoginPage loginPage = new BooksLoginPage();
		loginPage.login(Constant.USERNAME, Constant.PASSWORD);
		
		String headerText = homePage.getUsernameLabelValue();
		assertEquals(headerText, Constant.USERNAME, "Username is not displayed properly");
	}

}

package testcases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import constant.Constant;
import io.github.bonigarcia.wdm.WebDriverManager;
import pageobjects.BooksHomePage;
import pageobjects.BooksLoginPage;

public class AbstractBookPageTest {

    protected static final String BOOK_LINK_ID_PREFIX = "see-book-";
    @BeforeEach
    public void beforeMethod() {
        WebDriverManager.chromedriver().browserVersion(null).setup();
        Constant.WEBDRIVER = new ChromeDriver();
        Constant.WEBDRIVER.manage().window().maximize();
        Constant.WEBDRIVER.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
        Constant.WEBDRIVER.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    @AfterEach
    public void afterMethod() {
        Constant.WEBDRIVER.quit();
    }

    protected void assertUserLoggedInSuccess(BooksHomePage homePage) {
        homePage.gotoLoginPage();
        BooksLoginPage loginPage = new BooksLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);
        String headerText = homePage.getUsernameLabelValue();
        assertEquals(headerText, Constant.USERNAME, "Username is not displayed properly");
        System.out.println("Precondition: logged in success: DONE");
    }

    // will skip test case if profile link not available
    protected void goToProfilePage() {
        By profileLinkLocator = By.xpath("//span[@class='text' and text()='Profile']/parent::li");
        assumeTrue(Constant.WEBDRIVER.findElements(profileLinkLocator).size() > 0, () -> "Profile link button not found");
        
        WebElement profileLink = Constant.WEBDRIVER.findElement(profileLinkLocator);
        System.out.println("Found profile link, scrolling to view");
        ((ChromeDriver) Constant.WEBDRIVER).executeScript(
                "arguments[0].scrollIntoView();", profileLink);
        profileLink.click();
    }

    // will skip test case if called from page without add to collection button
    protected void addBookToCollection() {
        By addBookToCollectionLocator = By.xpath("//button[@id='addNewRecordButton' and text() = 'Add To Your Collection']");
        assumeTrue(Constant.WEBDRIVER.findElements(addBookToCollectionLocator).size() > 0, () -> "Button add book not found");
        Constant.WEBDRIVER.findElement(addBookToCollectionLocator).click();
        System.out.println("Clicked add book to collection");
    }

    protected void expectAndConfirmPopup() {
        // expect popup
        try {
            Alert alert = Constant.WEBDRIVER.switchTo().alert();
            System.out.println("Popup with text '" + alert.getText() + "'. Closing...");
            alert.accept();
        } catch (NoAlertPresentException e) {
            fail("should have notification after click");
        }
    }

    protected By searchBookByText(String searchCriteria) {
        By searchBox = By.id("searchBox");
        Constant.WEBDRIVER.findElement(searchBox).clear();
        Constant.WEBDRIVER.findElement(searchBox).sendKeys(searchCriteria);
        return searchBox;
    }

    protected void hideAdvertisementBanner() {
        try { // if there're ads, hide it
            By adsLocator = By.id("fixedban");
            WebElement adsElement = Constant.WEBDRIVER.findElement(adsLocator);
            ((ChromeDriver) Constant.WEBDRIVER).executeScript("arguments[0].style.visibility='hidden'", adsElement);
        } catch (NoSuchElementException e) {
            // do nothing
        }
    }
}

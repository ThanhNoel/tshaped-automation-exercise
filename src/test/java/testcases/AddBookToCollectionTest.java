package testcases;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import constant.Constant;
import pageobjects.BooksHomePage;

public class AddBookToCollectionTest extends AbstractBookPageTest {

    @DisplayName("When book is added to collection, book is shown in profile page")
    @Test
    public void whenAddBookToCollection_BookShownCorrectly() throws InterruptedException {
        BooksHomePage homePage = new BooksHomePage();
        homePage.open();
        
        assertUserLoggedInSuccess(homePage);
        // condition: exist book
        String expectedBookTitle = "Git Pocket Guide";
        By bookLink = By.xpath("//span[@id='" + BOOK_LINK_ID_PREFIX + expectedBookTitle + "']/a");
        assumeTrue(Constant.WEBDRIVER.findElements(bookLink).size() > 0);
        System.out.println("Pre-condition: has book " + expectedBookTitle + " DONE");
        // click book link then click add to collection:
        Constant.WEBDRIVER.findElement(bookLink).click();
        addBookToCollection();
        Thread.sleep(1000);
        expectAndConfirmPopup();
        hideAdvertisementBanner();
        goToProfilePage();
        assertAll(
                () -> assertTrue(Constant.WEBDRIVER.getCurrentUrl().contains("/profile")), // clicking profile button must go to profile url
                () -> assertEquals(Constant.WEBDRIVER.findElements(bookLink).size() == 1, true));
    }

}

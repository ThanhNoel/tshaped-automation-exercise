package testcases;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import constant.Constant;
import pageobjects.BooksHomePage;

public class DeleteBookFromUserCollectionTest extends AbstractBookPageTest {
    
    private String expectedBookTitle;
    
    @Test
    public void when_deleteBookFromUser_bookRemovedFromProfile() throws InterruptedException {
        BooksHomePage homePage = new BooksHomePage();
        homePage.open();
        expectedBookTitle = "Learning JavaScript Design Patterns";
        assertUserLoggedInSuccess(homePage);
        goToProfilePage();
        searchBookByText("Learning JavaScript Design Patterns");
        By bookLink = By.xpath("//span[@id='see-book-" + expectedBookTitle + "']/a");
        // if book not found, skip test
        assumeTrue(Constant.WEBDRIVER.findElements(bookLink).size() > 0, "Expected to find book " + expectedBookTitle);
        By deleteButton = By.xpath("//span[@id='see-book-" + expectedBookTitle + "']/../../..//span[@title='Delete']");
        Constant.WEBDRIVER.findElement(deleteButton).click();
        
        Constant.WEBDRIVER.switchTo().activeElement();
        By confirmDeleteBook = By.id("closeSmallModal-ok");
        Constant.WEBDRIVER.findElement(confirmDeleteBook).click();
        
        Thread.sleep(1000);
        expectAndConfirmPopup();
        Thread.sleep(1000);
        assertTrue(Constant.WEBDRIVER.findElements(bookLink).size() == 0);
    }

    @AfterEach
    @Override
    public void afterMethod() {
        System.out.println("Re-adding book after deletion...");
        BooksHomePage homePage = new BooksHomePage();
        homePage.open();
        // condition: exist book
        searchBookByText("Learning JavaScript Design Patterns");
        By bookLink = By.xpath("//span[@id='" + BOOK_LINK_ID_PREFIX + expectedBookTitle + "']/a");
        if (Constant.WEBDRIVER.findElements(bookLink).size() > 0) {
            try {
                // click book link then click add to collection:
                Constant.WEBDRIVER.findElement(bookLink).click();
                addBookToCollection();
                Thread.sleep(1000);
                expectAndConfirmPopup();
            } catch (Exception e) {
                fail("exception cleaning up", e);
            }
        }
        Constant.WEBDRIVER.quit();
    }

}

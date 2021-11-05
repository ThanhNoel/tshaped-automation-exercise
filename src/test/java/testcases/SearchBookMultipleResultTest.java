package testcases;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import constant.Constant;
import pageobjects.BooksHomePage;

public class SearchBookMultipleResultTest extends AbstractBookPageTest {
    

    @DisplayName("When enter keyword by different letter cases (Design, design), result shown regardless of case")
    @Test
    public void whenSearchBookByKeyWord_showMatchedBooks() {
        BooksHomePage homePage = new BooksHomePage();
        homePage.open();
        String[] expectedBookTitles = new String[] {"Learning JavaScript Design Patterns", "Designing Evolvable Web APIs with ASP.NET"};
        for (String bookTitle : expectedBookTitles) {
            By bookElement = By.id(BOOK_LINK_ID_PREFIX + bookTitle);
            assumeTrue(Constant.WEBDRIVER.findElements(bookElement).size() > 0); // fail -> skip this test
        }
        searchBookByText("Design");
        for (String bookTitle : expectedBookTitles) {
            By bookElement = By.id(BOOK_LINK_ID_PREFIX + bookTitle);
            assertTrue(Constant.WEBDRIVER.findElements(bookElement).size() > 0);
        }
        searchBookByText("design");
        for (String bookTitle : expectedBookTitles) {
            By bookElement = By.id(BOOK_LINK_ID_PREFIX + bookTitle);
            assertTrue(Constant.WEBDRIVER.findElements(bookElement).size() > 0);
        }
    }
}

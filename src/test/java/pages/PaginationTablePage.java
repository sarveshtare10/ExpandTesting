package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PaginationTablePage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "table.table-striped tbody tr:first-child td")
    private java.util.List<WebElement> firstRowColumns;

    @FindBy(linkText = "Next")
    private WebElement nextButton;

    @FindBy(css = "ul.pagination li.active span")
    private WebElement activePageNumber;

    public PaginationTablePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void navigateTo() {
        driver.get("https://practice.expandtesting.com/dynamic-pagination-table");
    }

    public String getFirstRowData() {
        wait.until(ExpectedConditions.visibilityOfAllElements(firstRowColumns));
        StringBuilder rowData = new StringBuilder();
        for (WebElement col : firstRowColumns) {
            rowData.append(col.getText()).append(" ");
        }
        return rowData.toString().trim();
    }

    public void clickNextButton() {
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
    }

    public void clickPageNumber(String pageNumber) {
        WebElement pageLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(pageNumber)));
        pageLink.click();
    }

    public String getActivePageNumber() {
        return wait.until(ExpectedConditions.visibilityOf(activePageNumber)).getText();
    }
}
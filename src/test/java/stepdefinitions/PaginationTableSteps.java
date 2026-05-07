package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.PaginationTablePage;
import utils.DriverManager;

public class PaginationTableSteps {

    private PaginationTablePage paginationTablePage;
    private String previousFirstRowData;

    public PaginationTableSteps() {
        this.paginationTablePage = new PaginationTablePage(DriverManager.getDriver());
    }

    @Given("I navigate to the Expand Testing dynamic pagination table page")
    public void iNavigateToTheExpandTestingDynamicPaginationTablePage() {
        paginationTablePage.navigateTo();
    }

    @When("I record the first row data of the current page")
    public void iRecordTheFirstRowDataOfTheCurrentPage() {
        previousFirstRowData = paginationTablePage.getFirstRowData();
    }

    @When("I click on the {string} pagination button")
    public void iClickOnThePaginationButton(String buttonName) {
        if ("Next".equalsIgnoreCase(buttonName)) {
            paginationTablePage.clickNextButton();
        } else {
            throw new IllegalArgumentException("Button " + buttonName + " not supported in this step yet.");
        }
    }

    @Then("the first row data should be different from the previous page")
    public void theFirstRowDataShouldBeDifferentFromThePreviousPage() {
        String currentFirstRowData = paginationTablePage.getFirstRowData();
        Assert.assertNotEquals(currentFirstRowData, previousFirstRowData, "The first row data did not change after pagination.");
    }

    @When("I click on pagination page {string}")
    public void iClickOnPaginationPage(String pageNumber) {
        paginationTablePage.clickPageNumber(pageNumber);
    }

    @Then("the active pagination button should be {string}")
    public void theActivePaginationButtonShouldBe(String expectedPageNumber) {
        // Depending on the exact DOM structure, the active page span might contain " (current)"
        String actualActiveText = paginationTablePage.getActivePageNumber();
        Assert.assertTrue(actualActiveText.contains(expectedPageNumber), 
            "Expected active page to be " + expectedPageNumber + " but found " + actualActiveText);
    }
}
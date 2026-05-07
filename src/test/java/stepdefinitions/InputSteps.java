package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import pages.InputsPage;
import utils.DriverManager;
import utils.ExcelReader;

import java.util.List;
import java.util.Map;

public class InputSteps {
    private InputsPage inputsPage;
    private static final String EXCEL_PATH = "src/test/resources/Excel_Files/InputStepsExcel.xlsx";
    private static final String SHEET_NAME = "Sheet1";

    public InputSteps() {
        this.inputsPage = new InputsPage(DriverManager.getDriver());
    }

    @Given("I navigate to the Expand Testing inputs page")
    public void iNavigateToTheExpandTestingInputsPage() {
        inputsPage.navigateTo();
    }

    @When("I fill out the form using data from row {string} of the Excel file")
    public void iFillOutTheFormUsingDataFromRowOfTheExcelFile(String rowNumberStr) {
        int rowIndex = Integer.parseInt(rowNumberStr) - 1; // 0-based index for list
        List<Map<String, String>> testData = ExcelReader.readExcelData(EXCEL_PATH, SHEET_NAME);
        
        if (rowIndex >= testData.size()) {
            throw new IllegalArgumentException("Row number " + rowNumberStr + " exceeds available data in Excel.");
        }
        
        Map<String, String> row = testData.get(rowIndex);
        
        inputsPage.enterNumber(row.get("Number"));
        inputsPage.enterText(row.get("Text"));
        inputsPage.enterPassword(row.get("Password"));
        inputsPage.enterDate(row.get("Date"));
    }

    @When("I enter {string} in the number field")
    public void iEnterInTheNumberField(String number) {
        inputsPage.enterNumber(number);
    }

    @When("I enter {string} in the text field")
    public void iEnterInTheTextField(String text) {
        inputsPage.enterText(text);
    }

    @When("I click the Display Inputs button")
    public void iClickTheDisplayInputsButton() {
        inputsPage.clickDisplayInputs();
    }

    @When("I click the Clear Inputs button")
    public void iClickTheClearInputsButton() {
        inputsPage.clickClearInputs();
    }

    @Then("I should see the entered values from row {string} displayed correctly on the screen")
    public void iShouldSeeTheEnteredValuesFromRowDisplayedCorrectlyOnTheScreen(String rowNumberStr) {
        int rowIndex = Integer.parseInt(rowNumberStr) - 1;
        List<Map<String, String>> testData = ExcelReader.readExcelData(EXCEL_PATH, SHEET_NAME);
        Map<String, String> row = testData.get(rowIndex);

        Assert.assertEquals(inputsPage.getDisplayedNumber(), row.get("Number"));
        Assert.assertEquals(inputsPage.getDisplayedText(), row.get("Text"));
        Assert.assertEquals(inputsPage.getDisplayedPassword(), row.get("Password"));
        
        // Handle date reformatting logic (Excel might have different formats, but website expects YYYY-MM-DD or MM/DD/YYYY)
        // Adjust this based on how Apache POI formats the date string in getCellValueAsString
        String expectedDate = row.get("ExpectedDate"); // Assume we have a column for expected format if needed, or format it dynamically
        // If the website outputs YYYY-MM-DD but we sent MM/DD/YYYY, we need to convert the expected string.
        String sentDate = row.get("Date");
        String finalExpectedDate = sentDate;
        if(sentDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
             String[] parts = sentDate.split("/");
             finalExpectedDate = parts[2] + "-" + parts[0] + "-" + parts[1];
        } else if (sentDate.matches("\\d{1,2}/\\d{1,2}/\\d{2}")) {
            // Very simple fallback for different excel date formats, ideally configure POI date formatting
            // This is just a placeholder, real logic depends on your excel data format
        }

        Assert.assertEquals(inputsPage.getDisplayedDate(), finalExpectedDate);
    }

    @Then("all input fields should be empty")
    public void allInputFieldsShouldBeEmpty() {
        Assert.assertTrue(inputsPage.areAllInputFieldsEmpty(), "Not all input fields were cleared.");
    }
}
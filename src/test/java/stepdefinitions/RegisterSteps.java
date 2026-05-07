package stepdefinitions;

import com.github.javafaker.Faker;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.RegisterPage;
import utils.DriverManager;
import utils.ExcelWriter;

public class RegisterSteps {

    private RegisterPage registerPage;
    private String generatedUsername;
    private String generatedPassword;
    private static final String EXCEL_PATH = "src/test/resources/Excel_Files/RegisteredUsers.xlsx";
    private static final String SHEET_NAME = "Users";

    public RegisterSteps() {
        this.registerPage = new RegisterPage(DriverManager.getDriver());
    }

    @Given("I navigate to the Expand Testing register page")
    public void iNavigateToTheExpandTestingRegisterPage() {
        registerPage.navigateTo();
    }

    @When("I register a new user with random credentials")
    public void iRegisterANewUserWithRandomCredentials() {
        Faker faker = new Faker();
        
        // Generate alphanumeric username without special characters, minimum 6 length
        generatedUsername = faker.name().username().replaceAll("[^a-zA-Z0-9]", "") + faker.number().digits(4);
        
        // Ensure password meets standard complexity requirements (if any on this site, faker is usually enough)
        generatedPassword = faker.internet().password(8, 15, true, true, true);

        registerPage.enterUsername(generatedUsername);
        registerPage.enterPassword(generatedPassword);
        registerPage.enterConfirmPassword(generatedPassword);
        registerPage.clickRegister();
    }

    @Then("I should see the successful registration message {string}")
    public void iShouldSeeTheSuccessfulRegistrationMessage(String expectedMessage) {
        String actualMessage = registerPage.getStatusAlertText();
        Assert.assertTrue(actualMessage.contains(expectedMessage), 
            "Expected success message to contain: '" + expectedMessage + "', but was: '" + actualMessage + "'");
    }

    @Then("the generated credentials should be saved to Excel")
    public void theGeneratedCredentialsShouldBeSavedToExcel() {
        ExcelWriter.writeToExcel(EXCEL_PATH, SHEET_NAME, generatedUsername, generatedPassword);
    }
}
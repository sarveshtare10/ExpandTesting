package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.LoginPage;
import utils.DriverManager;

public class LoginSteps {

    private LoginPage loginPage;

    public LoginSteps() {
        this.loginPage = new LoginPage(DriverManager.getDriver());
    }

    @Given("I navigate to the Expand Testing login page")
    public void iNavigateToTheExpandTestingLoginPage() {
        loginPage.navigateTo();
    }

    @When("I enter {string} as username")
    public void iEnterAsUsername(String username) {
        loginPage.enterUsername(username);
    }

    @When("I enter {string} as password")
    public void iEnterAsPassword(String password) {
        loginPage.enterPassword(password);
    }

    @When("I click the login button")
    public void iClickTheLoginButton() {
        loginPage.clickLogin();
    }

    @Then("I should be redirected to the secure page")
    public void iShouldBeRedirectedToTheSecurePage() {
        Assert.assertTrue(loginPage.isLogoutButtonDisplayed(), "Logout button is not displayed, likely not on secure page.");
        Assert.assertTrue(DriverManager.getDriver().getCurrentUrl().contains("/secure"), "Not redirected to secure page.");
    }

    @Then("I should see a {string} message")
    public void iShouldSeeAMessage(String expectedMessage) {
        String actualMessage = loginPage.getStatusAlertText();
        Assert.assertTrue(actualMessage.contains(expectedMessage), 
            "Expected message to contain: '" + expectedMessage + "', but was: '" + actualMessage + "'");
    }

    @Then("I should see an {string} error message")
    public void iShouldSeeAnErrorMessage(String expectedMessage) {
        iShouldSeeAMessage(expectedMessage); // Reusing the same method as both use the same flash alert element
    }
    
    @Given("I am logged in")
    public void iAmLoggedIn() {
        loginPage.login("practice", "Practice123!");
        Assert.assertTrue(loginPage.isLogoutButtonDisplayed(), "Failed to login during background step.");
    }

    @When("I click the logout button")
    public void iClickTheLogoutButton() {
        loginPage.clickLogout();
    }

    @Then("I should be redirected to the login page")
    public void iShouldBeRedirectedToTheLoginPage() {
        Assert.assertTrue(DriverManager.getDriver().getCurrentUrl().contains("/login"), "Not redirected to login page after logout.");
    }
}
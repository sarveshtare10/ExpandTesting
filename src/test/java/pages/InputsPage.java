package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;

public class InputsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators for Inputs
    @FindBy(id = "input-number")
    private WebElement numberInput;

    @FindBy(id = "input-text")
    private WebElement textInput;

    @FindBy(id = "input-password")
    private WebElement passwordInput;

    @FindBy(id = "input-date")
    private WebElement dateInput;

    @FindBy(id = "btn-display-inputs")
    private WebElement displayInputsBtn;

    @FindBy(id = "btn-clear-inputs")
    private WebElement clearInputsBtn;

    // Locators for Outputs
    @FindBy(id = "output-number")
    private WebElement outputNumber;

    @FindBy(id = "output-text")
    private WebElement outputText;

    @FindBy(id = "output-password")
    private WebElement outputPassword;

    @FindBy(id = "output-date")
    private WebElement outputDate;

    public InputsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void navigateTo() {
        driver.get("https://practice.expandtesting.com/inputs");
    }

    public void enterNumber(String number) {
        WebElement element = wait.until(ExpectedConditions.visibilityOf(numberInput));
        element.sendKeys(number);
    }

    public void enterText(String text) {
        textInput.sendKeys(text);
    }

    public void enterPassword(String password) {
        passwordInput.sendKeys(password);
    }

    public void enterDate(String date) {
        // Handle input type="date" in Firefox by formatting to standard yyyy-MM-dd
        String formattedDate = date;
        if (date.matches("\\d{2}/\\d{2}/\\d{4}")) {
            String[] parts = date.split("/");
            formattedDate = parts[2] + "-" + parts[0] + "-" + parts[1];
        }
        
        // Use JavascriptExecutor as it's the most robust way to set date fields across different browser locales
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", dateInput, formattedDate);
        
        // Also send an empty string to trigger any DOM events
        dateInput.sendKeys("");
    }

    public void clickDisplayInputs() {
        displayInputsBtn.click();
    }

    public void clickClearInputs() {
        clearInputsBtn.click();
    }

    public boolean areAllInputFieldsEmpty() {
        return numberInput.getAttribute("value").isEmpty() &&
               textInput.getAttribute("value").isEmpty() &&
               passwordInput.getAttribute("value").isEmpty() &&
               dateInput.getAttribute("value").isEmpty();
    }

    // Output Validation Methods
    public String getDisplayedNumber() {
        return wait.until(d -> {
            String text = outputNumber.getText();
            return text.isEmpty() ? null : text;
        });
    }
    public String getDisplayedText() {
        return wait.until(d -> {
            String text = outputText.getText();
            return text.isEmpty() ? null : text;
        });
    }
    public String getDisplayedPassword() {
        return wait.until(d -> {
            String text = outputPassword.getText();
            return text.isEmpty() ? null : text;
        });
    }
    public String getDisplayedDate() {
        return wait.until(d -> {
            String text = outputDate.getText();
            return text.isEmpty() ? null : text;
        });
    }
}
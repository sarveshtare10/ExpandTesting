package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;

    @FindBy(id = "flash")
    private WebElement statusAlert;

    @FindBy(xpath = "//a[contains(@class, 'button') and contains(@class, 'secondary') and contains(@class, 'radius')]")
    private WebElement logoutButton;
    
    @FindBy(xpath = "//div[contains(@class, 'container')]//h4")
    private WebElement welcomeHeader;

    @FindBy(xpath = "//div[contains(@class, 'container')]//h4/following-sibling::div")
    private WebElement welcomeSubtext;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void navigateTo() {
        driver.get("https://practice.expandtesting.com/login");
    }

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOf(usernameInput)).sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordInput.sendKeys(password);
    }

    public void clickLogin() {
        loginButton.click();
    }

    public String getStatusAlertText() {
        return wait.until(ExpectedConditions.visibilityOf(statusAlert)).getText();
    }
    
    public String getWelcomeHeader() {
        return wait.until(ExpectedConditions.visibilityOf(welcomeHeader)).getText();
    }

    public String getWelcomeSubtext() {
        return wait.until(ExpectedConditions.visibilityOf(welcomeSubtext)).getText();
    }

    public boolean isLogoutButtonDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(logoutButton)).isDisplayed();
    }

    public void clickLogout() {
        if (isLogoutButtonDisplayed()) {
            logoutButton.click();
        }
    }

    public void login(String username, String password) {
        navigateTo();
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
}
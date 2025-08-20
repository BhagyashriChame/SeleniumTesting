
package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    private By username = By.name("username");
    private By password = By.name("password");
    private By loginBtn = By.xpath("//input[@value='Log In']");
    private By logoutLink = By.xpath("//a[text()='Log Out']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("https://parabank.parasoft.com/");
    }

    public void login(String user, String pass) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(username)).sendKeys(user);
        driver.findElement(password).sendKeys(pass);
        driver.findElement(loginBtn).click();
    }

    public boolean isLogoutVisible() {
        return driver.findElements(logoutLink).size() > 0;
    }

    public void logout() {
        if (isLogoutVisible()) {
            wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
        }
    }
}


package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegisterPage extends BasePage {

    private By firstName = By.name("customer.firstName");
    private By lastName = By.name("customer.lastName");
    private By street = By.name("customer.address.street");
    private By city = By.name("customer.address.city");
    private By state = By.name("customer.address.state");
    private By zip = By.name("customer.address.zipCode");
    private By phone = By.name("customer.phoneNumber");
    private By ssn = By.name("customer.ssn");
    private By username = By.name("customer.username");
    private By password = By.name("customer.password");
    private By confirmPassword = By.name("repeatedPassword");
    private By registerBtn = By.xpath("//input[@value='Register']");
    private By usernameExistsMsg = By.xpath("//*[contains(text(),'This username already exists')]");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("https://parabank.parasoft.com/");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='register.htm']"))).click();
    }

    public void fillForm(String fname, String lname, String str, String cty, String st, String zp, String num,
                         String ssnVal, String user, String pass) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName)).sendKeys(fname);
        driver.findElement(lastName).sendKeys(lname);
        driver.findElement(street).sendKeys(str);
        driver.findElement(city).sendKeys(cty);
        driver.findElement(state).sendKeys(st);
        driver.findElement(zip).sendKeys(zp);
        driver.findElement(phone).sendKeys(num);
        driver.findElement(ssn).sendKeys(ssnVal);
        driver.findElement(username).sendKeys(user);
        driver.findElement(password).sendKeys(pass);
        driver.findElement(confirmPassword).sendKeys(pass);
    }

    public void submit() {
        driver.findElement(registerBtn).click();
    }

    public boolean isUsernameExistsError() {
        return driver.findElements(usernameExistsMsg).size() > 0;
    }
}

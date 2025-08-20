package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CustomerCarePage extends BasePage {

    private By name = By.name("name");
    private By email = By.name("email");
    private By phone = By.name("phone");
    private By message = By.name("message");
    private By sendBtn = By.xpath("//input[@value='Send to Customer Care']");

    public CustomerCarePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("https://parabank.parasoft.com/parabank/contact.htm");
    }

    public void fillForm(String n, String e, String p, String msg) {
        driver.findElement(name).sendKeys(n);
        driver.findElement(email).sendKeys(e);
        driver.findElement(phone).sendKeys(p);
        driver.findElement(message).sendKeys(msg);
    }

    public void submit() {
        driver.findElement(sendBtn).click();
    }
}

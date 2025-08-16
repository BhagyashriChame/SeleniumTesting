package testdemo;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

public class ParaBankTesting {
    WebDriver driver;

    @Parameters({ "browserName", "url" })
    @BeforeClass(alwaysRun = true)
    public void LaunchBrowser(String browserName, String url) {
        switch (browserName.toLowerCase()) {
        case "chrome":
            driver = new ChromeDriver();
            break;
        case "firefox":
            driver = new FirefoxDriver();
            break;
        case "edge":
            driver = new EdgeDriver();
            break;
        default:
            System.out.println("Invalid Browser");
            break;
        }
        driver.manage().window().maximize();
        driver.get(url);
    }

    @Test(groups = {"regression", "smoke"}, priority = 1)
    public void RegisterNewUser() {
        driver.get("https://parabank.parasoft.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='register.htm']"))).click();

        System.out.println(driver.getTitle());

        wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.firstName"))).sendKeys("Bhagyashri");
        wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.lastName"))).sendKeys("Chame");
        wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.address.street"))).sendKeys("Swargate");
        wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.address.city"))).sendKeys("Pune");
        wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.address.state"))).sendKeys("Maharashtra");
        wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.address.zipCode"))).sendKeys("413512");
        wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.phoneNumber"))).sendKeys("8936774840");
        wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.ssn"))).sendKeys("123456");
        wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.username"))).sendKeys("Shri19");
        wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.password"))).sendKeys("Admin123");
        wait.until(ExpectedConditions.elementToBeClickable(By.name("repeatedPassword"))).sendKeys("Admin123");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Register']"))).click();
    }

    @Test(groups = {"smoke","regression"}, dataProvider = "logInTestData", priority = 2)
    public void Login(String username, String password) {

        driver.get("https://parabank.parasoft.com/");
        System.out.println("performing Login");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.name("username"))).sendKeys(username);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Log In']"))).click();
        
      
        
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Log Out']"))).click();
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Logout link not found, maybe login failed.");
        }
    }

        
    
     // Ignore the all test cases
    @Ignore("CustomerCare Test Case Ignored")
    @Test(groups= {"regression"})
    public void CustomerCare() {
        driver.get("https://parabank.parasoft.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='contact.htm']"))).click();
        System.out.println("Opening CustomerCare");
        wait.until(ExpectedConditions.elementToBeClickable(By.name("name"))).sendKeys("Shri");
        wait.until(ExpectedConditions.elementToBeClickable(By.name("email"))).sendKeys("Shri@gmail.com");
        wait.until(ExpectedConditions.elementToBeClickable(By.name("phone"))).sendKeys("9922465370");
        wait.until(ExpectedConditions.elementToBeClickable(By.name("message"))).sendKeys("Hi");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Send to Customer Care']"))).click();
    }

    @AfterClass // Reset to login page
    public void afterClass() throws InterruptedException {
        System.out.println("Closing the browser after done");
        if (driver != null) {
            driver.quit();
        }
    }
      
    // It repetededly run 2 times 
    @DataProvider(name="logInTestData")
    public Object[][] logInData() {
        Object[][] data = new Object[2][2];
        data[0][0] = "Shri19";
        data[0][1] = "admin123";
        data[1][0] = "Abhas1";
        data[1][1] = "Admin123";
        return data;
    }
}

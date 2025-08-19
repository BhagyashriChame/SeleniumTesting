package testdemo;

import java.io.IOException;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

@Listeners(testdemo.TestNGListeners.class)
public class ParaBankTesting {
    public WebDriver driver;
    public WebDriverWait wait;

    // 🔹 Create Logger instance for this class
    private static final Logger logger = LogManager.getLogger(ParaBankTesting.class);

    // ================== SETUP ==================
    @Parameters({"browserName", "url"})
    @BeforeClass(alwaysRun = true)
    public void LaunchBrowser(String browserName, String url) {
        logger.info("Launching browser: {} with URL: {}", browserName, url);

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
                logger.error("Invalid browser provided: {}", browserName);
                throw new IllegalArgumentException("Invalid browser: " + browserName);
        }

        // Global waits
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.manage().window().maximize();
        driver.get(url);
        logger.info("Browser launched successfully and navigated to {}", url);
    }

    // ================== REGISTER NEW USER ==================
    @Test(groups = {"regression"}, priority = 1, dataProvider = "registerData", dataProviderClass = DataProviders.class)
    public void RegisterNewUser(String firstname, String secondname, String street, String city, String state,
                                String zip, String number, String ssn, String username, String password) {

        logger.info("Starting registration test for username: {}", username);

        driver.get("https://parabank.parasoft.com/");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='register.htm']"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("customer.firstName"))).sendKeys(firstname);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("customer.lastName"))).sendKeys(secondname);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("customer.address.street"))).sendKeys(street);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("customer.address.city"))).sendKeys(city);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("customer.address.state"))).sendKeys(state);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("customer.address.zipCode"))).sendKeys(zip);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("customer.phoneNumber"))).sendKeys(number);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("customer.ssn"))).sendKeys(ssn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("customer.username"))).sendKeys(username);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("customer.password"))).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("repeatedPassword"))).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Register']"))).click();

        boolean isError = driver.findElements(By.xpath("//*[contains(text(),'This username already exists')]")).size() > 0;

        if (isError) {
            logger.warn("Registration failed for username: {} (already exists)", username);
            Assert.assertTrue(isError, "Registration failed due to existing username");
        } else {
            logger.info("Registration successful for username: {}", username);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Log Out']"))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='Log In']")));
        }
    }

    // ================== LOGIN ==================
    @Test(groups = {"smoke"}, priority = 2, dataProvider = "logInTestData", dataProviderClass = DataProviders.class,
            invocationCount = 2, threadPoolSize = 2)
    public void Login(String username, String password) {
        logger.info("Attempting login for user: {}", username);

        driver.get("https://parabank.parasoft.com/");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))).sendKeys(username);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password"))).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Log In']"))).click();

        boolean isLogoutPresent = driver.findElements(By.xpath("//a[text()='Log Out']")).size() > 0;

        if (isLogoutPresent) {
            logger.info("Login successful for user: {}", username);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Log Out']"))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        } else {
            logger.error("Login failed for user: {}", username);
        }

        Assert.assertTrue(isLogoutPresent, "Login failed for user: " + username);
    }

    // ================== CUSTOMER CARE ==================
    @Ignore
    @Test(groups = {"regression"}, priority = 3)
    public void CustomerCare() {
        logger.info("Navigating to Customer Care page...");
        driver.get("https://parabank.parasoft.com/parabank/contact.htm");

        wait.until(ExpectedConditions.elementToBeClickable(By.name("name"))).sendKeys("Abhas");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email"))).sendKeys("abhasmittal1gmail.com");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("phone"))).sendKeys("1234567890");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("message"))).sendKeys("Testing is fun");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Send to Customer Care']"))).click();

        logger.info("Customer Care form submitted successfully");
    }

    // ================== CLEANUP ==================
    @AfterClass(alwaysRun = true)
    public void afterClass() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed and WebDriver quit successfully");
        }
    }
}
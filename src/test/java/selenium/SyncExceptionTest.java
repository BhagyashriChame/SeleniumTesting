package selenium;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class SyncExceptionTest {
    private static final Logger logger = LogManager.getLogger(SyncExceptionTest.class);
    
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void Logins() {
        logger.info("Starting the login process");  // Goes to console + file + rolling file
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://parabank.parasoft.com/");
        logger.info("Opened ParaBank URL");
    }

    @Test
    public void testLogin() {
        try {
            logger.info("Waiting for username field");
            WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
            username.sendKeys("Siya");
            logger.info("Entered username");

            WebElement password = driver.findElement(By.name("password"));
            password.sendKeys("Siya");
            logger.info("Entered password");

            WebElement loginBtn = driver.findElement(By.cssSelector("input[value='Log In']"));
            loginBtn.click();
            logger.info("Clicked login button");

            WebElement logoutLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Log Out")));
            Assert.assertTrue(logoutLink.isDisplayed(), "Login failed!");
            logger.info("Login successful, Logout link found!");

        } catch (NoSuchElementException e) {
            logger.error("Element not found", e);
            Assert.fail("Test failed due to missing element.");

        } catch (TimeoutException e) {
            logger.error("Timeout while waiting", e);
            Assert.fail("Test failed due to timeout.");

        } catch (Exception e) {
            logger.error("Unexpected error occurred", e);
            Assert.fail("Test failed due to unexpected exception.");
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed");
        }
    }
}

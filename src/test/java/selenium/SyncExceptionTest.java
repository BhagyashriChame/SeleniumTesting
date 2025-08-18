package  selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class SyncExceptionTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {
       
    	System.out.println("Launching the browser");

    	driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Synchronization: Implicit wait
       
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Synchronization: Explicit wait

        driver.get("https://parabank.parasoft.com/");
    }

    @Test
    public void testLoginWithSyncAndExceptionHandling() {
        try {
            // Synchronization - explicit wait for username field
            WebElement username = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.name("username")));
            username.sendKeys("Shri12");

            WebElement password = driver.findElement(By.name("password"));
            password.sendKeys("Admin12");

            WebElement loginBtn = driver.findElement(By.cssSelector("input[value='Log In']"));
            loginBtn.click();

            // Wait for Logout link (sync again)
            WebElement logoutLink = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.linkText("Log Out")));

            // Assertion
            Assert.assertTrue(logoutLink.isDisplayed(), "Login failed!");
            System.out.println("✅ Login successful, Logout link found!");

        } catch (NoSuchElementException e) {
            System.out.println("❌ Element not found: " + e.getMessage());
            Assert.fail("Test failed due to missing element.");
        } catch (TimeoutException e) {
            System.out.println("⏳ Timeout while waiting: " + e.getMessage());
            Assert.fail("Test failed due to timeout.");
        } catch (Exception e) {
            System.out.println("⚠️ Unexpected error: " + e.getMessage());
            Assert.fail("Test failed due to unexpected exception.");
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

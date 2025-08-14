package testdemo;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.*;

public class ParaBankTesting {
	WebDriver driver;

	@Parameters({ "browserName", "url" })
	@BeforeClass(groups= {"smoke"})
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

	@Test(groups = {"smoke"}, priority = 1)
	public void RegisterNewUser() {
		driver.get("https://parabank.parasoft.com/");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='register.htm']"))).click();

		System.out.println(driver.getTitle());

//		driver.findElement(By.id("FirstName")).sendKeys("Shri");//approch no.1, it given instance result
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.firstName"))).sendKeys("Bhagyashri");
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.lastName"))).sendKeys("Chame");
		// approch no.2, and it is a better approch bcz it is giving stability
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.address.street"))).sendKeys("Swargate");
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.address.city"))).sendKeys("Pune");
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.address.state"))).sendKeys("Maharashtra");
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.address.zipCode"))).sendKeys("413512");
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.phoneNumber"))).sendKeys("8936774840");
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.ssn"))).sendKeys("123456");
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.username"))).sendKeys("Shri11");
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.password"))).sendKeys("Admin123");
		wait.until(ExpectedConditions.elementToBeClickable(By.name("repeatedPassword"))).sendKeys("Admin123");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Register']"))).click();

	}

	@Test(groups= {"regression"}) //(priority = 2)
	public void Login() {

		driver.get("https://parabank.parasoft.com/");
		System.out.println("performing Login");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		             // driver.findElement(By.xpath("//input[@name='username']")).sendKeys("Admin");
		wait.until(ExpectedConditions.elementToBeClickable(By.name("username"))).sendKeys("Shri11");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Admin123");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Log In']"))).click();
	}
	
	
	@Ignore("CustomerCare Test Case Ignored")
	@Test(groups= {"regression"})
	public void CustomerCare() {
		driver.get("https://parabank.parasoft.com/");
		                                                //System.out.println("Opening CustomerCare");
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

}
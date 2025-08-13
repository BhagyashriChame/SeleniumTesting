package testdemo;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ApplicationTest {
	WebDriver driver;
	
	@BeforeClass
	public void launchBrowser() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
		
	@Test
	public void registerNewUser() {
		driver.get("https://demowebshop.tricentis.com/");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/register']"))).click();
		
		System.out.println(driver.getTitle());
		
		driver.findElement(By.id("FirstName")).sendKeys("Bhagyashri");
		driver.findElement(By.id("LastName")).sendKeys("Chame");
		driver.findElement(By.id("Email")).sendKeys("bhagyashrischame02@gmail.com");
		driver.findElement(By.id("Password")).sendKeys("B0806b@12");
		
		
		
	}

	@Test
	public void launchGoogle() {
		driver.get("https://www.google.com/");
	}
	
	
	


}

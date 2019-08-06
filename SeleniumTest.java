package testSelenium;

import org.testng.annotations.Test;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class SeleniumTest {
	public WebDriver driver;
	
	@BeforeClass
	public void beforeClass() {
		System.out.println("Intializing FirefoxDriver");
		driver = new FirefoxDriver();
		//setting 30 seconds implicit wait
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	@Test(priority=1)
	public void Search() throws InterruptedException {
		System.out.println("Google Search");
		//Open Google
		driver.get("https://www.google.com/");	
		//Search for gmail
		WebElement element = driver.findElement(By.name("q"));
		element.sendKeys(new String[]{"GMAIL"}); 
		element.submit();
		// Selects Gmail site 
		driver.findElement(By.xpath("//*[text()='Gmail - Google']")).click();
		// Go to Sign in page
		driver.findElement(By.linkText("Sign in")).click();
		// Select the next tab or window cause the Sign in page opens in a new tab
		// Get current window handle
        String parentWinHandle = driver.getWindowHandle();
        // Get the window handles of all open windows
        Set<String> winHandles = driver.getWindowHandles();
        // Loop through all handles
        String newTab = null;
        for(String handle: winHandles){
            if(!handle.equals(parentWinHandle)){
            driver.switchTo().window(handle);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            newTab = driver.getTitle();
            }
        }
        driver.switchTo().window(newTab);
	}
	  
	@Test(priority=2)
	public void Login() throws InterruptedException {
		System.out.println("Logging to Gmail");
		// Enter username
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("TYPE EMAIL ADDRESS");
		// Click Next button
		driver.findElement(By.xpath("//span[@class='RveJvd snByac']")).click();
		// Enter password
		WebElement pwd = driver.findElement(By.xpath("//input[@name='password']"));
		Thread.sleep(2000);
		pwd.sendKeys("TYPE PASSWORD");
		// Click SignIN
		driver.findElement(By.xpath("//span[@class='RveJvd snByac']")).click();  
	}

	@Test(priority=3)
	public void Verify() throws InterruptedException {
		System.out.println("Username Verification");
		String expectedEmail = "EMAIL ADDRESS";
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		// Get the Email address
		WebElement userName = driver.findElement(By.xpath("//div[@class='gb_lb']"));
		String actualEmail = userName.getAttribute("innerHTML");
		// Verification
		if (actualEmail.equals(expectedEmail)) {
			System.out.println("Username verified"); 
		}else {
			System.out.println("Error in verifying username"); 
		}
	}

	@AfterClass
	public void afterclass() {
		// Close the browser
		System.out.println("Close the browser");
		driver.quit();
	}

}

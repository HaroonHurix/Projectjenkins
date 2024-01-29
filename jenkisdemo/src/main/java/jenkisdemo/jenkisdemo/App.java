package jenkisdemo.jenkisdemo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class App
{
	static WebDriver driver;
	
	public static String path = System.getProperty("user.dir");
	
	public void setup() 
	{
		WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(); 
        driver.get(" https://www.equalsense.ai/altadmin/Dashboard");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//implicit wait
	}
	
	//File file = new File(path+"/PadpilotCredential.properties/");
	
	
	public void login() throws IOException, InterruptedException {	
	    BufferedReader br = new BufferedReader(new FileReader(path+"/loginequalsense.txt/"));

	    for (int i = 1; i < 4; i++) {
	        String username = br.readLine();
	        String password = br.readLine();

	        // Locate the username and password fields
	        WebElement usernameField = driver.findElement(By.xpath("//*[@id=\"inputEmail\"]"));
	        WebElement passwordField = driver.findElement(By.xpath("//*[@id=\"inputPassword\"]"));

	        // Enter the username and password
	        usernameField.sendKeys(username);
	        passwordField.sendKeys(password);

	        // Perform login
	        driver.findElement(By.xpath("//*[@id=\"loginForm\"]/button")).click();
	        
	        Thread.sleep(2000);
	        driver.findElement(By.xpath("//div[@id='toast-id-login']/button")).click();
	        

	        // Check for the presence of an error message
	        if (issuccessMessageDisplayed()) {
	            System.out.println("Login success, dashboard is displayed");
	        } else {
	            System.out.println("Login failed, error message is displayed");

	            // Clear fields and prepare for the next iteration
	            clearFieldWithJavaScript(usernameField);
	            Thread.sleep(2000);
	            clearFieldWithJavaScript(passwordField);
	            Thread.sleep(2000);
	        }
	    }

	    br.close();
	}

	private boolean issuccessMessageDisplayed() {
	    try {
	        // Check for the presence of the error message element
	        return driver.findElement(By.linkText("Projects")) != null;
	    } catch (org.openqa.selenium.NoSuchElementException e) {
	        return false;
	    }
	}

		   
		   
	 private static void clearFieldWithJavaScript(WebElement element) 
	 {
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("arguments[0].value='';", element);
	 }
	 
	 public void createProject() throws InterruptedException 
	 {
		 WebDriverWait wait = new WebDriverWait(driver,45);
		 WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Projects")));
		 //driver.navigate().refresh();
		 element.click();
		 //driver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/main/div[3]/div[1]/div[3]/ul/li[2]/a/span")).click();
			//driver.findElement(By.linkText("Projects")).click();
			  Thread.sleep(3000); driver.findElement(By.xpath(
			  "//div[@id='root']/div[2]/main/div[3]/div[2]/div/div/button/i")).click();
			  driver.findElement(By.id("projectName")).sendKeys("testauto1");
			  driver.findElement(By.id("description")).sendKeys("test auto one discription"
			  ); driver.findElement(By.xpath("//div[@id='selectOption']/div/div/div[2]")).
			  click(); driver.findElement(By.id("react-select-2-option-4")).click();
			  driver.findElement(By.
			  xpath("(.//*[normalize-space(text()) and normalize-space(.)='Enter user name'])[1]/following::div[1]"
			  )).click(); driver.findElement(By.xpath(
			  "//div[@id='react-select-3-option-1']/div/div/span/div/span[2]")).click();
			  driver.findElement(By.
			  xpath("(.//*[normalize-space(text()) and normalize-space(.)='Cancel'])[1]/following::button[1]"
			  )).click();
			  driver.findElement(By.xpath("//div[@id='toast-id-create-project']/button")).
			  click();
			 
	 }
	

	public static void main(String[] args) throws IOException, InterruptedException 
	{
		App alt1= new App();
		alt1.setup();
		alt1.login();
		//alt1.createProject();
	}

}

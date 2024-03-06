package AnkitSeleniumLearning.Tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
import junit.framework.Assert;

public class StandAloneTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		// New Comments added here
		String productName = "ZARA COAT 3";
		WebDriverManager.edgedriver().setup();
		WebDriver driver = new EdgeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));

		driver.get("https://rahulshettyacademy.com/client");
		driver.findElement(By.id("userEmail")).sendKeys("newuserone@yopmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Password@1");
		driver.findElement(By.id("login")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		WebElement prod = products.stream()
				.filter(product -> product.findElement(By.tagName("b")).getText().equals(productName)).findFirst()
				.orElse(null);

		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		// ng-animating
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		driver.findElement(By.cssSelector("button[routerlink*='cart']")).click();

		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));

		boolean match = cartProducts.stream()
				.anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match);

		driver.findElement(By.cssSelector(".totalRow button")).click();

		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("input[placeholder='Select Country']")), "india").build()
				.perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		driver.findElement(By.xpath("//button[contains(@class,'ta-item')][2]")).click();
		driver.findElement(By.cssSelector(".actions a")).click();
		
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		

		driver.close();

	}

}

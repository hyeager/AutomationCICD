package AnkitSeleniumLearning.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AnkitSeleniumLearning.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent {

	WebDriver driver;

	public LandingPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(id = "userEmail")
	WebElement useremail;

	@FindBy(id = "userPassword")
	WebElement passwordBy;

	@FindBy(id = "login")
	WebElement submit;
	
	@FindBy(css = "[class*='flyInOut']")
	WebElement errorMessage;
	
	
	
	//.ng-tns-c4-10.ng-star-inserted.ng-trigger.ng-trigger-flyInOut.ngx-toastr.toast-error

	public void goTo() {

		driver.get("https://rahulshettyacademy.com/client");

	}

	public ProductCatalogue loginApplication(String email, String password) {

		useremail.sendKeys(email);
		passwordBy.sendKeys(password);
		submit.click();
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		return productCatalogue;

	}
	
	public String getErrorMessage() {
		
		waitForWebelementToAppear(errorMessage);		
		return errorMessage.getText();
		
	}

}

package AnkitSeleniumLearning.Tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import AnkitSeleniumLearning.TestComponents.BaseTest;
import AnkitSeleniumLearning.TestComponents.Retry;
import AnkitSeleniumLearning.pageObjects.CartPage;
import AnkitSeleniumLearning.pageObjects.CheckoutPage;
import AnkitSeleniumLearning.pageObjects.ConfirmationPage;
import AnkitSeleniumLearning.pageObjects.ProductCatalogue;
import junit.framework.Assert;

public class ErrorValidations extends BaseTest{
	
	String productName;
	@Test(groups= {"Errorhandling"}, retryAnalyzer = Retry.class)
	public void loginErrorValidation() {
		
		
		landingPage.loginApplication("newuserone@yopmail.com", "Password@123");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
	}
	
	@Test
	public void productErrorValidation() throws IOException {
		productName = "ZARA COAT 3";
		ProductCatalogue productCatalogue = landingPage.loginApplication("newuseronerr@yopmail.com", "Password@123");
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.verifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);
		

	}

}

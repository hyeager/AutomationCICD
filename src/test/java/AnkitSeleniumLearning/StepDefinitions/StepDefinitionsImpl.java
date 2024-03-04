package AnkitSeleniumLearning.StepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;

import AnkitSeleniumLearning.TestComponents.BaseTest;
import AnkitSeleniumLearning.pageObjects.CartPage;
import AnkitSeleniumLearning.pageObjects.CheckoutPage;
import AnkitSeleniumLearning.pageObjects.ConfirmationPage;
import AnkitSeleniumLearning.pageObjects.LandingPage;
import AnkitSeleniumLearning.pageObjects.ProductCatalogue;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;

public class StepDefinitionsImpl extends BaseTest {

	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public ConfirmationPage confirmationPage;

	@Given("I landed on Ecommerce page")
	public void iLandedOnEcommercePage() throws IOException {
		landingPage = launchApplication();
	}

	@Given("^Logged in with username (.+) and password (.+)$")
	public void loggedInUsernameAndPassword(String username, String password) {
		productCatalogue = landingPage.loginApplication(username, password);

	}

	@When("^I add product (.+) to cart$")
	public void iAddProductToCart(String productName) {
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);

	}

	@When("^Checkout (.+) and submit the order$")
	public void checkoutSubmitOrder(String productName) {

		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.verifyProductDisplay(productName);
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		confirmationPage = checkoutPage.submitOrder();

	}
	
	@Then("{string} message is displayed on ConfirmationPage")
	public void message_displayed_confirmationPage(String string) {
		
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		driver.close();
		
	}
	
	@Then("{string} message is displayed")
	public void errorValidationCheck(String strArgs1) {
		
		
		Assert.assertEquals( strArgs1, landingPage.getErrorMessage());
		driver.close();
		
	}

}

package AnkitSeleniumLearning.Tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import AnkitSeleniumLearning.TestComponents.BaseTest;
import AnkitSeleniumLearning.pageObjects.CartPage;
import AnkitSeleniumLearning.pageObjects.CheckoutPage;
import AnkitSeleniumLearning.pageObjects.ConfirmationPage;
import AnkitSeleniumLearning.pageObjects.LandingPage;
import AnkitSeleniumLearning.pageObjects.OrderPage;
import AnkitSeleniumLearning.pageObjects.ProductCatalogue;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.opentelemetry.sdk.metrics.internal.state.ObjectPool;
import junit.framework.Assert;

public class SubmitOrderTest extends BaseTest {
	// String productName = "ZARA COAT 3";
	// TODO Auto-generated method stub

	@Test(dataProvider = "getData", groups = { "Purchase" })
	public void submitOrder(HashMap<String, String> input) throws IOException {
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("productName"));
		CartPage cartPage = productCatalogue.goToCartPage();
		
		
		Boolean match = cartPage.verifyProductDisplay(input.get("productName"));
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

	}

	@Test(dataProvider = "getData", dependsOnMethods = "submitOrder")
	public void orderHistory(HashMap<String, String> input) {
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		OrderPage orderPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(orderPage.verifyOrderDisplay(input.get("productName")));

	}

	

	@DataProvider
	public Object[][] getData() throws IOException {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", "newuserone@yopmail.com");
		map.put("password", "Password@1");
		map.put("productName", "ZARA COAT 3");

		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("email", "newuser122@yopmail.com");
		map1.put("password", "Password@1");
		map1.put("productName", "IPHONE 13 PRO");
		/*
		 * C://eclipse-workspace//SeleniumFrameworkDesign//src//test//java//
		 * AnkitSeleniumLearning//data//PurchaseOrder.json
		 */

		/*
		 * List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty(
		 * "user.dir"+
		 * "\\src\\test\\java\\AnkitSeleniumLearning\\data\\PurchaseOrder.json"));
		 */

		return new Object[][] { { map }, { map1 } };

	}

}

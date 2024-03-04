package AnkitSeleniumLearning.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AnkitSeleniumLearning.AbstractComponents.AbstractComponent;


public class CartPage extends AbstractComponent {

	WebDriver driver;

	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(css = ".cartSection h3")
	List<WebElement> cartProducts;

	@FindBy(css = ".totalRow button")
	WebElement checkoutEle;

	public Boolean verifyProductDisplay(String ProductName) {

		Boolean match = cartProducts.stream().anyMatch(product -> product.getText().equalsIgnoreCase(ProductName));
		return match;
	}

	public CheckoutPage goToCheckout() {
		checkoutEle.click();
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		return checkoutPage;
	}

}

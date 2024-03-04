package AnkitSeleniumLearning.TestComponents;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import AnkitSeleniumLearning.pageObjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	private static final boolean True = false;
	public WebDriver driver;
	public LandingPage landingPage;

	public WebDriver initializeDriver() throws IOException {

		Properties prop = new Properties();
		// C:\\eclipse-workspace\\SeleniumFrameworkDesign\\ -->
		// System.getProperty("user.dir")
		FileInputStream fis = new FileInputStream(
				"C:\\eclipse-workspace\\SeleniumFrameworkDesign\\src\\main\\java\\AnkitSeleniumLearning\\Resources\\GlobalData.properties");
		prop.load(fis);
		String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : prop.getProperty("browser");

		// String browserName = prop.getProperty("browser");

		if (browserName.contains("Chrome")) {
			
			ChromeOptions options = new ChromeOptions();
			
			if(browserName.contains("headless")) {
				
			options.addArguments("headless"); 
			
			}
			
			WebDriverManager.chromedriver().setup();
			
			driver = new ChromeDriver(options);
			//driver.manage().window().setSize(new Dimension(1920,1080));

		} else if (browserName.equalsIgnoreCase("Edge")) {

			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();

		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		driver.manage().window().maximize();

		return driver;
	}

	public List<HashMap<String, String>> getJsonDataToMap(String string) throws IOException {

		// Read json to string 
		String jsonContent = FileUtils.readFileToString(new File(System.getProperty(
				"C:\\eclipse-workspace\\SeleniumFrameworkDesign\\src\\test\\java\\AnkitSeleniumLearning\\data\\PurchaseOrder.json")),
				StandardCharsets.UTF_8);

		// Convert String to hashMap using jackson databind dependency
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});

		return data;
	}

	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File destination = new File(System.getProperty("C:\\Users\\howar\\Pictures\\Screenshots\newfile.png"));
		FileUtils.copyFile(source, destination);
		return System.getProperty("C:\\Users\\howar\\Pictures\\Screenshots\newfile.png");

		/*
		 * // Cast WebDriver to TakesScreenshot TakesScreenshot ts = (TakesScreenshot)
		 * driver;
		 * 
		 * // Capture the screenshot as File File source =
		 * ts.getScreenshotAs(OutputType.FILE);
		 * 
		 * // Define the destination file path and name File destination = new
		 * File("C:\\Users\\howar\\Pictures\\Screenshots\\newfile.png");
		 * 
		 * try { // Save the screenshot to the destination file
		 * FileUtils.copyFile(source, destination);
		 * System.out.println("Screenshot captured and saved successfully."); } catch
		 * (Exception e) { System.err.println("Error while capturing screenshot: " +
		 * e.getMessage()); }
		 */

	}

	@BeforeMethod(alwaysRun = True)
	public LandingPage launchApplication() throws IOException {

		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}

	@AfterMethod(alwaysRun = True)
	public void tearDown() {
		driver.close();

	}

}

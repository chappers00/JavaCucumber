package com.agilitas.example.steps;

import com.agilitas.example.GoogleResultsPage;
import com.agilitas.example.GoogleSearchSettingsPage;
import com.agilitas.example.SeleniumUtils;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import cucumber.api.java8.En;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.PageFactory;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import static org.junit.Assert.assertTrue;

/**
 * Some Cucumber step definitions for the example project
 * Created by Tom on 14/04/2017.
 */

public class MyStepdefs implements En {
    private WebDriver driver;
    private static final Logger LOGGER = LogManager.getLogger(MyStepdefs.class);

    @Before
    public void setUpDriver() throws Exception {
        String browser = System.getProperty("browser");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        if(browser==null) {
            LOGGER.info("No broswer JVM argument passed in, defaulting to Chrome");
            driver = new ChromeDriver(options);
        } else {
            switch (browser) {
                case "IE":
                    driver = new InternetExplorerDriver();
                    break;
                case "Chrome":
                    driver = new ChromeDriver(options);
                    break;
                case "Firefox":
                    driver = new FirefoxDriver();
                    break;
                default:
                    throw new UnsupportedOperationException(String.format("Sorry I don't know how to create a driver for your command line argument %s", browser));
            }
        }
    }
    @After
    public void tearDownDriver(Scenario scenario) {
        if (scenario.isFailed()) {
            // Take a screenshot...
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png"); // ... and embed it in the report.
        }
        if (driver != null) {
            driver.quit();
        }
    }

    public MyStepdefs() {
        Given("^I've opened the google home page$", () -> {
            //First we need to set the language. When tests run on a non-UK CI server you might
            //Get results in different languages which cause the test to fail
            driver.get("https://www.google.com/preferences?hl=en-GB&fg=1#languages");
            GoogleSearchSettingsPage settingsPage = PageFactory.initElements(driver, GoogleSearchSettingsPage.class);
            settingsPage.selectEnglish();
            driver.get("https://www.google.com");
            SeleniumUtils.waitForElementVisible(driver, By.cssSelector("input[name='q']"));
        });

        When("^I search for '(.*)'$", (String searchTerm) -> {
            WebElement searchBox = driver.findElement(By.cssSelector("input[name='q']"));
            searchBox.sendKeys(searchTerm);
            searchBox.sendKeys(Keys.ENTER);
            //Wait for the search results to come back
            SeleniumUtils.waitForElementVisible(driver, By.cssSelector("#resultStats"));
        });
        Then("^there should be a result titled '(.*)'$", (String expectedResultTitle) -> {
            GoogleResultsPage resultsPage = PageFactory.initElements(driver, GoogleResultsPage.class);
            assertTrue("Couldn't find expected result title in results", resultsPage.hasResultTitled( expectedResultTitle));
        });
    }
}

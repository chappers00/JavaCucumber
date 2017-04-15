package com.agilitas.example.steps;

import com.agilitas.example.SeleniumUtils;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Some Cucumber step definitions for the example project
 * Created by Tom on 14/04/2017.
 */
public class MyStepdefs implements En {
    private WebDriver driver;
    private static final Logger LOGGER = LoggerFactory.getLogger(MyStepdefs.class);

    @Before
    public void setUpDriver() {
        driver = new FirefoxDriver();
    }
    @After
    public void tearDownDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    public MyStepdefs() {
        Given("^I've opened the google home page$", () -> driver.get("https://www.google.com"));

        When("^I search for '(.*)'$", (String searchTerm) -> {
            WebElement searchBox = driver.findElement(By.cssSelector("#lst-ib"));
            searchBox.sendKeys(searchTerm);
            searchBox.sendKeys(Keys.ENTER);
            //Wait for the search results to come back
            SeleniumUtils.waitForElementVisible(driver, By.cssSelector("#resultStats"));
        });
        Then("^there should be a result titled '(.*)'$", (String expectedResultTitle) -> {
            List<WebElement> results = driver.findElements(By.xpath("//h3/a"));
            LOGGER.info("Found {} search results", results.size());
            boolean matchingTitle = false;
            for(WebElement result : results) {
                String resultTitle = result.getText();
                LOGGER.info("Looking at {}", resultTitle);
                if (resultTitle.equalsIgnoreCase(expectedResultTitle)) {
                    LOGGER.info("{} matches {}", resultTitle, expectedResultTitle);
                    matchingTitle = true;
                    break;
                }
            }
            assertTrue("Didn't get a match for "+expectedResultTitle, matchingTitle);
        });
    }
}

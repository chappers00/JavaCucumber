package com.agilitas.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class GoogleSearchSettingsPage {

    private static final Logger LOGGER = LogManager.getLogger(GoogleResultsPage.class);
    WebDriver driver;


    @FindBy(css = "#langten")
    private WebElement english;

    public GoogleSearchSettingsPage(WebDriver driver){
        this.driver = driver;
    }

    public void selectEnglish(){
        LOGGER.info("Waiting for language options to be populated");
        SeleniumUtils.waitForElementVisible(driver, By.cssSelector("#langtop"));
        LOGGER.info("Selecting English");
        english.click();
    }
}

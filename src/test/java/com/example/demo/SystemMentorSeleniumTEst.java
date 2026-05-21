package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SystemMentorSeleniumTEst {

    Logger logger = Logger.getLogger(SystemMentorSeleniumTEst.class.getName());
    static WebDriver driver;

    @BeforeAll
    static void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new org.openqa.selenium.chrome.ChromeDriver(options);
    }

    @AfterAll
    static void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Runs before EACH test - navigates to Google and handles cookie popup
    @BeforeEach
    void navigateToGoogle() {
        driver.get("https://www.google.com");

        // Handle GDPR cookie consent popup (common in Sweden)
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement acceptBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//*[text()='Accept all' or text()='Godkänn alla']")));
            acceptBtn.click();
            logger.info("*********Cookie popup accepted");
        } catch (Exception e) {
            logger.info("*********Cookie popup not shown");
        }
    }

    @Test
    void testOpenApplication() {
        logger.info("-----------Testing open application--------");

        String title = driver.getTitle();
        assertEquals("Google", title, "Page title is not 'Google'");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement searchBar = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//textarea[@name='q']")));

        assertTrue(searchBar.isDisplayed(), "Search bar is not displayed on the page");
    }

    @Test
    void testTypeSearchBar() {
        logger.info("-----------Testing search bar input--------");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Wait until search bar is clickable
        WebElement searchBar = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//textarea[@name='q']")));

        // Click and type
        searchBar.click();
        searchBar.sendKeys("Selenium");

        // Wait for value to be set
        wait.until(
                ExpectedConditions.attributeContains(searchBar, "value", "Selenium"));

        String searchBarValue = searchBar.getAttribute("value");
        assertEquals("Selenium", searchBarValue, "Search bar value is not 'Selenium'");
        searchBar.sendKeys(Keys.ENTER);
    }
}
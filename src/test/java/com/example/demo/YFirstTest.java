package com.example.demo;

import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
public class YFirstTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeEach
  public void setUp() throws Exception {
    ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new org.openqa.selenium.chrome.ChromeDriver(options);
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
  }

  @Test
  public void testFirst() throws Exception {
    driver.get(baseUrl);
    try {
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
      WebElement acceptBtn = wait.until(
          ExpectedConditions.elementToBeClickable(
              By.xpath("//*[text()='Accept all' or text()='Godkänn alla']")));
      acceptBtn.click();
    } catch (Exception e) {
    }
    driver.findElement(By.xpath("//textarea[@name='q']")).click();
    driver.findElement(By.xpath("//textarea[@name='q']")).clear();
    driver.findElement(By.xpath("//textarea[@name='q']")).sendKeys("testing on Katalon in FF");
    driver.findElement(By.xpath("//textarea[@name='q']")).sendKeys(Keys.ENTER);
    Thread.sleep(3003);

  }

  @AfterEach
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      Assertions.fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}

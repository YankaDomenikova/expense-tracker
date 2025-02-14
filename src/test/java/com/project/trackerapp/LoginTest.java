package com.project.trackerapp;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginTest {
	private WebDriver driver;
	private Map<String, Object> vars;
	JavascriptExecutor js;

	@Before
	public void setUp() {
		driver = new FirefoxDriver();
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();
	}
	
	@After
		public void tearDown() {
		driver.quit();
	}

	@Test
	public void login() {
		driver.get("http://localhost:8080/ExpenseTracker/");
		driver.findElement(By.name("username")).click();
		driver.findElement(By.name("username")).sendKeys("yankadomenikova");
		driver.findElement(By.id("password")).click();
		driver.findElement(By.id("password")).sendKeys("111111");
		driver.findElement(By.cssSelector(".button-main")).click();

		String expectedUrl = "http://localhost:8080/ExpenseTracker/dashboard";
		String actualUrl = driver.getCurrentUrl();
		assertEquals("URL адресът не е променен към таблото за управление", expectedUrl, actualUrl);
    
 	}
}

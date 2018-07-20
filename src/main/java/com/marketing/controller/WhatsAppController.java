package com.marketing.controller;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Configuration
public class WhatsAppController {

	@Value("${users}")
	private String users;

	@Value("${message}")
	private String message;

	@GetMapping(value = "/whatsapp")
	public ResponseEntity<Void> getAllEquipments() throws Exception {
		// create a Chrome Web Driver

		String os = System.getProperty("os.name").toLowerCase();
		System.out.println(message);

		if (os.contains("mac")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/chromedriver");
		} else {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\chromedriver.exe");
		}

		WebDriver driver = new ChromeDriver();
		// open the browser and go to open google.com
		/*
		 * driver.get("https://www.google.com");
		 * 
		 * 
		 * driver.findElement(By.id("lst-ib")).sendKeys("Selenium");
		 * driver.findElement(By.name("btnK")).click();
		 * driver.manage().window().maximize();
		 * 
		 * // get the number of pages int size =
		 * driver.findElements(By.cssSelector("[valign='top'] > td")).size(); for (int j
		 * = 1; j < size; j++) { if (j > 1) {// we don't need to navigate to the first
		 * page driver.findElement(By.cssSelector("[aria-label='Page " + j +
		 * "']")).click(); // navigate to page number // j }
		 * 
		 * String pagesearch = driver.getCurrentUrl();
		 * 
		 * List<WebElement> findElements =
		 * driver.findElements(By.xpath("//*[@id='rso']//h3/a"));
		 * System.out.println(findElements.size());
		 * 
		 * for (int i = 0; i < findElements.size(); i++) { findElements =
		 * driver.findElements(By.xpath("//*[@id='rso']//h3/a"));
		 * findElements.get(i).click();
		 * 
		 * driver.navigate().to(pagesearch); JavascriptExecutor jse =
		 * (JavascriptExecutor) driver; // Scroll vertically downward by 250 pixels
		 * jse.executeScript("window.scrollBy(0,250)", ""); } }
		 */
		// driver.findElement(By.cssSelector("Body")).sendKeys(Keys.CONTROL+"t");
		driver.get("https://web.whatsapp.com");
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
		for (String user : users.split(",")) {
			driver.findElement(By.xpath("//input[@title='Search or start new chat']")).clear();
			driver.findElement(By.xpath("//input[@title='Search or start new chat']")).sendKeys(user);
			driver.findElement(By.xpath("//span[@class='matched-text']")).click();
			driver.findElement(By.xpath("//div[@class='_2S1VP copyable-text selectable-text']")).sendKeys(message);
			driver.findElement(By.xpath("//div[@class='_2S1VP copyable-text selectable-text']")).sendKeys(Keys.ENTER);
		}
		driver.findElement(By.xpath("//span//span[@data-icon='menu']")).click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@title='Log out']")).click();
		return new ResponseEntity<Void>(HttpStatus.OK);

	}
}
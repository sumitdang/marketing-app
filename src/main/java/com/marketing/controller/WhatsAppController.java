package com.marketing.controller;

import java.util.Arrays;
import java.util.List;
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

import com.marketing.utilities.Constants;
import com.marketing.utilities.TextUtils;

@Controller
@Configuration
public class WhatsAppController {

	@Value("${users}")
	private String users;

	@Value("${numbers}")
	private String numbers;

	@Value("${message}")
	private String message;

	@Value("${series}")
	private String series;

	@Value("${skipnumbers}")
	private String skipNumbers;

	@GetMapping(value = "/whatsapp")
	public ResponseEntity<Void> sendWhatsapp() throws Exception {
		// create a Chrome Web Driver

		String os = System.getProperty("os.name").toLowerCase();
		System.out.println(message);

		if (os.contains("mac")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/chromedriver");
		} else {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\chromedriver.exe");
		}

		WebDriver driver = new ChromeDriver();

		driver.get("https://web.whatsapp.com");
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
		for (String user : users.split(Constants.COMMA)) {
			driver.findElement(By.xpath("//input[@title='Search or start new chat']")).clear();
			driver.findElement(By.xpath("//input[@title='Search or start new chat']")).sendKeys(user);
			// driver.findElement(By.xpath("//span[@class='matched-text']")).click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(By.xpath("//div[contains(text(),'Chats')]")).click();
			driver.findElement(By.xpath("//div[@class='_2S1VP copyable-text selectable-text']")).sendKeys(message);
			driver.findElement(By.xpath("//div[@class='_2S1VP copyable-text selectable-text']")).sendKeys(Keys.ENTER);
		}

		List<String> skipNumbersList = Arrays.asList(skipNumbers.split(Constants.COMMA));

		// Code for series number send
		for (String serie : series.split(",")) {
			for (int i = 1; i <= 99999; i++) {
				String number = TextUtils.generatePhoneNumber(serie, String.valueOf(i));
				for (String skip : skipNumbersList) {
					if (number.contains(skip)) {
						System.out.println(number + " is skipped");
					}
				}
			}
		}

		driver.findElement(By.xpath("//span//span[@data-icon='menu']")).click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@title='Log out']")).click();
		return new ResponseEntity<Void>(HttpStatus.OK);

	}

	@GetMapping(value = "/message")
	public ResponseEntity<Void> sendMessage() throws Exception {
		// create a Chrome Web Driver

		String os = System.getProperty("os.name").toLowerCase();
		System.out.println(message);

		if (os.contains("mac")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/chromedriver");
		} else {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\chromedriver.exe");
		}

		WebDriver driver = new ChromeDriver();
		driver.get("https://messages.android.com/");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@aria-label='New conversation']")).click();
		Boolean firstElement = true;
		for (String number : numbers.split(Constants.COMMA)) {
			if (firstElement) {
				driver.findElement(By.xpath("//input[@type='text']")).sendKeys(number);
				driver.findElement(By.xpath("//input[@type='text']")).sendKeys(Keys.ENTER);
				firstElement = false;
			} else {
				driver.findElement(By.xpath("//span[contains(text(),'Add more people')]")).sendKeys(number);
				driver.findElement(By.xpath("//span[contains(text(),'Add more people')]")).sendKeys(Keys.ENTER);
			}
		}
		driver.findElement(By.xpath("//div[starts-with(@aria-label,'Type a text message to')]")).sendKeys(message);
		driver.findElement(By.xpath("//div[starts-with(@aria-label,'Type a text message to')]")).sendKeys(Keys.ENTER);
		return new ResponseEntity<Void>(HttpStatus.OK);

	}
}
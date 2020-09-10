package Pages;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.WebDriverHelper;
import utils.logHelper;

public class Core {
	private final String className = this.getClass().getSimpleName();
	private  logHelper logger=new logHelper();
	private final WebDriverHelper webDriver = new WebDriverHelper();
	private  WebDriver driver;
	
	public boolean gotoApplication(String url) {
		try {
	
			driver=webDriver.createWebdriverInstance(url);
			if (driver==null) {
				logger.print(className,"Error: Unable to launch browser. Hence stopping the test execution");
				return false;
			}
		}catch(Exception ex){
				logger.print(className, "Fatal: "+ex);
				return false;
			}
		return true;
	}
	
	public boolean bookFlight(String departure,String destination) {
		try {

			WebElement departureCity=driver.findElement(By.name("fromPort"));
			webDriver.select(departureCity, "byvisibletext", departure);			
			WebElement destinationCity=driver.findElement(By.name("toPort"));
			webDriver.select(destinationCity, "byvisibletext", destination);
			
			
//			WebElement departureCity=driver.findElement(By.name("fromPort"));
//			webDriver.select(departureCity, "byValue", departure);			
//			WebElement destinationCity=driver.findElement(By.name("toPort"));
//			webDriver.select(destinationCity, "byValue", destination);	
			
			driver.findElement(By.xpath("//input[@value='Find Flights']")).click();
		}catch(Exception ex){
			logger.print(className, "Fatal: "+ex);
			return false;
		}
		return true;
		
	}
	
	public boolean chooseFlight(String flightName) {
		try {
			String chooseFlightDynamic="//form[@name='%s']/parent::tr/td/input[@value='Choose This Flight']";
			String chooseFlight=String.format(chooseFlightDynamic,flightName);
			
			if (!webDriver.waitForExist(chooseFlight, 20)) {
				logger.print(className,"Error: chooseFlight element not exist");
				return false;
			}
			
			driver.findElement(By.xpath(chooseFlight)).click();
		}catch(Exception ex){
			logger.print(className, "Fatal: "+ex);
			return false;
		}
		return true;
		
	}
	
	public boolean addPassengerDetails(HashMap<String,String> passengerDetails) {
		try {
			String purchaseFlight="//form[contains(@action,'confirmation')]//div[@class='controls']/input[@value='Purchase Flight']";
			if (!webDriver.waitForExist(purchaseFlight, 20)) {
				logger.print(className,"Error: addPassengerDetails - Purchase Flight element not exist");
				return false;
			}	
			driver.findElement(By.id("inputName")).sendKeys(passengerDetails.get("inputName"));
			driver.findElement(By.id("address")).sendKeys(passengerDetails.get("address"));
			driver.findElement(By.id("city")).sendKeys(passengerDetails.get("city"));
			driver.findElement(By.id("state")).sendKeys(passengerDetails.get("state"));
			driver.findElement(By.id("zipCode")).sendKeys(passengerDetails.get("zipCode"));
			webDriver.select(driver.findElement(By.id("cardType")), "byvisibletext", passengerDetails.get("cardType"));	
			driver.findElement(By.id("creditCardNumber")).sendKeys(passengerDetails.get("creditCardNumber"));
			driver.findElement(By.id("creditCardMonth")).sendKeys(passengerDetails.get("creditCardMonth"));
			driver.findElement(By.id("creditCardYear")).sendKeys(passengerDetails.get("creditCardYear"));
			driver.findElement(By.id("nameOnCard")).sendKeys(passengerDetails.get("nameOnCard"));
			
			driver.findElement(By.xpath(purchaseFlight)).click();	
			
		}catch(Exception ex){
			logger.print(className, "Fatal: "+ex);
			return false;
		}
		return true;
		
	}
	
	public boolean validateTicketBooking(String expectedBookingMessage) {
		try {
			String confId="//table//td[text()='Id']//following-sibling::td";
			if (!webDriver.waitForExist(confId, 20)) {
				logger.print(className,"Error: Unable to get the purchase completeion meesage");
				return false;
			}
			
			String actualBookingMessage=driver.findElement(By.tagName("h1")).getText();
			String confirmationId=driver.findElement(By.xpath(confId)).getText();
			if (expectedBookingMessage.equalsIgnoreCase(actualBookingMessage)) {
				logger.print(className,"Info: Passed- Purchase Completed with id " + confirmationId);
			}else {
				logger.print(className,"Error: Failed- Purchase not Completed");
				return false;
			}
			
		}catch(Exception ex){
			logger.print(className, "Fatal: "+ex);
			return false;
		}
		return true;
		
	}

}

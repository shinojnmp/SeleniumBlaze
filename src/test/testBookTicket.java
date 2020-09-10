package test;

import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import Pages.Core;
import utils.WebDriverHelper;
import utils.logHelper;

public class testBookTicket {
	private final String className = this.getClass().getSimpleName();
	private static  logHelper logger=new logHelper();
	private final WebDriverHelper webDriver = new WebDriverHelper();
	private final Core bdCore = new Core();
	
	WebDriver driver;
	public boolean test() {
		try {
			String url="http://blazedemo.com/";
			
			String departure="Boston";
			String destination="London";
			
			String flightName="VA43";
			
			HashMap<String,String> passengerDetails=new HashMap<>();
			passengerDetails.put("inputName", "Shinoj");
			passengerDetails.put("address", "Shinoj Address");
			passengerDetails.put("city", "Boston City");
			passengerDetails.put("state", "Boston");
			passengerDetails.put("zipCode", "02215");
			passengerDetails.put("cardType", "American Express");
			passengerDetails.put("creditCardNumber", "1111111111111111111");
			passengerDetails.put("creditCardMonth", "10");
			passengerDetails.put("creditCardYear", "2020");
			passengerDetails.put("nameOnCard", "Shinoj");
			
			String expectedBookingMessage="Thank you for your purchase today!";
			
			
			if (bdCore.gotoApplication(url)) {
				logger.print(className,"Info: gotoApplication completed");
			}else {
				logger.print(className,"Error: gotoApplication not completed");
				return false;
			}
			
			if (bdCore.bookFlight(departure, destination)) {
				logger.print(className,"Info: bookFlight completed");
			}else {
				logger.print(className,"Error: bookFlight not completed");
				return false;
			}
			
			if (bdCore.chooseFlight(flightName)) {
				logger.print(className,"Info: chooseFlight completed");
			}else {
				logger.print(className,"Error: chooseFlight not completed");
				return false;
			}
			
			if (bdCore.addPassengerDetails(passengerDetails)) {
				logger.print(className,"Info: addPassengerDetails completed");
			}else {
				logger.print(className,"Error: addPassengerDetails not completed");
				return false;
			}
			
			if (bdCore.validateTicketBooking(expectedBookingMessage)) {
				logger.print(className,"Info: validateTicketBooking completed");
			}else {
				logger.print(className,"Error: validateTicketBooking not completed");
				return false;
			}
			
		}catch(Exception ex){
			logger.print(className, "Fatal: "+ex);
		}finally {
			webDriver.closeAllBrowsers();
		}
		return true;
	}

	public static void main(String[] args) {	
		testBookTicket objMain=new testBookTicket();
		if (objMain.test()) {
			logger.print(objMain.className,"Info: TestCase Passed!");
		}else {
			logger.print(objMain.className,"Error: TestCase Failed!");
		}
	}
}

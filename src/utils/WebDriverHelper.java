package utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverHelper {
	private final String className = this.getClass().getSimpleName();
	public static WebDriver driver = null;
	private  logHelper logger=new logHelper();
	
	
	public WebDriver createWebdriverInstance(String url) {
		try {
			String chromeDriverPath="C:\\selenium\\Drivers\\chromedriver.exe";
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			
			driver=new ChromeDriver();
			driver.get(url);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception ex) {
			logger.print(className, "Fatal: "+ex);
            return driver;
        }
		return driver;
	}
	
	
	public boolean select(WebElement element,String SelectOption,String selectValue) {
        try {
        	Select iSelect = new Select(element);

            switch (SelectOption.toLowerCase().trim()) {
                case "byvisibletext":
                    iSelect.selectByVisibleText(selectValue);
                    break;
                case "byvalue":
                    iSelect.selectByValue(selectValue);
                case "byIndex":
                    iSelect.selectByIndex(Integer.parseInt(selectValue));
                default:
                    return false;
            }
        } catch (Exception ex) {
            logger.print(className, "Fatal: "+ex);
        }
        return true;
    }
	
	public boolean closeAllBrowsers() {
        try {
            Thread.sleep(5000);
            driver.close();

            driver.quit();
            driver = null;
        } catch (Exception ex) {
        	logger.print(className, "Fatal: "+ex);
            return false;
        } 
        return true;
    }
	
	 public boolean waitForExist(String xpathElementLocator, long timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathElementLocator)));
        } catch (TimeoutException ex) {
        	logger.print(className, "Fatal: "+ex);
        return false;
	    } catch (Exception ex) {
	    	logger.print(className, "Fatal: "+ex);
	        return false;
	    } 
	    return true;
	}
	
}

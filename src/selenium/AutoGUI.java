package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AutoGUI {

	private WebDriver driver;
	private WebElement element;
	
	AutoGUI (){
		driver = new ChromeDriver();
	}
	
	public WebDriver getWebDriver(){
		return driver;
	}
	
	public WebElement getWebElement(){
		return element;
	}
	
	public void goToURL(String url){
		driver.get(url);
	}
	
	public void findElementByName(String name){
		element = driver.findElement(By.name(name));
	}
	
	public void findElementById(String id){
		element = driver.findElement(By.id(id));
	}
	
	public void findElementByLT(String lt){
		element = driver.findElement(By.linkText(lt));
	}
	public void findElementByCSS(String css){
		element = driver.findElement(By.cssSelector(css));
	}
	public void findElementByXpath(String xp){
		element = driver.findElement(By.xpath(xp));
	}
	
	
	public void enterText(String text){
		
	}
	
}

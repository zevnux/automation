package selenium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class AutoGUI {

	private static WebDriver driver = new ChromeDriver();
	private WebElement element;
			
	public void goToURL(String url){
		driver.get(url);
	}

	/**
	 * Several ways to obtain the handle on the element
	 */
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
	
	public List<List<String>> getTableById(String id){
		List<List<String>> table = new ArrayList<List<String>>();
		element = driver.findElement(By.id((id)));
		List<WebElement> records = element.findElements(By.xpath("id('" + id + "')/tbody/tr"));
		for (WebElement e : records){
			String rawRow = e.getText();
			String[] row = rawRow.split(" ");
			table.add(Arrays.asList(row));
		}
		return table;
	}
	
	public void click(){
		element.click();
	}
	public void enterText(String text){
		element.sendKeys(text);
	}
	public void submit(){
		element.submit();
	}
	public void pressEnter(){
		element.sendKeys(Keys.RETURN);
	}
	
	public static void wait (int ms){
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}

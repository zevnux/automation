package automation;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;


public class Execute {

	public static List<String[]> data = new ArrayList<String[]>();
	public static List<String> keywords = new ArrayList<String>();
	public static List<String> parameters = new ArrayList<String>();
	
	public static void main(String[] args) {
		
		CSVReader reader;

		
		File file = new File("ext/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		
		try {
			reader = new CSVReader(new FileReader("config.csv"));
			data = reader.readAll();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		setParameters();
		
		for (String keyword : keywords){
			try{
				Class<?> keyClass = Class.forName("automation." + keyword);
				Object keyObject = keyClass.newInstance();
				Method execute = keyClass.getDeclaredMethod("execute");
				execute.invoke(keyObject, (Object[])null);
			} catch (Exception e){
				e.printStackTrace();
			}
			
		}

	}
	
	public static void setParameters(){
		for (String[] testStep : data){
			keywords.add(testStep[0]);
			for (int i = 1; i < testStep.length; i++){
				parameters.add(testStep[i]);
			}
		}
	}
	

}

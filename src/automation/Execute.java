package automation;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class Execute {
	
	public static final int globalDelay = 500;

	public static List<String[]> data = new ArrayList<String[]>();
	public static List<String> keywords = new ArrayList<String>();
	public static List<String> parameters = new ArrayList<String>();
	public static CSVWriter writer;
	
	public static void main(String[] args) {
		
		CSVReader reader;
		
		File file = new File("ext/chromedriver");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		
		try {
			reader = new CSVReader(new FileReader("config.csv"));
			writer = new CSVWriter(new FileWriter("results.csv"), ',', CSVWriter.NO_QUOTE_CHARACTER);
			data = reader.readAll();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		setParameters();
		
		int stepNum = 0;
		for (String keyword : keywords){
			if (keyword.equals("")){	
				continue;
			}
			System.out.println("\n" + stepNum + ". The next action is: " + keyword);
			try{
				Class<?> keyClass = Class.forName("automation." + keyword);
				Object keyObject = keyClass.newInstance();
				Method execute = keyClass.getDeclaredMethod("execute");
				execute.invoke(keyObject, (Object[])null);
			} catch (Exception e){
				e.printStackTrace();
			}
			
			stepNum++;
		}
		
		System.out.println("\nCompleted Execution, now closing");
		

		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
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

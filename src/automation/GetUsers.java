package automation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import selenium.AutoGUI;

public class GetUsers {
	
	private static AutoGUI engine = new AutoGUI();
	private static List<String> expectedUsers;
	
	public static void execute(){
		setParameters();
		AutoGUI.wait(1000);
		List<String> foundUsers;
		engine.findElementByLT("View registered users");
		engine.click();
		foundUsers = getUsersOnPage();
		String[] result = {GetUsers.class.getSimpleName(), String.valueOf(verifyUsers(foundUsers))};
		Execute.writer.writeNext(result);
		AutoGUI.wait(1000);
		engine.findElementByLT("Home Page");
		engine.click();
	}

	public static void setParameters (){
		String rawUsers = Execute.parameters.remove(0);
		expectedUsers = Arrays.asList(rawUsers.split(";"));
	}
	
	private static boolean verifyUsers(List<String> actualUsers){
		System.out.println("Verifying the following users appear on the page: " + expectedUsers);
		if (!expectedUsers.isEmpty()){
			for (String user : expectedUsers){
				if (actualUsers.contains(user)){
					System.out.println("Found user: " + user);
					continue;
				} else {
					System.out.println("Failed to find user: " + user + "; Verification failed.");
					return false;
				}
			}
			System.out.println("Successfully found all expected users");
			return true;
		} else {
			System.out.println("No users specifed to verify, skipping verification");
			return true;
		}
	}
	
	public static List<String> getUsersOnPage(){
		AutoGUI.wait(1000);
		System.out.println("Getting a list of users from the web page");
		List<List<String>> rawTable = engine.getTableById("users");
		List<String> users = new ArrayList<String>();
		for (List<String> row : rawTable){
			users.add(row.get(0));
		}
		System.out.println("Found the following users: " + users);
		return users;
	}
}

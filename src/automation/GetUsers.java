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
		//verifyUsers();
		foundUsers = getUsersOnPage();
		verifyUsers(foundUsers);
	}
	
	/**
	 * Users you want to verify that appear in this list
	 * @param expectedUsers
	 */
	public static void setParameters (){
		String rawUsers = Execute.parameters.remove(0);
		expectedUsers = Arrays.asList(rawUsers.split(";"));
	}
	
	private static boolean verifyUsers(List<String> actualUsers){
		if (!expectedUsers.isEmpty()){
			for (String user : expectedUsers){
				if (actualUsers.contains(user)){
					continue;
				} else {
					return false;
				}
			}
			return true;
		} else {
			return true;
		}
	}
	
	private static List<String> getUsersOnPage(){
		AutoGUI.wait(1000);
		List<List<String>> rawTable = engine.getTableById("users");
		List<String> users = new ArrayList<String>();
		for (List<String> row : rawTable){
			users.add(row.get(0));
		}	
		return users;
	}
}

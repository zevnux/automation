package automation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import selenium.AutoGUI;



public class DeleteUser {
	
	private static AutoGUI engine = new AutoGUI();
	private static List<String> usersToDelete = new ArrayList<String>();
	
	public static void execute(){
		setParameters();
		boolean deleted = false;
		System.out.println("The following users will be deleted: " + usersToDelete);
		AutoGUI.wait(Execute.globalDelay);
		engine.findElementByLT("View registered users");
		engine.click();
		for (String user : usersToDelete){
			System.out.println("Attempting to delete user: " + user);
			deleted = deleteUser(user);
		}
		String[] result = {DeleteUser.class.getSimpleName(), String.valueOf(deleted)};
		Execute.writer.writeNext(result);
		AutoGUI.wait(Execute.globalDelay);
		engine.findElementByLT("Home Page");
		engine.click();
	}

	public static void setParameters(){
		String[] rawDelete = Execute.parameters.remove(0).split(";");
		usersToDelete = Arrays.asList(rawDelete);
	}
	
	private static boolean deleteUser(String user) {
		List<String> users = VerifyUsers.getUsersOnPage();
		if (users.contains(user)){
			engine.deleteUserInTable("users", user);
			System.out.println("Successfully deleted user: " + user);
			return true;
		} else {
			System.out.println("Failed to delete user: " + user +"; Unable to find the user");
			return false;
		}
	}

}

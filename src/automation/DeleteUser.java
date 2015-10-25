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
		System.out.println("The following users will be deleted: " + usersToDelete);
		AutoGUI.wait(Execute.globalDelay);
		engine.findElementByLT("View registered users");
		engine.click();
		for (String user : usersToDelete){
			System.out.println("Attempting to delete user: " + user);
			deleteUser(user);
		}
		String[] result = {DeleteUser.class.getSimpleName(), String.valueOf(verifyDeletedUsers())};
		Execute.writer.writeNext(result);
		AutoGUI.wait(Execute.globalDelay);
		engine.findElementByLT("Home Page");
		engine.click();
	}

	private static boolean verifyDeletedUsers() {
		System.out.println("Verifying the following users do not appear on the page: " + usersToDelete);
		List<String> users = GetUsers.getUsersOnPage();
		for (String user : usersToDelete){
			if (!users.contains(user)){
				System.out.println("Verified this user does not appear: " + user);
				continue;
			} else {
				System.out.println("Found the user: " + user + "; Verification failed.");
				return false;
			}
		}
		System.out.println("Successfully verified all users deleted");
		return true;		
	}

	public static void setParameters(){
		String[] rawDelete = Execute.parameters.remove(0).split(";");
		usersToDelete = Arrays.asList(rawDelete);
	}
	
	private static void deleteUser(String user) {
		List<String> users = GetUsers.getUsersOnPage();
		if (users.contains(user)){
			engine.deleteUserInTable("users", user);
			System.out.println("Successfully deleted user: " + user);
		} else {
			System.out.println("Failed to delete user: " + user +"; Unable to find the user");
		}
	}

}

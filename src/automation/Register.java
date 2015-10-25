package automation;

import selenium.AutoGUI;

public class Register {
	
	private static String username;
	private static String password;
	private static String expectedMessage;
	private static AutoGUI engine = new AutoGUI();

	public static void execute(){
		setParameters();
		System.out.println("Attempting to go to the register page");
		AutoGUI.wait(Execute.globalDelay);
		engine.findElementByLT("Register now!");
		engine.click();
		System.out.println("Attempting to fill out form with the following parameters; Username: " + username + "; Password: " + password);
		fillInRegistration();
		engine.findElementByName("commit");
		engine.click();
		AutoGUI.wait(Execute.globalDelay);
		String[] result = {Register.class.getSimpleName(), String.valueOf(verifyConfirmation())};
		Execute.writer.writeNext(result);
		engine.findElementByLT("Back");
		engine.click();
		AutoGUI.wait(Execute.globalDelay);
		engine.findElementByLT("Home Page");
		engine.click();
		
	}
	
	public static void setParameters(){
		username = Execute.parameters.remove(0);
		password = Execute.parameters.remove(0);
		expectedMessage = Execute.parameters.remove(0);
	}
	
	private static void fillInRegistration(){
		AutoGUI.wait(Execute.globalDelay);
		engine.findElementById("registration_Username");
		engine.enterText(username);
		AutoGUI.wait(Execute.globalDelay);
		engine.findElementById("registration_Password");
		engine.enterText(password);
		AutoGUI.wait(Execute.globalDelay);
	}
	
	private static boolean verifyConfirmation(){
		String message;
		System.out.println("Verifying the confirmation message");
		try {
			engine.findElementById("notice");
			message = engine.getText();
		}catch (Exception e){
			engine.findElementById("error_explanation");
			message = engine.getText().replaceAll("(\\r|\\n)", "");
		}
		if (message.equalsIgnoreCase(expectedMessage)){
			System.out.println("Actual and expected message the same: " + message);
			return true;
		} else {
			System.out.println("Failed to verify the message. Expected: " + expectedMessage + "; Actual: " + message);
			return false;
		}
	}
}

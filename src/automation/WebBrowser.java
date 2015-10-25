package automation;

import selenium.AutoGUI;

public class WebBrowser {
	
	private static String url;
	private static AutoGUI engine = new AutoGUI();
	
	public static void execute(){
		setParameters();
		if (url.equalsIgnoreCase("Shutdown")){
			System.out.println("Shutting down the web browser");
			engine.closeWebBrowser();
		} else {
			System.out.println("Going to open the following page: " + url);
			engine.goToURL(url);
		}
	}
	
	public static void setParameters(){
		url = Execute.parameters.remove(0);
	}
	
}

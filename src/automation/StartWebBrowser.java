package automation;

import selenium.AutoGUI;

public class StartWebBrowser {
	
	private static String url;
	private static AutoGUI engine = new AutoGUI();
	
	public static void execute(){
		setParameters();
		engine.goToURL(url);
	}
	
	public static void setParameters(){
		url = Execute.parameters.remove(0);
	}
	
}

package automation;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import selenium.AutoGUI;

public class SQLVerifier {
	
	private static Connection conn = null;
	private static String table;
	private static String column;
	private static String value;
	private static String expectedCount;
	private static String jdbcConnection;
	private static String dbUsername;
	private static String dbPassword;
	
	private static ResultSet result = null;
	
	public static void execute(){
		setParameters();
		
		List<String> queryParams = Arrays.asList(table, column, value);
		int expected = Integer.parseInt(expectedCount);
		
		try {
			Scanner fileScan = new Scanner(new File("db"));
			String rawDbInfo = fileScan.next();
			String [] dbInfo = rawDbInfo.split(";");
			jdbcConnection = "jdbc:mysql:" + dbInfo[0];
			dbUsername = dbInfo[1];
			dbPassword = dbInfo[2];
			loadDriver();
			System.out.println("Connected to the database");
			
			runQuery(queryParams);
			String[] result = {SQLVerifier.class.getSimpleName(), String.valueOf(verifyResult(expected))};
			Execute.writer.writeNext(result);
			conn.close();
			fileScan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static boolean verifyResult(int expected) {
		try{ 
			result.next();
			int actual = result.getInt(1);
			if (expected == actual){
				System.out.println("Verified the number of records with " + column + " = " + value + " is " + actual);
				return true;
			} else {
				System.out.println("Failed to verify the number of records with " + column + " = " + value);
				System.out.println("Actual: " + actual + "; Expected: " + expected);
				return false;
			}
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
		
	}

	private static void runQuery(List<String> parameters) {
		try {
			String rawQuery = "SELECT count(*) FROM " + table + " WHERE " + column + " = ?";
			PreparedStatement query = conn.prepareStatement(rawQuery);
			query.setString(1, value);
			System.out.println("Executing the following statement on the database: " + query.toString().substring(query.toString().indexOf("SELECT")));
			AutoGUI.wait(1000);
			result = query.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public static void setParameters(){
		table = Execute.parameters.remove(0);
		column = Execute.parameters.remove(0);
		value = Execute.parameters.remove(0);
		expectedCount = Execute.parameters.remove(0);
	}
	
	private static void loadDriver(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(jdbcConnection,dbUsername,dbPassword);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		
	}
}

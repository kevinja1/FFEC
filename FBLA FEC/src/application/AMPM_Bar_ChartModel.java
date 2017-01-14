package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AMPM_Bar_ChartModel {
	private int numAM;
	private int numPM;
	
	Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    
	public int calcNumAM(){
		
		String sqlQuery = "select * FROM Customers_Attendance";
		connection = SqliteConnection.Connector();
	    try {
			 	statement = connection.createStatement();
			 	resultSet = statement.executeQuery(sqlQuery);
			 	
			 	while(resultSet.next()){
			 		if(resultSet.getString("AMPM").equals("AM")){			 			
			 			numAM++;
			 		}
			 	}
			 	
			 	statement.close();
			 	resultSet.close();
			 	connection.close();
			 	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	    return numAM;	
	}
	
	public int calcNumPM(){
		
		String sqlQuery = "select * FROM Customers_Attendance";
		connection = SqliteConnection.Connector();
	    try {
			 	statement = connection.createStatement();
			 	resultSet = statement.executeQuery(sqlQuery);
			 	
			 	while(resultSet.next()){
			 		if(resultSet.getString("AMPM").equals("PM")){
			 			numPM++;
			 		}
			 	}
			 	
			 	statement.close();
			 	resultSet.close();
			 	connection.close();
			    
			 	 	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	    return numPM;	
	}
	



  
}
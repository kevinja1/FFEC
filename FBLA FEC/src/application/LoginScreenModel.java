package application;
import java.sql.*;

public class LoginScreenModel {
	
	Connection connection;
	
	public LoginScreenModel(){
		connection = SqliteConnection.Connector();
		if(connection == null){
			System.exit(1);
		}
	}
	
	public boolean isDbConnected(){
		try{
			return !connection.isClosed();
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Connection getConnection(){
		return connection;
	}
	
	public boolean isLogin(String user, String pass) throws SQLException{
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from User_Logins where Username = ? and Password = ?";
		
		try{
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user);
			preparedStatement.setString(2, pass);
			
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()){
				return true;
			}
			else{
				return false;
			}
			
		} 
		catch (Exception e){
			return false;
		} 
		finally{
			preparedStatement.close();
			resultSet.close();
			//connection.close();
		}
	}
}

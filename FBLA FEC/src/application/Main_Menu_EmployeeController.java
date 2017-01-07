package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.RadioButton;

import javafx.scene.control.DatePicker;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class Main_Menu_EmployeeController implements Initializable {
	
	public Main_Menu_EmployeeModel Employee_Table_Screen = new Main_Menu_EmployeeModel();
	
	@FXML
	private Button txtAdd;
	@FXML
	private Button MainMenuSaveButton;
	@FXML
	private Button MainMenuClearButton;
	@FXML
	private TextField txtFirst_Name;
	@FXML
	private TextField txtLast_Name;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtPhone;
	@FXML
	private TextField txtAddress;
	@FXML
	private TextField txtSearch;
	@FXML
	private DatePicker dtDOB;
	@FXML
	private TextField txtID;
	@FXML
	private TableView<Main_Menu_EmployeeModel> TableEmployees;
	@FXML
	private TableColumn<Main_Menu_EmployeeModel, String> EmployeesFirst_Name;
	@FXML
	private TableColumn<Main_Menu_EmployeeModel, String> EmployeesLast_Name;
	@FXML
	private TableColumn<Main_Menu_EmployeeModel, String> EmployeesID;
	@FXML
	private TableColumn<Main_Menu_EmployeeModel, String> EmployeesEmail;
	
	
	private boolean isMainMenuAddNewButtonClick;
	private boolean isMainMenuEditButtonClick;
	
	Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    
    private String temp;
    
	
    @Override
    public void initialize(URL location, ResourceBundle resources) {


       
        //Get data from adminTableData ObservableList and set this data on JavaFX table column

        EmployeesFirst_Name.setCellValueFactory(new PropertyValueFactory<Main_Menu_EmployeeModel,String>("EmployeesFirst_Name")); 
        EmployeesLast_Name.setCellValueFactory(new PropertyValueFactory<Main_Menu_EmployeeModel,String>("EmployeesLast_Name"));
        EmployeesID.setCellValueFactory(new PropertyValueFactory<Main_Menu_EmployeeModel,String>("EmployeesID"));
        EmployeesEmail.setCellValueFactory(new PropertyValueFactory<Main_Menu_EmployeeModel,String>("EmployeesEmail"));
        
        TableEmployees.setItems(Employee_Table_Screen.getDataFromSqlAndAddToObservableList("SELECT * FROM EMPLOYEES"));

    }
	
	@FXML
    private void setMainMenuAddNewButtonClick(Event event){
        MainMenuSetAllEnable();
        isMainMenuAddNewButtonClick = true;
    }
	
	private void MainMenuSetAllEnable(){
        txtFirst_Name.setDisable(false);
        txtLast_Name.setDisable(false);
        txtID.setDisable(false);
        txtPhone.setDisable(false);
        txtAddress.setDisable(false);
        txtEmail.setDisable(false);
        dtDOB.setDisable(false);
        

        MainMenuSaveButton.setDisable(false);
        MainMenuClearButton.setDisable(false);

    }
	
	private void MainMenuSetAllDisable(){
        txtFirst_Name.setDisable(true);
        txtLast_Name.setDisable(true);
        txtID.setDisable(true);
        txtPhone.setDisable(true);
        txtAddress.setDisable(true);
        txtEmail.setDisable(true);
        dtDOB.setDisable(true);
        

        MainMenuSaveButton.setDisable(true);
        MainMenuClearButton.setDisable(true);

    }
	
	 private void MainMenuSetAllClear(){
		 	txtFirst_Name.clear();
	        txtLast_Name.clear();
	        txtID.clear();
	        txtPhone.clear();
	        txtAddress.clear();
	        txtEmail.clear();
	        dtDOB.setValue(null);
	       	        
	    }
	 
	 @FXML
	 private void MainMenuSetAllClear(Event event){
		 	txtFirst_Name.clear();
	        txtLast_Name.clear();
	        txtID.clear();
	        txtPhone.clear();
	        txtAddress.clear();
	        txtEmail.clear();
	        dtDOB.setValue(null);
	       	        
	    }
	
	 @FXML
	    private void setMainMenuSaveButtonClick(Event event){
	        try{	       
	            connection = SqliteConnection.Connector();
	            statement = connection.createStatement();
	        
	            if(isMainMenuAddNewButtonClick){
	                int rowsAffected = statement.executeUpdate("insert into`Employees` "+
	                        "(`First_Name`,`Last_Name`, ID, `Email`,`Phone`,"+
	                        "`Address`,`DOB`"+
	                        ") "+
	                        "values ('"+txtFirst_Name.getText()+"','"+txtLast_Name.getText()+"',"+txtID.getText() + ",'"+txtEmail.getText()
	                        +"','"+txtPhone.getText()
	                        +"','"+txtAddress.getText()
	                        +"','"+dtDOB.getValue()
	                       
	    
	                        +"'); ");
	           
	            }
	            else if (isMainMenuEditButtonClick){

	                int rowsAffected = statement.executeUpdate("update Employees set "+
	                        "First_Name = '"+txtFirst_Name.getText()+"',"+
	                        "Last_Name = '"+txtLast_Name.getText()+"',"+
	                        "ID = "+txtID.getText()+","+
	                        "Email = '"+txtEmail.getText()+"',"+
	                        "Phone = '"+txtPhone.getText()+"',"+
	                        "Address = '"+txtAddress.getText()+"',"+
	                        "DOB = '"+dtDOB.getValue()+
	                        "' where ID = '"+
	                        temp+"';");
	               /* if (temp.equals(txtID.getText())){
	                    statement.executeUpdate("update studentgpa set dbstudentgpaID ='"+adminTFStudentID.getText()+"' where dbStudentID = '"+ temp+"';");
	                }
	                */
	            }

	            statement.close();
	            connection.close();
	        }
	        catch (SQLException e){
	            e.printStackTrace();
	        }
	        MainMenuSetAllClear();
	        MainMenuSetAllDisable();
	        TableEmployees.setItems(Employee_Table_Screen.getDataFromSqlAndAddToObservableList("SELECT * FROM Employees;"));
	        isMainMenuEditButtonClick=false;
	        isMainMenuAddNewButtonClick = false;
	    }
	 
	 @FXML
	    private void setMainMenuEditButtonClick(Event event){
	        
	     if(TableEmployees.getSelectionModel().getSelectedItem()!=null) {
	    	 Main_Menu_EmployeeModel getSelectedRow = TableEmployees.getSelectionModel().getSelectedItem();
	        	String sqlQuery = "select * FROM Employees where ID = "+getSelectedRow.getEmployeesID()+";";
	        	 
	        try {
	        	 connection = SqliteConnection.Connector();
		         statement = connection.createStatement();
	             resultSet = statement.executeQuery(sqlQuery);
	        
	             MainMenuSetAllEnable();
	             while(resultSet.next()) {
	                 txtFirst_Name.setText(resultSet.getString("First_Name"));
	                 txtLast_Name.setText(resultSet.getString("Last_Name"));
	                 txtID.setText(resultSet.getString("ID"));
	                 txtEmail.setText(resultSet.getString("Email"));
	                 txtPhone.setText(resultSet.getString("Phone"));
	                 txtAddress.setText(resultSet.getString("Address"));
	                 //dtDOB.setValue(value);
	                 /*try {
	                    if (!(resultSet.getString("DOB").isEmpty())) {
	                        adminDPStudentDOB.setValue(LocalDate.parse(resultSet.getString("dbStudentDOB")));
	                    }
	                }
	                catch (NullPointerException e){
	                    adminDPStudentDOB.setValue(null);
	                }
	                */
	            }

	            temp = txtID.getText();
	            isMainMenuEditButtonClick = true;
	        }
	        catch (SQLException e) {
	            e.printStackTrace();
	        }

	     }
	     else{
	    	    NotificationType notificationType = NotificationType.ERROR;
	            TrayNotification tray = new TrayNotification();
	            tray.setTitle("No Employee Selected");
	            tray.setMessage("To edit, please select an Employee from the table");
	            tray.setNotificationType(notificationType);
	            tray.showAndDismiss(Duration.millis(5000));
	     }
		 		
	    }
	 
	 @FXML
	    private void setMainMenuDeleteButtonClick(Event event){
		 	if(TableEmployees.getSelectionModel().getSelectedItem()!=null){
		 		Main_Menu_EmployeeModel getSelectedRow = TableEmployees.getSelectionModel().getSelectedItem();
		        String sqlQuery = "delete from Employees where ID = '"+getSelectedRow.getEmployeesID()+"';";
		        try {
		        	connection = SqliteConnection.Connector();
			        statement = connection.createStatement();
		             
		            statement.executeUpdate(sqlQuery);
		            statement.executeUpdate("delete from Employees where ID ='"+getSelectedRow.getEmployeesID()+"';");
		            TableEmployees.setItems(Employee_Table_Screen.getDataFromSqlAndAddToObservableList("SELECT * FROM Employees;"));
		            statement.close();
		            connection.close();

		        }
		        catch (SQLException e) {
		            e.printStackTrace();
		        }


		        //adminTableView.setItems(getDataFromSqlAndAddToObservableList(sqlQuery));
		 	}
		 	else{
		 		NotificationType notificationType = NotificationType.ERROR;
	            TrayNotification tray = new TrayNotification();
	            tray.setTitle("No Employee Selected");
	            tray.setMessage("To delete, please select an Employee from the table");
	            tray.setNotificationType(notificationType);
	            tray.showAndDismiss(Duration.millis(5000));
		 	}        
	    }
	 
	 @FXML
	    private void setMainMenuSearchButtonClick(Event event){
	        String sqlQuery = "select * FROM Employees where ID = '"+txtSearch.getText()+"';";
	        TableEmployees.setItems(Employee_Table_Screen.getDataFromSqlAndAddToObservableList(sqlQuery));
	    }
	 
	 @FXML
	    private void setMainMenuRefreshButtonClick(Event event){
	        TableEmployees.setItems(Employee_Table_Screen.getDataFromSqlAndAddToObservableList("SELECT * FROM Employees;"));//sql Query
	        txtSearch.clear();
	    }

	 
	


	
}

package application;

import java.awt.Insets;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class Menu_Customer_DetailsController implements Initializable {

	public Menu_CustomerModel Customers_Table_Screen = new Menu_CustomerModel();
	
	//UI Features
	@FXML
	AnchorPane ancpane;
	
	@FXML
	private JFXTextField txtFirst_Name;
	@FXML
	private JFXTextField txtLast_Name;
	@FXML
	private JFXTextField txtEmail;
	@FXML
	private JFXTextField txtPhone;
	@FXML
	private JFXTextField txtAddress;
	@FXML
	private JFXDatePicker dtDOB;
	
	@FXML
	private Button CustomerSaveButton;
	@FXML
	private Button CustomerClearButton;
	
	@FXML
	private TextField txtSearch;
	@FXML
	private TableView<Menu_CustomerModel> TableCustomers;
	@FXML
	private TableColumn<Menu_CustomerModel, String> CustomersID;
	@FXML
	private TableColumn<Menu_CustomerModel, String> CustomersFirst_Name;
	@FXML
	private TableColumn<Menu_CustomerModel, String> CustomersLast_Name;
	@FXML
	private TableColumn<Menu_CustomerModel, String> CustomersEmail;
	
	private boolean isCustomersAddNewButtonClick;
	private boolean isCustomersEditButtonClick;

	Connection connection;
	private Statement statement;
	private ResultSet resultSet;

	private int temp;
	
	@FXML
	private JFXDrawer topDrawer;
	@FXML
	private JFXButton buttonDetails;
	@FXML
	private JFXButton buttonSchedule;
	@FXML
	private JFXButton buttonCustomer;
	@FXML
	private JFXButton buttonReports;
	@FXML
	private JFXButton buttonAbout;
	@FXML
	private JFXButton buttonExit;
	@FXML
	private HBox hbMenu;
	
	@FXML
	private AnchorPane root;
	
	public static AnchorPane rootP;
	


	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		

		CustomersFirst_Name.setCellValueFactory(new PropertyValueFactory<Menu_CustomerModel,String>("CustomersFirst_Name")); 
		CustomersLast_Name.setCellValueFactory(new PropertyValueFactory<Menu_CustomerModel,String>("CustomersLast_Name"));
		CustomersID.setCellValueFactory(new PropertyValueFactory<Menu_CustomerModel,String>("Customers_ID"));
		CustomersEmail.setCellValueFactory(new PropertyValueFactory<Menu_CustomerModel,String>("Customers_Email"));

		

		//Fills in the customers in the customer table
		TableCustomers.setItems(Customers_Table_Screen.getDataFromSqlAndAddToObservableList("SELECT * FROM Customers"));
		
		buttonDetails.setGraphic(new ImageView("application/ic_perm_identity_white_48pt.png"));
        buttonSchedule.setGraphic(new ImageView("application/ic_date_range_white_48pt.png"));
        buttonCustomer.setGraphic(new ImageView("application/ic_group_white_2x.png"));
        buttonReports.setGraphic(new ImageView("application/ic_insert_chart_white_2x.png"));
        buttonAbout.setGraphic(new ImageView("application/ic_info_outline_white_48pt.png"));
        buttonExit.setGraphic(new ImageView("application/ic_clear_white_48pt.png"));
       
        for(Node node: hbMenu.getChildren()){
    		if(node.getAccessibleText()!=null){
    			node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
    				
    				switch(node.getAccessibleText()){
    				case "Employee Details":
    					BorderPane pane;
    					try {
    						pane = FXMLLoader.load(getClass().getResource("Main_Menu_Employee.fxml"));
    						root.getChildren().setAll(pane);
    					} catch (IOException e3) {
    						// TODO Auto-generated catch block
    						e3.printStackTrace();
    					}
    					break;
    				case "Schedule Employee":
    					AnchorPane pane1;
    					try {
    						pane1 = FXMLLoader.load(getClass().getResource("Employee_Shift_Scheduler.fxml"));
    						root.getChildren().setAll(pane1);
    					} catch (IOException e1) {
    						// TODO Auto-generated catch block
    						e1.printStackTrace();
    					}
    					break;
    				case "Customer Attendance":
    					AnchorPane pane2;
    					try {
    						pane2 = FXMLLoader.load(getClass().getResource("Menu_Customer.fxml"));
    						root.getChildren().setAll(pane2);
    					} catch (IOException e2) {
    						// TODO Auto-generated catch block
    						e2.printStackTrace();
    					}
    					break;
    				case "Reports":
    				
    				case "About":
    				
    				case "Exit":
    				}
    			});
    		}
        }
	}

	//Method called when the user wishes to add a new customer
	@FXML
	private void setCustomersAddNewButtonClick(Event event){
		CustomersSetAllEnable();
		isCustomersAddNewButtonClick = true;
	}

	//Enables the text fields for users to enter information
	private void CustomersSetAllEnable(){
		txtFirst_Name.setDisable(false);
		txtLast_Name.setDisable(false);
		txtEmail.setDisable(false);
		txtPhone.setDisable(false);
		txtAddress.setDisable(false);
		dtDOB.setDisable(false);


		CustomerSaveButton.setDisable(false);
		CustomerClearButton.setDisable(false);

	}

	//Disables Customer text fields
	private void CustomersSetAllDisable(){
		txtFirst_Name.setDisable(true);
		txtLast_Name.setDisable(true);
		txtEmail.setDisable(true);
		txtPhone.setDisable(true);
		txtAddress.setDisable(true);
		dtDOB.setDisable(true);

		CustomerSaveButton.setDisable(true);
		CustomerClearButton.setDisable(true);

	}

	

	//Clear Customer Fields
	private void CustomersSetAllClear(){
		txtFirst_Name.clear();
		txtLast_Name.clear();
		txtEmail.clear();
		txtPhone.clear();
		txtAddress.clear();
		dtDOB.setValue(null);
	}

	

	//Method called when clear button is clicked
	@FXML
	private void CustomersSetAllClear(Event event){
		txtFirst_Name.clear();
		txtLast_Name.clear();
		txtEmail.clear();
		txtPhone.clear();
		txtAddress.clear();
		dtDOB.setValue(null);
	}

	//Method called when user saves a new customer
	@FXML
	private void setCustomerSaveButtonClick(Event event){
		try{	       
			if(validateFirstName() && validateLastName() && validateEmail() && validatePhone() && validateDOB() && validateAddress()){
				connection = SqliteConnection.Connector();
				statement = connection.createStatement();

				if(isCustomersAddNewButtonClick){
					isCustomersEditButtonClick = true;
					int rowsAffected = statement.executeUpdate("insert into`Customers` "+
							"(`First_Name`,`Last_Name`,`Email`,`Phone`,"+
							"`Address`,`DOB`"+
							") "+
							"values ('"+txtFirst_Name.getText()+"','"+txtLast_Name.getText()+"','"+txtEmail.getText()
							+"','"+txtPhone.getText()
							+"','"+txtAddress.getText()
							+"','"+dtDOB.getValue().toString()


							+"'); ");

				}
				else if (isCustomersEditButtonClick){
					isCustomersAddNewButtonClick = false;
					int rowsAffected = statement.executeUpdate("update Customers set "+
							"First_Name = '"+txtFirst_Name.getText()+"',"+
							"Last_Name = '"+txtLast_Name.getText()+"',"+
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

				CustomersSetAllClear();
				CustomersSetAllDisable();
				TableCustomers.setItems(Customers_Table_Screen.getDataFromSqlAndAddToObservableList("SELECT * FROM Customers;"));
				isCustomersEditButtonClick=false;
				isCustomersAddNewButtonClick = false;
			}	        	
		}
		catch (SQLException e){
			e.printStackTrace();
		}

	}

	//Loads in customer data to text field when user wishes to edit a Customer's information
	@FXML
	private void setCustomerEditButtonClick(Event event){

		if(TableCustomers.getSelectionModel().getSelectedItem()!=null) {
			Menu_CustomerModel getSelectedRow = TableCustomers.getSelectionModel().getSelectedItem();
			String sqlQuery = "select * FROM Customers where ID = "+getSelectedRow.getCustomers_ID()+";"; 
			try {
				connection = SqliteConnection.Connector();
				statement = connection.createStatement();
				resultSet = statement.executeQuery(sqlQuery);

				CustomersSetAllEnable();
				while(resultSet.next()) {
					txtFirst_Name.setText(resultSet.getString("First_Name"));
					txtLast_Name.setText(resultSet.getString("Last_Name"));
					txtEmail.setText(resultSet.getString("Email"));
					txtPhone.setText(resultSet.getString("Phone"));
					txtAddress.setText(resultSet.getString("Address"));
					dtDOB.setValue(LocalDate.parse(resultSet.getString("DOB")));
					temp = resultSet.getInt("ID");

				}


				isCustomersEditButtonClick = true;
			}
			catch (SQLException e) {
				e.printStackTrace();
			}

		}
		else{
			NotificationType notificationType = NotificationType.ERROR;
			TrayNotification tray = new TrayNotification();
			tray.setTitle("No Customer Selected");
			tray.setMessage("To edit, please select a Customer from the table");
			tray.setNotificationType(notificationType);
			tray.showAndDismiss(Duration.millis(5000));
		}

	}

	//Method called when user wishes to delete a customer
	@FXML
	private void setCustomerDeleteButtonClick(Event event){
		TableCustomers.setPlaceholder(new Label("No Customers"));	
		if(TableCustomers.getSelectionModel().getSelectedItem()!=null){
			Menu_CustomerModel getSelectedRow = TableCustomers.getSelectionModel().getSelectedItem();
			String sqlQuery = "delete from Customers where ID = '"+getSelectedRow.getCustomers_ID()+"';";
			String sqlQuery2 = "delete from Customers_Attendance where ID = '"+getSelectedRow.getCustomers_ID()+"';";

			try {
				connection = SqliteConnection.Connector();
				statement = connection.createStatement();

				statement.executeUpdate(sqlQuery);
				statement.executeUpdate("delete from Customers where ID ='"+getSelectedRow.getCustomers_ID()+"';");
				TableCustomers.setItems(Customers_Table_Screen.getDataFromSqlAndAddToObservableList("SELECT * FROM Customers;"));
				statement.close();


				statement.executeUpdate(sqlQuery2);

				
				statement.close();
				connection.close();

			}
			catch (SQLException e) {
				e.printStackTrace();
			}

		}
		else{
			NotificationType notificationType = NotificationType.ERROR;
			TrayNotification tray = new TrayNotification();
			tray.setTitle("No Customer Selected");
			tray.setMessage("To delete, please select a Customer from the table");
			tray.setNotificationType(notificationType);
			tray.showAndDismiss(Duration.millis(5000));
		}        
	}

	//Method to search for Customer by given ID
	@FXML
	private void setCustomerSearchButtonClick(Event event){
		String sqlQuery = "select * FROM Customers where ID = '"+txtSearch.getText()+"';";
		TableCustomers.setItems(Customers_Table_Screen.getDataFromSqlAndAddToObservableList(sqlQuery));
	}

	//Method called to refresh Customer Table
	@FXML
	private void setCustomerRefreshButtonClick(Event event){
		TableCustomers.setItems(Customers_Table_Screen.getDataFromSqlAndAddToObservableList("SELECT * FROM Customers;"));//sql Query
		txtSearch.clear();
	}

	//Method to search for a Customer based on typed name
	@FXML
	public void setOnSearchKeyPressed(KeyEvent event) throws IOException{
		if(txtSearch.getText()!=""){
			String sqlQuery = "select * FROM Customers where First_Name like '%"+txtSearch.getText()+"%' OR "
					+ "Last_Name like '%"+txtSearch.getText() + "%';";
			if(Customers_Table_Screen.getDataFromSqlAndAddToObservableList(sqlQuery)==null){
				TableCustomers.setPlaceholder(new Label("No Customer With Given Name"));
			}
			TableCustomers.setItems(Customers_Table_Screen.getDataFromSqlAndAddToObservableList(sqlQuery));
		}
		else{
			TableCustomers.setItems(Customers_Table_Screen.getDataFromSqlAndAddToObservableList("select * FROM Customers"));
		}
	} 

	


	//Following launch methods are to load other windows for various parts of the program
	@FXML
	private void launchScheduler(Event event) throws IOException{
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent Scheduler = FXMLLoader.load(getClass().getResource("Employee_Shift_Scheduler.fxml"));
		Scene scheduler = new Scene(Scheduler);
		Stage Schedule = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Schedule.hide();
		Schedule.setScene(scheduler);
		Schedule.setTitle("Scheduler");
		Schedule.show();
	}

	@FXML
	private void launchEmployeeMainMenu(Event event) throws IOException{
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent Main_Menu = FXMLLoader.load(getClass().getResource("Main_Menu_Employee.fxml"));
		Scene MainMenu = new Scene(Main_Menu);
		Stage mainMenu = (Stage) ((Node) event.getSource()).getScene().getWindow();
		mainMenu.hide();
		mainMenu.setScene(MainMenu);
		mainMenu.setTitle("Main Menu");
		mainMenu.show();
	}

	@FXML
	private void launchBarChart(Event event) throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("AMPM_Bar_Chart.fxml"));
		loader.load();
		Parent p = loader.getRoot();
		Stage stage = new Stage();
		stage.setScene(new Scene(p));
		stage.setTitle("All Customer Attendance Data");
		stage.show();
	}

	@FXML
	private void launchLineChart(Event event) throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Customer_Attendance_Line_Chart.fxml"));
		loader.load();
		Parent p = loader.getRoot();
		Stage stage = new Stage();
		stage.setScene(new Scene(p));
		stage.setTitle("Week Customer Attendance Data");
		stage.show();
	}

	


	//Following validate methods are to make sure the required info is provided in the correct format,
	//otherwise show an alert
	private boolean validateFirstName(){
		Pattern p = Pattern.compile("[a-zA-z]+");
		Matcher m = p.matcher(txtFirst_Name.getText());
		if(m.find() && m.group().equals(txtFirst_Name.getText())){
			return true;
		}
		else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Validate Name");
			alert.setHeaderText(null);
			alert.setContentText("Please Enter a Valid First Name");
			alert.showAndWait();
			txtFirst_Name.clear();
			txtFirst_Name.requestFocus();
			
			
			
			return false;
		}
	}


	private boolean validateLastName(){
		Pattern p = Pattern.compile("[a-zA-z]+");
		Matcher m = p.matcher(txtLast_Name.getText());
		if(m.find() && m.group().equals(txtLast_Name.getText())){
			return true;
		}
		else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Validate Name");
			alert.setHeaderText(null);
			alert.setContentText("Please Enter a Valid Last Name");
			alert.showAndWait();
			txtLast_Name.clear();
		
			txtLast_Name.requestFocus();
			
			
			return false;
		}
	}

	private boolean validateEmail(){
		Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._-]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
		Matcher m = p.matcher(txtEmail.getText());
		if(m.find() && m.group().equals(txtEmail.getText())){
			return true;
		}
		else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Validate Email");
			alert.setHeaderText(null);
			alert.setContentText("Please Enter a Valid Email");
			alert.showAndWait();
			txtEmail.clear();
	
			txtEmail.requestFocus();
			
			
			return false;
		}
	}

	private boolean validatePhone(){
		Pattern p = Pattern.compile("[0-9]{3}[-][0-9]{3}[-][0-9]{4}");
		Matcher m = p.matcher(txtPhone.getText());
		if(m.find() && m.group().equals(txtPhone.getText())){
			return true;
		}
		else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Validate Phone Number");
			alert.setHeaderText(null);
			alert.setContentText("Please Enter a Valid Phone Number (aaa-aaa-aaaa)");
			alert.showAndWait();
			txtPhone.clear();
			
			txtPhone.requestFocus();
			
			
			return false;
		}
	}

	private boolean validateDOB(){
		if(dtDOB.getValue() != null){
			return true;
		}
		else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Validate Date");
			alert.setHeaderText(null);
			alert.setContentText("Please Enter a DOB");
			alert.showAndWait();
			
			
			
			return false;
		}

	}

	private boolean validateAddress(){
		if(txtAddress.getText() != null){
			return true;
		}
		else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Validate Address");
			alert.setHeaderText(null);
			alert.setContentText("Please Enter an Address");
			alert.showAndWait();
			
			txtAddress.requestFocus();
			
			return false;
		} 
	}


	
	//Method to automatically add dashes when user types in a phone number
	@FXML
	public void setOnPhoneKeyReleased(KeyEvent event) throws IOException{
		if(txtPhone.getText().length()==3){
			txtPhone.setText(txtPhone.getText()+"-");
			txtPhone.positionCaret(4);
		}
		else if(txtPhone.getText().length()==7){
			txtPhone.setText(txtPhone.getText()+"-");
			txtPhone.positionCaret(8);
		}
		else if(txtPhone.getText().length()>12 ){
			txtPhone.setText(txtPhone.getText().substring(0,12));
			txtPhone.positionCaret(12);
		}
	}

	//Method called when a user selects a customer from the table
	@FXML
	public void setOKClicked(MouseEvent event) throws IOException{
		if(TableCustomers.getSelectionModel().getSelectedItem()!=null) {
			Menu_CustomerModel getSelectedRow = TableCustomers.getSelectionModel().getSelectedItem();
			
			AnchorPane pane;
			try {
				FXMLLoader loader = new FXMLLoader(
					    getClass().getResource(
					      "Menu_Customer_Details.fxml"
					    )
					  );

				pane = loader.load();
				Menu_CustomerController controller = 
					    loader.<Menu_CustomerController>getController();
			   controller.setCustomer(getSelectedRow.getCustomers_ID());
			   root.getChildren().setAll(pane);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		else{
			NotificationType notificationType = NotificationType.ERROR;
			TrayNotification tray = new TrayNotification();
			tray.setTitle("No Customer Selected");
			tray.setMessage("Please select a customer");
			tray.setNotificationType(notificationType);
			tray.showAndDismiss(Duration.millis(5000));
		}
	}
}

package application;

import java.awt.print.PageFormat;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import javafx.scene.control.ListView;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import javafx.scene.control.RadioButton;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterAttributes;
import javafx.print.PrinterJob;

public class Employee_Shift_SchedulerController implements Initializable {
	
	public Employee_Shift_SchedulerModel Scheduler_Table = new Employee_Shift_SchedulerModel();
	@FXML
	ToggleGroup group = new ToggleGroup();
	
	@FXML
	private DatePicker dtSchedule;
	
	@FXML
	private ListView<String> listSun;
	@FXML
	private ListView<String> listMon;
	@FXML
	private ListView<String> listTues;
	@FXML
	private ListView<String> listWed;
	@FXML
	private ListView<String> listThurs;
	@FXML
	private ListView<String> listFri;
	@FXML
	private ListView<String> listSat;
	
	@FXML
	private Label lblSun;
	@FXML
	private Label lblMon;
	@FXML
	private Label lblTues;
	@FXML
	private Label lblWed;
	@FXML
	private Label lblThurs;
	@FXML
	private Label lblFri;
	@FXML
	private Label lblSat;
	
	@FXML
	private TableView<Employee_Shift_SchedulerModel> TableEmployees;
	@FXML
	private TableColumn<Employee_Shift_SchedulerModel, String> EmployeesFirst_Name;
	@FXML
	private TableColumn<Employee_Shift_SchedulerModel, String> EmployeesLast_Name;
	@FXML
	private TableColumn<Employee_Shift_SchedulerModel, String> EmployeesID;
	@FXML
	private TextField txtSearch;
	@FXML
	private RadioButton rdAM_Shift;
	@FXML
	private RadioButton rdPM_Shift;
	@FXML
	private Button btAdd_Employee;
	@FXML
	private Button btSearch_Employee;
	@FXML
	private Button btDelete;
	@FXML
	private Button btRefresh;
	
	@FXML
	private GridPane grSchedule;

	private Label chosen;
	Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PageFormat format;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {

        EmployeesFirst_Name.setCellValueFactory(new PropertyValueFactory<Employee_Shift_SchedulerModel,String>("EmployeesFirst_Name")); 
        EmployeesLast_Name.setCellValueFactory(new PropertyValueFactory<Employee_Shift_SchedulerModel,String>("EmployeesLast_Name"));
        EmployeesID.setCellValueFactory(new PropertyValueFactory<Employee_Shift_SchedulerModel,String>("EmployeesID"));
        
       
        TableEmployees.setItems(Scheduler_Table.getDataFromSqlAndAddToObservableList("SELECT * FROM EMPLOYEES"));
        
        TableEmployees.setDisable(true);
        rdPM_Shift.setDisable(true);
        rdAM_Shift.setSelected(true);
        rdAM_Shift.setDisable(true);
        btAdd_Employee.setDisable(true);
        btSearch_Employee.setDisable(true);
        txtSearch.setDisable(true);
        dtSchedule.setDisable(false);
        btDelete.setDisable(true);
        btRefresh.setDisable(true);
        
        dtSchedule.setEditable(false);
        
        String now = LocalDate.now().getDayOfWeek().toString();
        if(now == "SUNDAY"){
			
			lblSun.setText(LocalDate.now().toString());
			lblMon.setText(LocalDate.now().plusDays(1).toString());
			lblTues.setText(LocalDate.now().plusDays(2).toString());
			lblWed.setText(LocalDate.now().plusDays(3).toString());
			lblThurs.setText(LocalDate.now().plusDays(4).toString());
			lblFri.setText(LocalDate.now().plusDays(5).toString());
			lblSat.setText(LocalDate.now().plusDays(6).toString());

		}
		else if(now == "MONDAY"){
			
			lblMon.setText(LocalDate.now().toString());
			lblTues.setText(LocalDate.now().plusDays(1).toString());
			lblWed.setText(LocalDate.now().plusDays(2).toString());
			lblThurs.setText(LocalDate.now().plusDays(3).toString());
			lblFri.setText(LocalDate.now().plusDays(4).toString());
			lblSat.setText(LocalDate.now().plusDays(5).toString());
			lblSun.setText(LocalDate.now().minusDays(1).toString());

		}
		else if(now == "TUESDAY"){
			
			lblTues.setText(LocalDate.now().toString());
			lblWed.setText(LocalDate.now().plusDays(1).toString());
			lblThurs.setText(LocalDate.now().plusDays(2).toString());
			lblFri.setText(LocalDate.now().plusDays(3).toString());
			lblSat.setText(LocalDate.now().plusDays(4).toString());
			lblSun.setText(LocalDate.now().minusDays(2).toString());
			lblMon.setText(LocalDate.now().minusDays(1).toString());

		}
		else if(now == "WEDNESDAY"){
			
			lblWed.setText(LocalDate.now().toString());
			lblThurs.setText(LocalDate.now().plusDays(1).toString());
			lblFri.setText(LocalDate.now().plusDays(2).toString());
			lblSat.setText(LocalDate.now().plusDays(3).toString());
			lblSun.setText(LocalDate.now().minusDays(3).toString());
			lblMon.setText(LocalDate.now().minusDays(2).toString());
			lblTues.setText(LocalDate.now().minusDays(1).toString());

		}
		else if(now == "THURSDAY"){
			
			lblThurs.setText(LocalDate.now().toString());
			lblFri.setText(LocalDate.now().plusDays(1).toString());
			lblSat.setText(LocalDate.now().plusDays(2).toString());
			lblSun.setText(LocalDate.now().minusDays(4).toString());
			lblMon.setText(LocalDate.now().minusDays(3).toString());
			lblTues.setText(LocalDate.now().minusDays(2).toString());
			lblWed.setText(LocalDate.now().minusDays(1).toString());

		}
		else if(now == "FRIDAY"){
			
			lblFri.setText(LocalDate.now().toString());
			lblSat.setText(LocalDate.now().plusDays(1).toString());
			lblSun.setText(LocalDate.now().minusDays(5).toString());
			lblMon.setText(LocalDate.now().minusDays(4).toString());
			lblTues.setText(LocalDate.now().minusDays(3).toString());
			lblWed.setText(LocalDate.now().minusDays(2).toString());
			lblThurs.setText(LocalDate.now().minusDays(1).toString());

		}
		else if(now == "SATURDAY"){
			
			lblSat.setText(LocalDate.now().toString());
			lblSun.setText(LocalDate.now().minusDays(6).toString());
			lblMon.setText(LocalDate.now().minusDays(5).toString());
			lblTues.setText(LocalDate.now().minusDays(4).toString());
			lblWed.setText(LocalDate.now().minusDays(3).toString());
			lblThurs.setText(LocalDate.now().minusDays(2).toString());
			lblFri.setText(LocalDate.now().minusDays(1).toString());
		}
        
        setLoadListSun();
        setLoadListMon();
        setLoadListTues();
        setLoadListWed();
        setLoadListThurs();
        setLoadListFri();
        setLoadListSat();
        
    }
	
	@FXML
	public void populateDates()
	{
		if(validateDate()){
			String day_of_week = dtSchedule.getValue().getDayOfWeek().toString();
			if(day_of_week == "SUNDAY"){
				
				lblSun.setText(dtSchedule.getValue().toString());
				lblMon.setText(dtSchedule.getValue().plusDays(1).toString());
				lblTues.setText(dtSchedule.getValue().plusDays(2).toString());
				lblWed.setText(dtSchedule.getValue().plusDays(3).toString());
				lblThurs.setText(dtSchedule.getValue().plusDays(4).toString());
				lblFri.setText(dtSchedule.getValue().plusDays(5).toString());
				lblSat.setText(dtSchedule.getValue().plusDays(6).toString());

			}
			else if(day_of_week == "MONDAY"){
				
				lblMon.setText(dtSchedule.getValue().toString());
				lblTues.setText(dtSchedule.getValue().plusDays(1).toString());
				lblWed.setText(dtSchedule.getValue().plusDays(2).toString());
				lblThurs.setText(dtSchedule.getValue().plusDays(3).toString());
				lblFri.setText(dtSchedule.getValue().plusDays(4).toString());
				lblSat.setText(dtSchedule.getValue().plusDays(5).toString());
				lblSun.setText(dtSchedule.getValue().minusDays(1).toString());

			}
			else if(day_of_week == "TUESDAY"){
				
				lblTues.setText(dtSchedule.getValue().toString());
				lblWed.setText(dtSchedule.getValue().plusDays(1).toString());
				lblThurs.setText(dtSchedule.getValue().plusDays(2).toString());
				lblFri.setText(dtSchedule.getValue().plusDays(3).toString());
				lblSat.setText(dtSchedule.getValue().plusDays(4).toString());
				lblSun.setText(dtSchedule.getValue().minusDays(2).toString());
				lblMon.setText(dtSchedule.getValue().minusDays(1).toString());

			}
			else if(day_of_week == "WEDNESDAY"){
				
				lblWed.setText(dtSchedule.getValue().toString());
				lblThurs.setText(dtSchedule.getValue().plusDays(1).toString());
				lblFri.setText(dtSchedule.getValue().plusDays(2).toString());
				lblSat.setText(dtSchedule.getValue().plusDays(3).toString());
				lblSun.setText(dtSchedule.getValue().minusDays(3).toString());
				lblMon.setText(dtSchedule.getValue().minusDays(2).toString());
				lblTues.setText(dtSchedule.getValue().minusDays(1).toString());

			}
			else if(day_of_week == "THURSDAY"){
				
				lblThurs.setText(dtSchedule.getValue().toString());
				lblFri.setText(dtSchedule.getValue().plusDays(1).toString());
				lblSat.setText(dtSchedule.getValue().plusDays(2).toString());
				lblSun.setText(dtSchedule.getValue().minusDays(4).toString());
				lblMon.setText(dtSchedule.getValue().minusDays(3).toString());
				lblTues.setText(dtSchedule.getValue().minusDays(2).toString());
				lblWed.setText(dtSchedule.getValue().minusDays(1).toString());

			}
			else if(day_of_week == "FRIDAY"){
				
				lblFri.setText(dtSchedule.getValue().toString());
				lblSat.setText(dtSchedule.getValue().plusDays(1).toString());
				lblSun.setText(dtSchedule.getValue().minusDays(5).toString());
				lblMon.setText(dtSchedule.getValue().minusDays(4).toString());
				lblTues.setText(dtSchedule.getValue().minusDays(3).toString());
				lblWed.setText(dtSchedule.getValue().minusDays(2).toString());
				lblThurs.setText(dtSchedule.getValue().minusDays(1).toString());

			}
			else if(day_of_week == "SATURDAY"){
				
				lblSat.setText(dtSchedule.getValue().toString());
				lblSun.setText(dtSchedule.getValue().minusDays(6).toString());
				lblMon.setText(dtSchedule.getValue().minusDays(5).toString());
				lblTues.setText(dtSchedule.getValue().minusDays(4).toString());
				lblWed.setText(dtSchedule.getValue().minusDays(3).toString());
				lblThurs.setText(dtSchedule.getValue().minusDays(2).toString());
				lblFri.setText(dtSchedule.getValue().minusDays(1).toString());

			}
			
			 setLoadListSun();
	         setLoadListMon();
	         setLoadListTues();
	         setLoadListWed();
	         setLoadListThurs();
	         setLoadListFri();
	         setLoadListSat();
		}
		
	}
	
	@FXML 
	private void setAllEnable(){
		 	rdPM_Shift.setDisable(false);
	        rdAM_Shift.setDisable(false);
	        btAdd_Employee.setDisable(false);
	        btSearch_Employee.setDisable(false);
	        txtSearch.setDisable(false);
	        TableEmployees.setDisable(false);
	        btDelete.setDisable(false);
	        btRefresh.setDisable(false);
	}
	
	@FXML 
	private void chooseEmpSun(Event event){
		 	setAllEnable();
		 	chosen = lblSun;
	}
	
	@FXML 
	private void chooseEmpMon(Event event){
		 	setAllEnable();
		 	chosen = lblMon;
	}
	
	@FXML 
	private void chooseEmpTues(Event event){
		 	setAllEnable();
		 	chosen = lblTues;
	}
	
	@FXML 
	private void chooseEmpWed(Event event){
		 	setAllEnable();
		 	chosen = lblWed;
	}
	
	@FXML 
	private void chooseEmpThurs(Event event){
		 	setAllEnable();
		 	chosen = lblThurs;
	}
	
	@FXML 
	private void chooseEmpFri(Event event){
		 	setAllEnable();
		 	chosen = lblFri;
	}
	
	@FXML 
	private void chooseEmpSat(Event event){
		 	setAllEnable();
		 	chosen = lblSat;
	}
	
	@FXML 
	private void addEmployeeClicked(Event event){
		
        if(TableEmployees.getSelectionModel().getSelectedItem()!=null) {
        		int count = 0;
        		Employee_Shift_SchedulerModel getSelectedRow = TableEmployees.getSelectionModel().getSelectedItem();
	        	String sqlQuery = "select * FROM Employees_Schedule where ID = "+getSelectedRow.getEmployeesID()+" AND Date = '"+chosen.getText()+"';";
	        	String sqlQuery1 = "select * FROM Employees where ID = "+getSelectedRow.getEmployeesID()+";";
	        	 
	        	try{
	        		connection = SqliteConnection.Connector();
			        statement = connection.createStatement();
		            resultSet = statement.executeQuery(sqlQuery);
		            
		            while(resultSet.next()){
		            	count++;
		            }
		            resultSet.close();
		            resultSet = statement.executeQuery(sqlQuery1);

		            if(count == 0){
		            	 int rowsAffected = statement.executeUpdate("insert into `Employees_Schedule` " +
		            	
	                     "(`ID`,`Date`, `AM/PM`)"+
	                     "values ("+resultSet.getString("ID")+",'"+chosen.getText()+"','" + AMPM() + "'"
	                    
	                     +");");
		            }
		            else{
		            	NotificationType notificationType = NotificationType.ERROR;
			            TrayNotification tray = new TrayNotification();
			            tray.setTitle("Employee Repeat");
			            tray.setMessage("This employee is already working this day");
			            tray.setNotificationType(notificationType);
			            tray.showAndDismiss(Duration.millis(5000));
		            }
		            
		            
		            statement.close();
		            resultSet.close();
		            connection.close();
		        
	        	}
	        	catch (SQLException e) {
		            e.printStackTrace();
		            
		        }
	        	
	        	 TableEmployees.setDisable(true);
	             rdPM_Shift.setDisable(true);
	             rdAM_Shift.setDisable(true);
	             btAdd_Employee.setDisable(true);
	             btSearch_Employee.setDisable(true);
	             txtSearch.setDisable(true);
	             btDelete.setDisable(true);
	             btRefresh.setDisable(true);
	        	 
	             setLoadListSun();
	             setLoadListMon();
	             setLoadListTues();
	             setLoadListWed();
	             setLoadListThurs();
	             setLoadListFri();
	             setLoadListSat();
        }
        else{
        	    NotificationType notificationType = NotificationType.ERROR;
	            TrayNotification tray = new TrayNotification();
	            tray.setTitle("No Employee Selected");
	            tray.setMessage("Please select an Employee from the table to schedule for this date");
	            tray.setNotificationType(notificationType);
	            tray.showAndDismiss(Duration.millis(5000));
        }
        
        
	}
	
	public String AMPM(){
		if(rdAM_Shift.isSelected())
		{
			return "AM";
		}
		else{
			return "PM";
		}
	}
	
	
    private void setLoadListSun(){
        //String sqlQuery = "select * FROM Employees_Schedule where Date = '"+lblSun.getText()+"';";
        //listSun.setItems((Scheduler_Table.getDataFromSqlAndAddToObservableList(sqlQuery)));
    	
    	listSun.setItems((Scheduler_Table.getDataFromSqlAndAddToObservableListSchedule("SELECT Employees_Schedule.*, Employees.ID, Employees.First_Name, Employees.Last_Name FROM"
        		+ " Employees_Schedule INNER JOIN Employees ON Employees_Schedule.ID=Employees.ID WHERE Employees_Schedule.Date = '" +lblSun.getText()+"';")));
    }
    
    private void setLoadListMon(){
    	listMon.setItems((Scheduler_Table.getDataFromSqlAndAddToObservableListSchedule("SELECT Employees_Schedule.*, Employees.ID, Employees.First_Name, Employees.Last_Name FROM"
        		+ " Employees_Schedule INNER JOIN Employees ON Employees_Schedule.ID=Employees.ID WHERE Employees_Schedule.Date = '" +lblMon.getText()+"';")));
    }
    
    private void setLoadListTues(){
    	listTues.setItems((Scheduler_Table.getDataFromSqlAndAddToObservableListSchedule("SELECT Employees_Schedule.*, Employees.ID, Employees.First_Name, Employees.Last_Name FROM"
        		+ " Employees_Schedule INNER JOIN Employees ON Employees_Schedule.ID=Employees.ID WHERE Employees_Schedule.Date = '" +lblTues.getText()+"';")));
    }
    
    private void setLoadListWed(){
    	listWed.setItems((Scheduler_Table.getDataFromSqlAndAddToObservableListSchedule("SELECT Employees_Schedule.*, Employees.ID, Employees.First_Name, Employees.Last_Name FROM"
        		+ " Employees_Schedule INNER JOIN Employees ON Employees_Schedule.ID=Employees.ID WHERE Employees_Schedule.Date = '" +lblWed.getText()+"';")));
    }
    
    private void setLoadListThurs(){
    	listThurs.setItems((Scheduler_Table.getDataFromSqlAndAddToObservableListSchedule("SELECT Employees_Schedule.*, Employees.ID, Employees.First_Name, Employees.Last_Name FROM"
        		+ " Employees_Schedule INNER JOIN Employees ON Employees_Schedule.ID=Employees.ID WHERE Employees_Schedule.Date = '" +lblThurs.getText()+"';")));
    }
    
    private void setLoadListFri(){
    	listFri.setItems((Scheduler_Table.getDataFromSqlAndAddToObservableListSchedule("SELECT Employees_Schedule.*, Employees.ID, Employees.First_Name, Employees.Last_Name FROM"
        		+ " Employees_Schedule INNER JOIN Employees ON Employees_Schedule.ID=Employees.ID WHERE Employees_Schedule.Date = '" +lblFri.getText()+"';")));
    }
    
    private void setLoadListSat(){
    	listSat.setItems((Scheduler_Table.getDataFromSqlAndAddToObservableListSchedule("SELECT Employees_Schedule.*, Employees.ID, Employees.First_Name, Employees.Last_Name FROM"
        		+ " Employees_Schedule INNER JOIN Employees ON Employees_Schedule.ID=Employees.ID WHERE Employees_Schedule.Date = '" +lblSat.getText()+"';")));
    }
    
   @FXML  
    private void doPrint(Event event) throws InvocationTargetException{
	   Printer printer = Printer.getDefaultPrinter();
	   PrinterJob job = PrinterJob.createPrinterJob();
	   
	   if(printer != null){
		   PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);
		   double scaleX
	       = pageLayout.getPrintableWidth() / grSchedule.getBoundsInParent().getWidth();
		   double scaleY
	       = pageLayout.getPrintableHeight() / grSchedule.getBoundsInParent().getHeight();
		   Scale scale = new Scale(scaleX, scaleY);
	  	   grSchedule.getTransforms().add(scale);
	  	   
	  	 if(job.printPage(pageLayout, grSchedule)){
	        	job.endJob();
	        	grSchedule.getTransforms().remove(scale);        	
	    	}
	    	else{
	    		NotificationType notificationType = NotificationType.ERROR;
	            TrayNotification tray = new TrayNotification();
	            tray.setTitle("Printing error");
	            tray.setMessage("Try turning on your printers");
	            tray.setNotificationType(notificationType);
	            tray.showAndDismiss(Duration.millis(10000));
	    	}
	   }
	   else if(printer == null){
    		//tray notification printer not found
    		NotificationType notificationType = NotificationType.ERROR;
            TrayNotification tray = new TrayNotification();
            tray.setTitle("Printer not found");
            tray.setMessage("Please set default printer/turn printer on");
            tray.setNotificationType(notificationType);
            tray.showAndDismiss(Duration.millis(10000));
    		
    	}
    	
    }
   
   
   @FXML
   private void schedulerDelete(Event event){
	 	if(TableEmployees.getSelectionModel().getSelectedItem()!=null){
	 		Employee_Shift_SchedulerModel getSelectedRow = TableEmployees.getSelectionModel().getSelectedItem();
	 		String sqlQuery = "delete FROM Employees_Schedule where ID = "+getSelectedRow.getEmployeesID()+" AND Date = '"+chosen.getText()+"';";
	        try {
	        	connection = SqliteConnection.Connector();
		        statement = connection.createStatement();
	             
	            statement.executeUpdate(sqlQuery);
	            //statement.executeUpdate("delete from Employees where ID ='"+getSelectedRow.getEmployeesID()+"';");
	             TableEmployees.setDisable(true);
	             rdPM_Shift.setDisable(true);
	             rdAM_Shift.setDisable(true);
	             btAdd_Employee.setDisable(true);
	             btSearch_Employee.setDisable(true);
	             txtSearch.setDisable(true);
	             btDelete.setDisable(true);
	             btRefresh.setDisable(true);
	        	 
	             setLoadListSun();
	             setLoadListMon();
	             setLoadListTues();
	             setLoadListWed();
	             setLoadListThurs();
	             setLoadListFri();
	             setLoadListSat();
	            
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
	 		tray.setTitle("No Date Selected");
	 		tray.setMessage("To delete a Customer, please select a day to delete from");
	 		tray.setNotificationType(notificationType);
	 		tray.showAndDismiss(Duration.millis(5000));
	 	}        
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
   	private void launchCustomerScreen(Event event) throws IOException{
	 	((Node)event.getSource()).getScene().getWindow().hide();
	 	Parent CustomerScreen = FXMLLoader.load(getClass().getResource("Menu_Customer.fxml"));
	 	Scene customer_screen = new Scene(CustomerScreen);
	 	Stage Customer_Screen = (Stage) ((Node) event.getSource()).getScene().getWindow();
	 	Customer_Screen.hide();
	 	Customer_Screen.setScene(customer_screen);
	 	Customer_Screen.setTitle("Customer Screen");
	 	Customer_Screen.show();
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
   
   @FXML
   private void setRefreshButtonClick(Event event){
       TableEmployees.setItems(Scheduler_Table.getDataFromSqlAndAddToObservableList("SELECT * FROM Employees;"));//sql Query
       txtSearch.clear();
   }
   
   @FXML
   private void setSearchButtonClick(Event event){
       String sqlQuery = "select * FROM Employees where ID = '"+txtSearch.getText()+"';";
       TableEmployees.setItems(Scheduler_Table.getDataFromSqlAndAddToObservableList(sqlQuery));
   }
   
   private boolean validateDate(){
		 if(dtSchedule.getValue() != null){
			 return true;
		 }
		 else{
			 Alert alert = new Alert(AlertType.WARNING);
			 alert.setTitle("Validate Date");
			 alert.setHeaderText(null);
			 alert.setContentText("Please Choose a Date");
			 alert.showAndWait();
			 
			 return false;
		 }
		 
	 }

    
   
   
}
	


   	                 
   	           
   	       
   	        
            
            
            
            
            
        
	
	
	
	
	


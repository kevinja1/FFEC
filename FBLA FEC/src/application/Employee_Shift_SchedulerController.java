package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import javafx.scene.control.RadioButton;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Employee_Shift_SchedulerController implements Initializable {
	
	public Employee_Shift_SchedulerModel Scheduler_Table = new Employee_Shift_SchedulerModel();
	@FXML
	ToggleGroup group = new ToggleGroup();
	@FXML
	private DatePicker dtSchedule;
	@FXML
	private ListView<Integer> listSun;
	@FXML
	private ListView<Integer> listMon;
	@FXML
	private ListView<Integer> listTues;
	@FXML
	private ListView<Integer> listWed;
	@FXML
	private ListView<Integer> listThurs;
	@FXML
	private ListView<Integer> listFri;
	@FXML
	private ListView<Integer> listSat;
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

	private Label chosen;
	Connection connection;
    private Statement statement;
    private ResultSet resultSet;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		
        //Get data from adminTableData ObservableList and set this data on JavaFX table column

        EmployeesFirst_Name.setCellValueFactory(new PropertyValueFactory<Employee_Shift_SchedulerModel,String>("EmployeesFirst_Name")); 
        EmployeesLast_Name.setCellValueFactory(new PropertyValueFactory<Employee_Shift_SchedulerModel,String>("EmployeesLast_Name"));
        EmployeesID.setCellValueFactory(new PropertyValueFactory<Employee_Shift_SchedulerModel,String>("EmployeesID"));
        
        
        TableEmployees.setItems(Scheduler_Table.getDataFromSqlAndAddToObservableList("SELECT * FROM EMPLOYEES"));
        
        TableEmployees.setDisable(true);
        rdPM_Shift.setDisable(true);
        rdAM_Shift.setDisable(true);
        btAdd_Employee.setDisable(true);
        btSearch_Employee.setDisable(true);
        txtSearch.setDisable(true);
        dtSchedule.setDisable(false);
        
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
	
	@FXML 
	private void setAllEnable(){
		 	rdPM_Shift.setDisable(false);
	        rdAM_Shift.setDisable(false);
	        btAdd_Employee.setDisable(false);
	        btSearch_Employee.setDisable(false);
	        txtSearch.setDisable(false);
	        TableEmployees.setDisable(false);
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
        	Employee_Shift_SchedulerModel getSelectedRow = TableEmployees.getSelectionModel().getSelectedItem();
	        	String sqlQuery = "select * FROM Employees where ID = "+getSelectedRow.getEmployeesID()+";";
	        	 
	        	try{
	        		connection = SqliteConnection.Connector();
			         statement = connection.createStatement();
		             resultSet = statement.executeQuery(sqlQuery);
		        

	             int rowsAffected = statement.executeUpdate("insert into `Employees_Schedule` " +
	                     "(`ID`,`Date`, `AM/PM`)"+
	                     "values ("+resultSet.getString("ID")+",'"+chosen.getText()+"','" + AMPM() + "'"
	                    
	                     +");");
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
        String sqlQuery = "select * FROM Employees_Schedule where Date = '"+lblSun.getText()+"';";
        listSun.setItems(Scheduler_Table.getDataFromSqlAndAddToObservableListSchedule(sqlQuery));
    }
    
    private void setLoadListMon(){
        String sqlQuery = "select * FROM Employees_Schedule where Date = '"+lblMon.getText()+"';";
        listMon.setItems(Scheduler_Table.getDataFromSqlAndAddToObservableListSchedule(sqlQuery));
    }
    
    private void setLoadListTues(){
        String sqlQuery = "select * FROM Employees_Schedule where Date = '"+lblTues.getText()+"';";
        listTues.setItems(Scheduler_Table.getDataFromSqlAndAddToObservableListSchedule(sqlQuery));
    }
    
    private void setLoadListWed(){
        String sqlQuery = "select * FROM Employees_Schedule where Date = '"+lblWed.getText()+"';";
        listWed.setItems(Scheduler_Table.getDataFromSqlAndAddToObservableListSchedule(sqlQuery));
    }
    
    private void setLoadListThurs(){
        String sqlQuery = "select * FROM Employees_Schedule where Date = '"+lblThurs.getText()+"';";
        listThurs.setItems(Scheduler_Table.getDataFromSqlAndAddToObservableListSchedule(sqlQuery));
    }
    
    private void setLoadListFri(){
        String sqlQuery = "select * FROM Employees_Schedule where Date = '"+lblFri.getText()+"';";
        listFri.setItems(Scheduler_Table.getDataFromSqlAndAddToObservableListSchedule(sqlQuery));
    }
    
    private void setLoadListSat(){
        String sqlQuery = "select * FROM Employees_Schedule where Date = '"+lblSat.getText()+"';";
        listSat.setItems(Scheduler_Table.getDataFromSqlAndAddToObservableListSchedule(sqlQuery));
    }
}
	


   	                 
   	           
   	       
   	        
            
            
            
            
            
        
	
	
	
	
	

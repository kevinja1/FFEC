package application;

//import statements
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

//Controller Class for the Employee Scheduler
public class Employee_SchedulerController extends MenuBar implements Initializable {
	
	//SQL queries for data access, insertion, deletion
	
	//selects all fields from Employee table
	public static final String EMP_SELECT_ALL_SQL = "SELECT * FROM EMPLOYEES";
			
	//Selects employees on a given date that are working
	public static final String EMP_SCHEDULE_LOAD_BY_DATE_SQL = 
		"SELECT Employees_Schedule.*, Employees.ID, Employees.First_Name, Employees.Last_Name FROM"
			+ " Employees_Schedule INNER JOIN Employees ON Employees_Schedule.ID=Employees.ID WHERE Employees_Schedule.Date = '%s';";
	
	//Selects specific employee working on a given date
	public static final String EMP_SCHEDULE_LOAD_BY_ID_AND_DATE_SQL =
		"SELECT * FROM Employees_Schedule WHERE ID = %s AND Date = '%s';";
	
	//Setting an employee shift (updating)
	public static final String EMP_SCHEDULE_UPDATE_SQL =
		"UPDATE Employees_Schedule SET Shift = '%s' WHERE ID = %s AND Date = '%s'";
	
	//Setting an employee shift (inserting)
	public static final String EMP_SCHEDULE_INSERT_SQL =
		"INSERT INTO Employees_Schedule(`ID`,`Date`,`Shift`) VALUES (%s, '%s','%s');";
	
	//Deleting an employee's shift
	public static final String EMP_SCHEDULE_DELETE_SQL =
		"DELETE FROM Employees_Schedule WHERE ID = %s AND Date = '%s'";
		
	
	//Class to perform calculations, database operations for the scheduler
	private Employee_Shift_SchedulerModel Scheduler_Table = new Employee_Shift_SchedulerModel();

	//UI Features
	@FXML
	private JFXDatePicker dtSchedule;
	@FXML
	private JFXComboBox<String> cbEmployee;
	@FXML
	private JFXComboBox<String> cbDOW;
	@FXML
	private JFXCheckBox cbAM;
	@FXML
	private JFXCheckBox cbPM;
	@FXML
	private Button txtAdd;
	@FXML
	private Button txtDelete;

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
	private ListView<String> chosenlist;

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
	
	private List<UIDayContext> dayContexts = null;

	@FXML
	private GridPane grSchedule;

	
	private Label chosen;
	
	//Connection, statement, and resulSet for database access
	Connection connection;
	private Statement statement;
	private ResultSet resultSet;

	@FXML
	private JFXDrawer topDrawer;
	@FXML
	private HBox hbMenu;

	@FXML
	private AnchorPane root;

	public static AnchorPane rootP;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbDOW.setValue("Sunday");
		dtSchedule.setValue(LocalDate.now());

		//Method to load navigation menu at top of screen
		initToolbar(root, hbMenu);
		
		// loading the employee information into the table
		cbEmployee.setItems(Scheduler_Table.getDataFromSqlAndAddToObservableList(EMP_SELECT_ALL_SQL));

		//Observable List values for combo box for user to choose day of week
		ObservableList<String> options = 
			FXCollections.observableArrayList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
		cbDOW.getItems().addAll(options);
		dtSchedule.setEditable(false);
		dtSchedule.setDisable(false);
		
		// initialized labelsObjs and listViewObjs into UIDayContext list
		dayContexts = 
			Arrays.asList(
				new UIDayContext(lblSun, listSun), 
				new UIDayContext(lblMon, listMon),
				new UIDayContext(lblTues, listTues), 
				new UIDayContext(lblWed, listWed),
				new UIDayContext(lblThurs, listThurs), 
				new UIDayContext(lblFri, listFri), 
				new UIDayContext(lblSat, listSat));
		
		// add list selection listeners
		for(UIDayContext dayContext : dayContexts) {
			dayContext.listViewObj.setOnMouseClicked(e -> {
				// clear other listViewSelected Items
				
				chooseEmp(dayContext.labelObj, dayContext.listViewObj);
			});
		}
		
		// update schedule with current week dates
		updateDateLabel(LocalDate.now());

		// Loading each of the days with the employees scheduled that day
		LoadEmployeeSchedule();
	}

	// Method that is run when a date is chosen on the date picker
	@FXML
	public void setOnDatePickerChosen(Event event) {
		if (dtSchedule.getValue() != null) {
			if (validateDate()) {
				updateDateLabel(dtSchedule.getValue());
				LoadEmployeeSchedule();
			}
		}
	}
	
	private void LoadEmployeeSchedule() {
		for(UIDayContext dayContext : dayContexts) {
			setLoadList(dayContext.labelObj, dayContext.listViewObj);
		}
	}
	
	//updates UI Label to alert user what date it is
	private void updateDateLabel(LocalDate inputDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtils.asDate(inputDate));
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		
		int daysToAdd = 0;
		LocalDate firstDayOfWeek = DateUtils.asLocalDate(calendar.getTime());
		for(UIDayContext dayContext : dayContexts) {
			dayContext.labelObj.setText(firstDayOfWeek.plusDays(daysToAdd).toString());
			daysToAdd++;
		}
	}

	// Adds an employee to the schedule if it was selected from bottom table
	@FXML
	private void addEmployeeClicked(Event event) {
		if (cbEmployee.getValue() != null && cbDOW.getValue() != null && validateDate() && validateAMPM()) {
			String day_of_week = cbDOW.getValue().toString();
			switch(day_of_week) {
				case "Sunday":
					chooseEmp(lblSun, listSun);
					break;
				case "Monday":
					chooseEmp(lblMon, listMon);
					break;
				case "Tuesday":
					chooseEmp(lblTues, listTues);
					break;
				case "Wednesday":
					chooseEmp(lblWed, listWed);
					break;
				case "Thursday":
					chooseEmp(lblThurs, listThurs);
					break;
				case "Friday":
					chooseEmp(lblFri, listFri);
					break;
				case "Saturday":
					chooseEmp(lblSat, listSat);
					break;
			}
			
			int count = 0;
			String str = cbEmployee.getValue().toString();
			int pos = str.indexOf(":");
			String id = str.substring(pos + 1);

			try {
				connection = SqliteConnection.Connector();
				statement = connection.createStatement();
				resultSet = statement.executeQuery(String.format(EMP_SCHEDULE_LOAD_BY_ID_AND_DATE_SQL, id, chosen.getText()));

				//Counts how many instances of the employee in the exact shift inputed there exists
				String shiftFromDb = null;
				while (resultSet.next()) {
					shiftFromDb = resultSet.getString("Shift");
					if (shiftFromDb != null && shiftFromDb.equals(AMPM())) {
						count++;
					}
				}

				//If there are already no instances of the employee in this shift, this either inserts the employee into this shift, or updates their shift
				if (count == 0) {
					if(shiftFromDb != null) {						
						statement.executeUpdate(String.format(EMP_SCHEDULE_UPDATE_SQL, AMPM(), id, chosen.getText()));
					} else {
						statement.executeUpdate(String.format(EMP_SCHEDULE_INSERT_SQL, id, chosen.getText(), AMPM()));
					}
				} else {
					//Otherwise, the user will be alerted the following message
					NotificationType notificationType = NotificationType.ERROR;
					TrayNotification tray = new TrayNotification();
					tray.setTitle("Employee Repeat");
					tray.setMessage("This employee is already working at this shift.");
					tray.setNotificationType(notificationType);
					tray.showAndDismiss(Duration.millis(5000));
				}

				statement.close();
				resultSet.close();
				connection.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}

			LoadEmployeeSchedule();
		} else if (validateAMPM()) {
			NotificationType notificationType = NotificationType.ERROR;
			TrayNotification tray = new TrayNotification();
			tray.setTitle("No Employee Selected");
			tray.setMessage("Please select an Employee");
			tray.setNotificationType(notificationType);
			tray.showAndDismiss(Duration.millis(5000));
		}

	}
	
	// Based on what day is chosen, the respective chooseEmp__ is called which
	// shows to the user that the day is selected to schedule
	private void chooseEmp(Label dayLabel, ListView<String> dayList) {
		chosen = dayLabel;
		chosenlist = dayList;
	}
	
	//Method to know which shifts the user wishes to input the employee for
	private String AMPM() {
		if (cbAM.isSelected() && cbPM.isSelected()) {
			return "AM/PM";
		} else if (cbAM.isSelected()) {
			return "AM";
		} else if (cbPM.isSelected()) {
			return "PM";
		} else {
			return "";
		}
	}
	
	//Makes sure at least one check box is selected
	private boolean validateAMPM() {
		if (AMPM() != "") {
			return true;
		} else {
			NotificationType notificationType = NotificationType.ERROR;
			TrayNotification tray = new TrayNotification();
			tray.setTitle("Validate Shift");
			tray.setMessage("Please select a valid shift (AM, PM)");
			tray.setNotificationType(notificationType);
			tray.showAndDismiss(Duration.millis(25000));

			return false;
		}
	}

	// Methods to load the employees into the schedule
	private void setLoadList(Label lblObj, ListView<String> listViewObj) {
		listViewObj.setItems(
			Scheduler_Table.getDataFromSqlAndAddToObservableListSchedule(
				String.format(EMP_SCHEDULE_LOAD_BY_DATE_SQL, lblObj.getText())));
	}
	
	// Method to print the schedule
	@FXML
	private void doPrint(Event event) throws InvocationTargetException {
		Printer printer = Printer.getDefaultPrinter();
		
		PrinterJob job = PrinterJob.createPrinterJob();
		if(job != null && printer == null) {
			printer = job.getPrinter();
		}
		
		if (printer != null) {
			PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE,
					Printer.MarginType.DEFAULT);
			double scaleX = pageLayout.getPrintableWidth() / grSchedule.getBoundsInParent().getWidth();
			double scaleY = pageLayout.getPrintableHeight() / grSchedule.getBoundsInParent().getHeight();
			Scale scale = new Scale(scaleX, scaleY);
			grSchedule.getTransforms().add(scale);

			if (job.printPage(pageLayout, grSchedule)) {
				job.endJob();
				grSchedule.getTransforms().remove(scale);
			} else {
				NotificationType notificationType = NotificationType.ERROR;
				TrayNotification tray = new TrayNotification();
				tray.setTitle("Printing error");
				tray.setMessage("Try turning on your printers");
				tray.setNotificationType(notificationType);
				tray.showAndDismiss(Duration.millis(10000));
			}
		} else if (printer == null) {
			// tray notification printer not found
			NotificationType notificationType = NotificationType.ERROR;
			TrayNotification tray = new TrayNotification();
			tray.setTitle("Printer not found");
			tray.setMessage("Please set default printer/turn printer on");
			tray.setNotificationType(notificationType);
			tray.showAndDismiss(Duration.millis(10000));

		}
	}

	// Method called when user wants to delete an employee schedule on a certain
	// day
	@FXML
	private void schedulerDelete(Event event) {
		if (chosenlist.getSelectionModel().getSelectedItem() != null) {
			String selectedItem = chosenlist.getSelectionModel().getSelectedItem();			
			String selectedEmpId = selectedItem.substring(0, selectedItem.indexOf(":"));
			
			try {
				connection = SqliteConnection.Connector();
				statement = connection.createStatement();

				statement.executeUpdate(String.format(EMP_SCHEDULE_DELETE_SQL, selectedEmpId, chosen.getText()));

				LoadEmployeeSchedule();

				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			NotificationType notificationType = NotificationType.ERROR;
			TrayNotification tray = new TrayNotification();
			tray.setTitle("No Date Selected");
			tray.setMessage("To delete, please select from weekly schedule list");
			tray.setNotificationType(notificationType);
			tray.showAndDismiss(Duration.millis(25000));
		}
	}


	// Gives an alert if a user tries to schedule without selecting a date
	private boolean validateDate() {
		if (dtSchedule.getValue() != null) {
			return true;
		} else {
			NotificationType notificationType = NotificationType.ERROR;
			TrayNotification tray = new TrayNotification();
			tray.setTitle("Validate Date");
			tray.setMessage("Please select a valid date");
			tray.setNotificationType(notificationType);
			tray.showAndDismiss(Duration.millis(25000));

			return false;
		}
	}
}
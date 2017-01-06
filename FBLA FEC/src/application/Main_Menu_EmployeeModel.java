package application;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Main_Menu_EmployeeModel{
	private final SimpleIntegerProperty EmployeesID;
	private final SimpleStringProperty EmployeesFirst_Name;
	private final SimpleStringProperty EmployeesLast_Name;
	private final SimpleStringProperty EmployeesEmail;
	private final SimpleStringProperty EmployeesPhone;
	private final SimpleStringProperty EmployeesAddress;
	private final SimpleStringProperty EmployeesDOB;
	private final SimpleIntegerProperty EmployeesisActive;
	
	
	public Main_Menu_EmployeeModel(int EmployeesID, String EmployeesFirst_Name, String EmployeesLast_Name, String EmployeesEmail, String EmployeesPhone, String EmployeesAddress, String EmployeesDOB, int EmployeesisActive){

		this.EmployeesID = new SimpleIntegerProperty(EmployeesID);
		this.EmployeesFirst_Name = new SimpleStringProperty(EmployeesFirst_Name);
		this.EmployeesLast_Name = new SimpleStringProperty(EmployeesLast_Name);
		this.EmployeesEmail = new SimpleStringProperty(EmployeesEmail);
		this.EmployeesPhone = new SimpleStringProperty(EmployeesPhone);
		this.EmployeesAddress = new SimpleStringProperty(EmployeesAddress);
		this.EmployeesDOB = new SimpleStringProperty(EmployeesDOB);
		this.EmployeesisActive = new SimpleIntegerProperty(EmployeesisActive);
	}

	//getters
	
	public SimpleIntegerProperty getEmployeesID() {
		return EmployeesID;
	}
	
	public SimpleStringProperty getEmployeesFirst_Name() {
		return EmployeesFirst_Name;
	}
	
	public SimpleStringProperty getEmployeesLast_Name() {
		return EmployeesLast_Name;
	}
	
	public SimpleStringProperty getEmployeesEmail() {
		return EmployeesEmail;
	}
	
	public SimpleStringProperty getEmployeesPhone() {
		return EmployeesPhone;
	}
	
	public SimpleStringProperty getEmployeesAddress() {
		return EmployeesAddress;
	}
	
	public SimpleStringProperty getEmployeesDOB() {
		return EmployeesDOB;
	}
	
	public SimpleIntegerProperty getEmployeesisActive() {
		return EmployeesisActive;
	}
	
	//setters
	
	public void setEmployeesID(int EmployeesID) {
		this.EmployeesID.set(EmployeesID);
	}
	
	public void setEmployeesFirst_Name(String First_Name) {
		this.EmployeesFirst_Name.set(First_Name);
	}
	
	public void setEmployeesLast_Name(String Last_Name) {
		this.EmployeesLast_Name.set(Last_Name);
	}
	
	public void setEmployeesEmail(String Email) {
		this.EmployeesEmail.set(Email);
	}
	
	public void setEmployeesPhone(String Phone) {
		this.EmployeesPhone.set(Phone);
	}
	
	public void setEmployeesAddress(String Address) {
		this.EmployeesAddress.set(Address);
	}
	
	public void setEmployeesDOB(String DOB) {
		this.EmployeesDOB.set(DOB);
	}
	
	public void setEmployeesisActive(int isActive) {
		this.EmployeesisActive.set(isActive);
	}
		
}



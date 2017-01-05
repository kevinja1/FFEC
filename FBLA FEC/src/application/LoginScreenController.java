package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class LoginScreenController implements Initializable {
		public LoginModel loginModel = new LoginModel();
		
		@FXML
		private TextField txtUsername;
		
		@FXML 
		private PasswordField txtPassword;
		
		@Override
		public void initialize(URL location, ResourceBundle resources){
			
		}
		
		public void Login (ActionEvent event) throws IOException
		{
			try{
				if(loginModel.isLogin(txtUsername.getText(), txtPassword.getText())){
					    Parent Main_Menu = FXMLLoader.load(getClass().getResource("Main_Menu.fxml"));
					    Scene MainMenu = new Scene(Main_Menu);
                        Stage mainMenu = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        mainMenu.hide();
                        mainMenu.setScene(MainMenu);
                        mainMenu.setTitle("Main Menu");
                        mainMenu.show();
				} 
				else {
					 	NotificationType notificationType = NotificationType.ERROR;
			            TrayNotification tray = new TrayNotification();
			            tray.setTitle("Error Login");
			            tray.setMessage("Incorrect login or password");
			            tray.setNotificationType(notificationType);
			            tray.showAndDismiss(Duration.millis(5000));

			            txtUsername.clear();
			            txtPassword.clear();

				}
			} 
			catch (SQLException e){
				e.printStackTrace();
			}		
		}	
}


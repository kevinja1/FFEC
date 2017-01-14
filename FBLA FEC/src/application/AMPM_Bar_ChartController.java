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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

 
public class AMPM_Bar_ChartController implements Initializable {
	
	AMPM_Bar_ChartModel barchart = new AMPM_Bar_ChartModel();
	
	@FXML
	private BarChart<String,Integer> barChartAttendance;
	
	@Override
	public void initialize(URL url, ResourceBundle rb){
		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		   
		series.getData().add(new XYChart.Data<>("AM", barchart.calcNumAM()));
		series.getData().add(new XYChart.Data<>("PM", barchart.calcNumPM()));
		
		
		barChartAttendance.getData().addAll(series);   
	}
	
	
	
	
}
package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class Customer_Attendance_Line_ChartController implements Initializable {
	
	Customer_Attendance_Line_ChartModel linechart = new Customer_Attendance_Line_ChartModel();
	
	@FXML
	private LineChart<String,Integer> lineChartAttendance;
	
	@Override
	public void initialize(URL url, ResourceBundle rb){
		XYChart.Series<String, Integer> seriesAM = new XYChart.Series<>();
		
		seriesAM.setName("AM");
		
		XYChart.Series<String, Integer> seriesPM = new XYChart.Series<>();
		seriesPM.setName("PM");
		   
		seriesAM.getData().add(new XYChart.Data<>("Sunday", linechart.calcNumAMSunday()));
		seriesAM.getData().add(new XYChart.Data<>("Monday", linechart.calcNumAMMonday()));
		seriesAM.getData().add(new XYChart.Data<>("Tuesday", linechart.calcNumAMTuesday()));
		seriesAM.getData().add(new XYChart.Data<>("Wednesday", linechart.calcNumAMWednesday()));
		seriesAM.getData().add(new XYChart.Data<>("Thursday", linechart.calcNumAMThursday()));
		seriesAM.getData().add(new XYChart.Data<>("Friday", linechart.calcNumAMFriday()));
		seriesAM.getData().add(new XYChart.Data<>("Saturday", linechart.calcNumAMSaturday()));
		
		seriesPM.getData().add(new XYChart.Data<>("Sunday", linechart.calcNumPMSunday()));
		seriesPM.getData().add(new XYChart.Data<>("Monday", linechart.calcNumPMMonday()));
		seriesPM.getData().add(new XYChart.Data<>("Tuesday", linechart.calcNumPMTuesday()));
		seriesPM.getData().add(new XYChart.Data<>("Wednesday", linechart.calcNumPMWednesday()));
		seriesPM.getData().add(new XYChart.Data<>("Thursday", linechart.calcNumPMThursday()));
		seriesPM.getData().add(new XYChart.Data<>("Friday", linechart.calcNumPMFriday()));
		seriesPM.getData().add(new XYChart.Data<>("Saturday", linechart.calcNumPMSaturday()));
		
		
		
		lineChartAttendance.getData().addAll(seriesAM);   
		lineChartAttendance.getData().addAll(seriesPM);   
	}
}
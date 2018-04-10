package Assignment1;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Start extends Application {

	private ArrayList<MyData> myData;

	private String[] array_houseType;
	private double[] array_currentValue;
	private double[] array_previousValue;
	private double[] array_Age;
	private String[] array_street;
	private String[] array_postCode;

	private double avgValue;
	private double maxValue;
	private double minValue;
	private double sdValue;
	private double sumValue = 0;

	private double avgAge;
	private double maxAge;
	private double minAge;
	private double sdAge;
	private int sumAge=0 ;
	
	private double avgChange;
	private double maxChange;
	private double minChange;
	private double sdChange;
	private int sumChange=0 ;

	public double getSD(double[] array, double avg) {
		double deviation = 0;
		double sd;
		for (double d : array) {
			deviation += (d - avg) * (d - avg);
		}
		sd = Math.sqrt(deviation / array.length);
		return sd;
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Read data from file
		ReadData readData = new ReadData();
		readData.readCSVFile("bin/property_tax_report.csv");

		myData = readData.getData();
		array_houseType = new String[myData.size()];
		array_currentValue = new double[myData.size()];
		array_previousValue = new double[myData.size()];
		array_Age = new double[myData.size()];
		array_street = new String[myData.size()];
		array_postCode = new String[myData.size()];

		for (int i = 0; i < myData.size(); i++) {
			array_houseType[i] = myData.get(i).getTypeHouse();
			array_currentValue[i] = myData.get(i).getCurrentLandValue();
			array_previousValue[i] = myData.get(i).getPreviousLandValue();
			array_Age[i] = myData.get(i).getAge();
			array_street[i] = myData.get(i).getStreetName();
			array_postCode[i] = myData.get(i).getPostCode();
		}
		// count by house type
		int oneFamilyNumber = 0;
		int mulFamilyNumber = 0;
		int commerNumber = 0;
		for (String s : array_houseType) {
			if (s.equals("One Family Dwelling"))
				oneFamilyNumber++;
			if (s.equals("Multiple Family Dwelling"))
				mulFamilyNumber++;
			if (s.equals("Commercial"))
				commerNumber++;
		}
		
		// Calculate property value
		maxValue = array_currentValue[0];
		minValue = array_currentValue[0];

		for (double d : array_currentValue) {
			maxValue = d > maxValue ? d : maxValue;
			minValue = d < minValue ? d : minValue;
			sumValue += d;
		}
		avgValue = sumValue / array_currentValue.length;
		sdValue = getSD(array_currentValue, avgValue);

		// Calculate house age
		maxAge = array_Age[0];
		minAge = array_Age[0];

		for (double d : array_Age) {
			maxAge = d > maxAge ? d : maxAge;
			minAge = d< minAge  ? d : minAge;
			sumAge += d;
		}		
		avgAge = sumAge / array_Age.length;
		sdAge = getSD(array_Age, avgAge);
		
		// Calculate property value change
		maxChange = array_currentValue[0]-array_previousValue[0];
		minChange = array_currentValue[0]-array_previousValue[0];
		double[] array_valueChange = new double[array_currentValue.length];
		
		for(int i = 0;i<array_currentValue.length;i++) {
			if(array_currentValue[i]!=0 && array_previousValue[i]!=0) {
			double change = array_currentValue[i]-array_previousValue[i];
			maxChange = change>maxValue ? change:maxValue;
			minChange = change<minValue ? change:minValue;
			sumChange += change;
			array_valueChange[i] = change;
			}
			else {
				array_valueChange[i] = 0;
			}
		}
		avgChange = sumChange / array_currentValue.length;		
		sdChange = getSD(array_valueChange,avgChange);
		

		// Labels
		Font titleFont = Font.font("Arial", FontWeight.BOLD, 12);
		
		Label avgValueTitleLabel = new Label("Average Property Value");
		avgValueTitleLabel.setFont(titleFont);
		avgValueTitleLabel.setTextFill(Color.web("#0076a3"));
		Label avgValueLabel = new Label(Double.toString(avgValue));
		avgValueTitleLabel.setOnMouseClicked(e -> {
			openSecondStage("AverageValue");
		});
		
		Label maxValueTitleLabel = new Label("Max Property Value");
		maxValueTitleLabel.setFont(titleFont);
		Label maxValueLabel = new Label(Double.toString(maxValue));
		Label minValueTitleLabel = new Label("Min Property Value");
		minValueTitleLabel.setFont(titleFont);
		Label minValueLabel = new Label(Double.toString(minValue));
		Label sdValueTitleLabel = new Label("Standard Deviation of Property Value");
		sdValueTitleLabel.setFont(titleFont);
		Label sdValueLabel = new Label(Double.toString(sdValue));
		
		Label avgAgeTitleLabel = new Label("Average Property Age");
		avgAgeTitleLabel.setFont(titleFont);
		avgAgeTitleLabel.setTextFill(Color.web("#0076a3"));
		Label avgAgeLabel = new Label(Double.toString(avgAge));
		avgAgeTitleLabel.setOnMouseClicked(e->{
			openSecondStage("AverageAge");
		});
		
		Label maxAgeTitleLabel = new Label("Max Property Value");
		maxAgeTitleLabel.setFont(titleFont);
		Label maxAgeLabel = new Label(Double.toString(maxAge));
		Label minAgeTitleLabel = new Label("Min Property Value");
		minAgeTitleLabel.setFont(titleFont);
		Label minAgeLabel = new Label(Double.toString(minAge));
		Label sdAgeTitleLabel = new Label("Standard Deviation of Property Value");
		sdAgeTitleLabel.setFont(titleFont);
		Label sdAgeLabel = new Label(Double.toString(sdAge));
		
		Label avgChangeTitleLabel = new Label("Average Property Change");
		avgChangeTitleLabel.setFont(titleFont);
		avgChangeTitleLabel.setTextFill(Color.web("#0076a3"));
		Label avgChangeLabel = new Label(Double.toString(avgChange));
		avgChangeTitleLabel.setOnMouseClicked(e->{
			openSecondStage("ValueChange");
		});		
		Label maxChangeTitleLabel = new Label("Max Property Value Change");
		maxChangeTitleLabel.setFont(titleFont);
		Label maxChangeLabel = new Label(Double.toString(maxChange));
		Label minChangeTitleLabel = new Label("Min Property Value Change");
		minChangeTitleLabel.setFont(titleFont);
		Label minChangeLabel = new Label(Double.toString(minChange));
		Label sdChangeTitleLabel = new Label("Standard Deviation of Property Value Change");
		sdChangeTitleLabel.setFont(titleFont);
		Label sdChangeLabel = new Label(Double.toString(sdChange));
		
		// Pie Chart --- Property Types
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data("One Family Dwelling", oneFamilyNumber),
				new PieChart.Data("Multiple Family Dwelling", mulFamilyNumber),
				new PieChart.Data("Commercial", commerNumber));
		final PieChart chart = new PieChart(pieChartData);
		chart.setTitle("Property Type");
		chart.setLabelLineLength(10);
		chart.setLegendSide(Side.LEFT);
		
		// Line Chart
		final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("House Values");
        yAxis.setLabel("Number of house in the Price range ");
        //creating the chart
        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("House value per $25,000");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("2016 Property Value");    
        
        int size = (int)maxValue/25000+1;
        int[] number = new int[size];
        for(double d:array_currentValue) {
        	if(d > (size-1)*25000) {
        		number[number.length - 1] ++;        		
        	}
        	else {
        		number[(int) d/25000] ++;
        	}
        }
        
        //populating the series with data
        for (int i =0; i<number.length; i++){
            if(number[i] == 0){
                break;
            }else {
                // 100, 000 / 25,000 to start with is 4            
                series.getData().add(new XYChart.Data(i+1, number[i]) );
            }
        }        
        //----------
        lineChart.getData().add(series);     
		
		// GridPane
		GridPane gridPane = new GridPane();
		GridPane.setConstraints(avgValueTitleLabel, 0, 0);
		GridPane.setConstraints(avgValueLabel, 0, 1);
		GridPane.setConstraints(maxValueTitleLabel, 1, 0);
		GridPane.setConstraints(maxValueLabel, 1, 1);
		GridPane.setConstraints(minValueTitleLabel, 2, 0);
		GridPane.setConstraints(minValueLabel, 2, 1);
		GridPane.setConstraints(sdValueTitleLabel, 3, 0);
		GridPane.setConstraints(sdValueLabel, 3, 1);
		
		GridPane.setConstraints(avgAgeTitleLabel, 0, 2);
		GridPane.setConstraints(avgAgeLabel, 0, 3);
		GridPane.setConstraints(maxAgeTitleLabel, 1, 2);
		GridPane.setConstraints(maxAgeLabel, 1, 3);
		GridPane.setConstraints(minAgeTitleLabel, 2, 2);
		GridPane.setConstraints(minAgeLabel, 2, 3);
		GridPane.setConstraints(sdAgeTitleLabel, 3, 2);
		GridPane.setConstraints(sdAgeLabel, 3, 3);
		
		GridPane.setConstraints(avgChangeTitleLabel, 0, 4);
		GridPane.setConstraints(avgChangeLabel, 0, 5);
		GridPane.setConstraints(maxChangeTitleLabel, 1, 4);
		GridPane.setConstraints(maxChangeLabel, 1, 5);
		GridPane.setConstraints(minChangeTitleLabel, 2, 4);
		GridPane.setConstraints(minChangeLabel, 2, 5);
		GridPane.setConstraints(sdChangeTitleLabel, 3, 4);
		GridPane.setConstraints(sdChangeLabel, 3, 5);
		
		gridPane.setMinWidth(200);
		gridPane.getChildren().addAll(avgValueTitleLabel, avgValueLabel,maxValueTitleLabel,maxValueLabel,
				minValueTitleLabel,minValueLabel,sdValueTitleLabel,sdValueLabel,avgAgeTitleLabel,avgAgeLabel,
				maxAgeTitleLabel,maxAgeLabel,minAgeTitleLabel,minAgeLabel,sdAgeTitleLabel,sdAgeLabel,
				avgChangeTitleLabel,avgChangeLabel,maxChangeTitleLabel,maxChangeLabel,minChangeTitleLabel,minChangeLabel,sdChangeTitleLabel,sdChangeLabel);
		
		// VBox		
		VBox vbox = new VBox();
		vbox.getChildren().addAll(gridPane,chart,lineChart);

		Scene scene = new Scene(vbox,1000,800);
		primaryStage.setTitle("Assignment 1 ");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	// Get HashMap listed by street name
	public HashMap<String, ArrayList<Double>> getStreetHashMap(String clickType) {
		HashMap<String, ArrayList<Double>> hashMap = new HashMap<>();
		String key;
		double keyValue;

		if (clickType.equals("AverageValue")) {
			for (MyData data : myData) {
				key = data.getStreetName();
				keyValue = data.getCurrentLandValue();
				if(hashMap.get(key)==null) {
					ArrayList<Double> list = new ArrayList<>();
					list.add(keyValue);
					hashMap.put(key, list);
				}
				else 
					hashMap.get(key).add(keyValue);
			}
		}

		else if (clickType.equals("AverageAge")) {
			for (MyData data : myData) {
				key = data.getStreetName();
				keyValue = data.getAge();
				if(hashMap.get(key)==null) {
					ArrayList<Double> list = new ArrayList<>();
					list.add(keyValue);
					hashMap.put(key, list);
				}
				else 
					hashMap.get(key).add(keyValue);
			}
		} else {
			for (MyData data : myData) {
				key = data.getStreetName();					
				keyValue = data.getAge();				
				
				if(hashMap.get(key)==null) {
					ArrayList<Double> list = new ArrayList<>();
					list.add(keyValue);
					hashMap.put(key, list);
				}
				else 
					hashMap.get(key).add(keyValue);
			}
		}
		return hashMap;
	}

	// Get HashMap listed by postal code
	public HashMap<String, ArrayList<Double>> getPostCodeHashMap(String clickType, String clickType2) {
		HashMap<String, ArrayList<Double>> hashMap = new HashMap<>();
		String street;
		String key;
		double keyValue;
		if (clickType.equals("AverageValue")) {
			for (MyData data : myData) {
				street = data.getStreetName();
				key = data.getPostCode();
				keyValue = data.getCurrentLandValue();
				if (street.equals(clickType2)) {					
					if(hashMap.get(key)==null) {
						ArrayList<Double> list = new ArrayList<>();
						list.add(keyValue);
						hashMap.put(key, list);
					}
					else 
						hashMap.get(key).add(keyValue);
				}
			}
		} else if (clickType.equals("AverageAge")) {
			for (MyData data : myData) {
				street = data.getStreetName();
				key = data.getPostCode();
				keyValue = data.getAge();
				if (street.equals(clickType2)) {
					if(hashMap.get(key)==null) {
						ArrayList<Double> list = new ArrayList<>();
						list.add(keyValue);
						hashMap.put(key, list);
					}
					else 
						hashMap.get(key).add(keyValue);
				}
			}
		} else {
			for (MyData data : myData) {
				street = data.getStreetName();
				key = data.getPostCode();
				if(data.getCurrentLandValue()!=0 && data.getPreviousLandValue()!=0) {
					keyValue = data.getCurrentLandValue() - data.getPreviousLandValue();
				}
				else {
					keyValue = 0;
				}				
				if (street.equals(clickType2)) {
					if(hashMap.get(key)==null) {
						ArrayList<Double> list = new ArrayList<>();
						list.add(keyValue);
						hashMap.put(key, list);
					}
					else 
						hashMap.get(key).add(keyValue);
				}
			}
		}
		return hashMap;
	}

	public class ResultRow {
		private String streetName;
		private double averageValue;
		private String postCode;
		private double averageAge;
		private double valueChange;

		public ResultRow(String streetName,String postCode, double averageValue,double averageAge,double valueChange) {
			this.streetName = streetName;
			this.averageValue = averageValue;
			this.postCode = postCode;
			this.averageAge = averageAge;
			this.valueChange = valueChange;
		}

		public String getStreetName() {
			return streetName;
		}

		public double getAverageValue() {
			return averageValue;
		}

		public double getAverageAge() {
			return averageAge;
		}

		public String getPostCode() {
			return postCode;
		}
		
		public double getValueChange() {
			return valueChange;
		}

	}

	protected void openSecondStage(String clickType) {
		// Create the table, and 3 columns:
		TableView<ResultRow> table = new TableView<>();
		TableColumn<ResultRow, String> firstCol;
		TableColumn<ResultRow, String> secondCol;

		// Open second stage		
		firstCol = new TableColumn<>("StreetName");
		secondCol = new TableColumn<>(clickType);
		HashMap<String, ArrayList<Double>> allStreets = getStreetHashMap(clickType);
		table.setOnMouseClicked(e -> {
			String clickType2 = table.getSelectionModel().getSelectedItem().getStreetName();
			openThirdStage(clickType, clickType2);
		});
		table.getColumns().addAll(firstCol, secondCol);

		// Tell the columns what getter function to call for their data:
		firstCol.setCellValueFactory(new PropertyValueFactory<ResultRow, String>("StreetName"));
		secondCol.setCellValueFactory(new PropertyValueFactory<ResultRow, String>(clickType));

		final ObservableList<ResultRow> data = FXCollections.observableArrayList();
		double sum = 0;
		for (String key : allStreets.keySet()) {
			ArrayList<Double> housesOnThisStreet = allStreets.get(key);
			sum=0;
			for (Double aHouse : housesOnThisStreet) {			
				sum += aHouse;				
			}
			double avg = sum / housesOnThisStreet.size();
			ResultRow thisRow;
			if(clickType.equals("AverageValue")) {
				thisRow = new ResultRow(key,null,avg,0,0);
			}
			else if(clickType.equals("AverageAge")) {
				thisRow = new ResultRow(key,null,0,avg,0);
			}
			else {
				thisRow = new ResultRow(key,null,0,0,avg);
			}
			data.add(thisRow);
		}
		table.setItems(data);	

		Scene newScene = new Scene(table, 500, 500);
		Stage newStage = new Stage();
		newStage.setScene(newScene);
		newStage.show();
	}

	public void openThirdStage(String clickType, String clickType2) {
		// Create the table, and 3 columns:
		TableView<ResultRow> table = new TableView<>();
		TableColumn<ResultRow, String> firstCol;
		TableColumn<ResultRow, String> secondCol;

		firstCol = new TableColumn<>("PostalCode");
		secondCol = new TableColumn<>(clickType);		
		table.getColumns().addAll(firstCol, secondCol);

		// Tell the columns what getter function to call for their data:
		firstCol.setCellValueFactory(new PropertyValueFactory<ResultRow, String>("PostCode"));
		secondCol.setCellValueFactory(new PropertyValueFactory<ResultRow, String>(clickType));

		final ObservableList<ResultRow> data = FXCollections.observableArrayList();
		HashMap<String, ArrayList<Double>> allStreets = getPostCodeHashMap(clickType,clickType2);
		double sum = 0;
		for (String key : allStreets.keySet()) {
			ArrayList<Double> housesOnThisStreet = allStreets.get(key);
			sum = 0;
			for (Double aHouse : housesOnThisStreet) {	
				sum += aHouse;				
			}
			double avg = sum / housesOnThisStreet.size();
			ResultRow thisRow;
			if(clickType.equals("AverageValue")) {
				thisRow = new ResultRow(null,key, avg,0,0);
			}
			else if(clickType.equals("AverageAge")) {
				thisRow = new ResultRow(null,key,0,avg,0);
			}
			else {
				thisRow = new ResultRow(null,key,0,0,avg);
			}			
			data.add(thisRow);
		}
		table.setItems(data);	
		
		Scene newScene = new Scene(table, 500, 500);
		Stage newStage = new Stage();
		newStage.setScene(newScene);
		newStage.show();
	}

}
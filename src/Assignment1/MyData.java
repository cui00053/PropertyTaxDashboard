package Assignment1;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MyData{
	
	 private String typeHouse; 	    
	 private String streetName; 
	 private String postCode; 	    
	 private int currentLandValue; 
	 private int previousLandValue; 	    
	 private int age; 	 
	 	 
	 
	public MyData() {
		 
	 }
	// Constructor
	public MyData(String typeHouse, String streetName, String postCode, int currentLandValue, int previousLandValue, int age) {
       this.typeHouse = typeHouse;
       this.streetName = streetName;
       this.postCode = postCode;
       this.currentLandValue = currentLandValue;
       this.previousLandValue = previousLandValue;
       this.age = age;
	}
	
	// Getters
	 public String getTypeHouse() {
	        return typeHouse;
	    }
	 public String getStreetName() {
	        return streetName;
	    }
	 public String getPostCode() {
	        return postCode;
	    }
	 public int getCurrentLandValue() {
	        return currentLandValue;
	    }
	 public int getPreviousLandValue() {
	        return previousLandValue;
	    }
	 public int getAge() {
	        return age;
	    }
}
	
 
class ReadData {
	 ArrayList<MyData> myData ;
	
	 public ReadData() {
		 myData = new ArrayList<MyData>();
	 }
	 public ArrayList<MyData> getData(){
		 return myData;
	 }
	 	 
	 public void readCSVFile(String filename){
			String houseType="";
			String street="";
			String postCode="";		        
		    int currentValue;
		    int previousValue;	   
		    int age;			
			
			try(BufferedReader reader = Files.newBufferedReader(Paths.get(filename))){
				String line = "";
				while(line != null){
					
					//get a line of text from the file
					line = reader.readLine();
					if(line == null) {
						break;
					}
					//Split the line by commas
					String [] partsOfLine = line.split(",");				
					
					//The array partsOfLine should now hold everything in the line between commas
					try {
						int _currentValue = Integer.parseInt(partsOfLine[19]);
						int _previousValue = Integer.parseInt(partsOfLine[22]);
						int _year = Integer.parseInt(partsOfLine[24]);
						if(_currentValue>0 && _previousValue>0 &&_year > 0 && _year < 2016) {
							street = partsOfLine[12];			
							postCode = partsOfLine[13];
							currentValue = Integer.parseInt(partsOfLine[19]);                  
			                previousValue = Integer.parseInt(partsOfLine[22]);	               
		                	age = 2016-Integer.parseInt(partsOfLine[24]);
							houseType = partsOfLine[5];							
							myData.add(new MyData(houseType, street, postCode, currentValue, previousValue, age));
		                }		                
					}
					catch(Exception e) {
						
					}
				}				
			}catch(IOException ioe)
			{
				System.out.println("Problem reading csv: " + ioe.getMessage());
			}
		}
	
}
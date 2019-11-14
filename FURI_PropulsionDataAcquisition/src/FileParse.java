import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileParse {
	
	private final double THRESHOLD_MIN = 0.3;
	private final double THRESHOLD_MAX = 25;
	private final double THRESHOLD_DERIV = 0.2;
	
	private ArrayList<String[]> rawData = new ArrayList<String[]>();
	private double[][] data;
	public double min1, min2, max1, max2;
	
	//Constructor Reads File + Saves to 2D Array of Integers
	public FileParse(String fileName) {
		
		try {
			Scanner in = new Scanner(new File(fileName));
			while(in.hasNextLine()) {
				rawData.add(in.nextLine().split("\t"));
			}
			
			data = new double[rawData.size() - 1][rawData.get(0).length];
			
			for(int i = 1; i < data.length; i++){
				for(int j = 0; j < rawData.get(0).length; j++){
					data[i-1][j] = Double.parseDouble(rawData.get(i)[j]);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
	}
	
	public String[] getHeaders() {
		String[] temp = new String[rawData.get(0).length];
		for(int i = 0; i < rawData.get(0).length; i++) {
			temp[i] = rawData.get(0)[i];
		}
		
		return temp;
	}
	
	//Method Writes data to specified file from 2D data matrix
	public void writeFile(String fileName) {
		int a = fileName.indexOf(".txt");
		String name = fileName.substring(0, a) + "MOD.txt";
		
		try {
			FileWriter fw = new FileWriter(name);
			String str;
			fw.write(getHeaders() + "\n");
			for(int i = 1; i < data.length; i++){
				str = "";
				for(int j = 0; j < data[i].length; j++){
					str += data[i][j] + "\t";
				}
				fw.write(str + "\n");
			}
			fw.close();
		}catch(Exception e){
			System.out.println(e);
		}
		
	}
	
	//Test METHOD
	public void writeDeriv(String fileName) {
		double d1, d2;
		
		try {
			FileWriter fw = new FileWriter(fileName);
			for(int i = 2; i < data.length; i++) {
				d1 = ((double)(data[i][1] - data[i-1][1]))/(data[i][0] - data[i-1][0]);
				d2 = ((double)(data[i][2] - data[i-1][2]))/(data[i][0] - data[i-1][0]);
				fw.write(data[i][0] + "\t" + d1 + "\t" + d2 + "\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//Method only writes every Nth element into the file
	public void writeEveryNPoint(String fileName, int n) {
		
		try {
			FileWriter fw = new FileWriter(fileName);
			fw.write("Time\tPitot\tThrust\n");
			for(int i = 1; i < data.length; i++){
				if(i%n == 0) {
					fw.write(data[i][0] + "\t" + data[i][1] + "\t" + data[i][2] + "\n");					
				}
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/*Filter 1:
	 * Eliminates small/large jumps in data. 
	 * Data is eliminated if not within a threshold determined by the threshold constants.
	 */
	public void oscillationRedFilter() {
		for(int i = 2; i < data.length; i++) {				
			for(int j = 1; j < data[i].length; j++) {
				if(Math.abs(data[i-1][j] - data[i][j]) <= THRESHOLD_MIN){
					data[i][1] = data[i-1][1];
				}
				if(Math.abs(data[i-1][j] - data[i][j]) >= THRESHOLD_MAX){
					data[i][1] = data[i-1][1];
				}
			}			
		}
	}
	
	/*Filter 2:
	 * Divides all numbers in data matrix by num.
	 * Eliminates any small oscillations in data.
	 */
	public void divideIntFilter(int num) {
		for(int i = 0; i < data.length; i++) {
			for(int j = 1; j < data[i].length; j++) {
				data[i][j] = num * (data[i][j]/num);
			}
		}
	}
	
	//Filter 3: Moving Average
	public void movAvgFilter() {
		for(int i = 2; i < data.length; i++) {
			for(int j = 1; j < data[i].length; j++) {
				data[i][j] = (data[i][j] + data[i-1][j])/2.0;
			}
		}
	}
	
	//Filter 3b: Moving Average (custom)
	public void movAvgFilter(int n) {
		double sum;
		for(int i = n; i < data.length; i++) {
			for(int j = 1; j < data[i].length; j++) {
				sum = 0;
				for(int k = i; k > i-n; k--) {
					sum += data[k][j];
				}
				data[i][j] = sum/((double)n);
			}
		}
	}
	
	//Filter 4: Threshold Check on Derivative
	public void derivRedFilter() {
		double d1, d2;
		
		for(int i = 2; i < data.length; i++) {				
			d1 = ((double)(data[i][1] - data[i-1][1]))/(data[i][0] - data[i-1][0]);
			d2 = ((double)(data[i][2] - data[i-1][2]))/(data[i][0] - data[i-1][0]);
			
			if(Math.abs(d1) >= THRESHOLD_DERIV){
				data[i][1] = data[i-1][1];
			}
			
			if(Math.abs(d2) >= THRESHOLD_DERIV){
				data[i][2] = data[i-1][2];
			}
		}
	}
	
	/*EXP FIlter*/
	public double[][] linearRegression(int n) {
		double[][] temp = new double[data.length][data[0].length];
		
		LinearRegression l;
		for(int i = 0; i < data.length-n; i+=n) {
			//subData(i, i+n)
			l = new LinearRegression(subData(i, i+n));
			for(int j = i; j < i+n; j++) {
				temp[j] = l.function(data[i][0]);
			}
		}
		
		try {
			FileWriter fw = new FileWriter("LinearReg.txt");
			fw.write("Time\tPitot\tThrust\n");
			for(int i = 1; i < temp.length; i++){
				fw.write(temp[i][0] + "\t" + temp[i][1] + "\t" + temp[i][2] + "\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//TEST
		data = temp;
		
		return temp;
	}
	
	private double[][] subData(int start, int end) {
		double[][] temp = new double[end - start][data[0].length];
		for(int i = start; i < end; i++) {
			temp[i-start] = data[i];
		}
		
		return temp;
	}
	
	/*
	 * Finds Min and Max values in data matrix
	 */
	public void setMinMax() {
		min1 = data[1][1];
		min2 = data[1][2];
		max1 = data[1][1];
		max2 = data[1][2];
		
		for(int i = 2; i < data.length; i++) {
			if(data[i][1] > max1){
				max1 = data[i][1];
			}				
			if(data[i][1] < min1){
				min1 = data[i][1];
			}
			if(data[i][2] > max2){
				max2 = data[i][2];
			}
			if(data[i][2] < min2){
				min2 = data[i][2];
			}
		}
		
	}
	
	
	public double[][] getTVsS() {
		int thrust = 0;
		int pitot = 0;
		
		for(int i = 0; i < rawData.get(0).length; i++) {
			if(rawData.get(0)[i].equals("Thrust")) {
				thrust = i;
			}
			if(rawData.get(0)[i].equals("Pitot")) {
				pitot = i;
			}
		}
		
		double[][] temp = new double[data.length][2];
		for(int i = 0; i < data.length; i++) {
			temp[i][0] = data[i][pitot];
			temp[i][1] = data[i][thrust];
		}
		
		
		return temp;
	}
	
	
	public double[][] getData() {
		return data;
	}
	
	/*
	 * Converts data matrix into string. User has option to print string to console.
	 */
	public String toString() {
		String str = "";
		for(int i = 1; i < data.length; i++) {
			for(int j = 0; j < data[i].length; j++) {
				str += data[i][j] + "\t";
			}
			str += '\n';
		}
		str += "\nMin1: " + min1 + "\tMax1: " + max1 + "\n" + "Min2: " + min2 + "\tMax2: " + max2 + "\n";
		return str;
	}
	

}
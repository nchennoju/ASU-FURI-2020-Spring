import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CalibrateData {

	private double[][] newData;
	private double[] min = new double[5];
	private double[] max = new double[5];
	
	public CalibrateData(double[][] data, int start, int end) {
		
		newData = new double[end - start][data[0].length];
		min = new double[data[0].length - 1];
		max = new double[data[0].length - 1];
		
		for(int i = start; i < end; i++) {
			for(int j = 0; j < data[i].length; j++) {
				newData[i - start][j] = data[i][j];
			}
		}

		for(int i = 1; i < newData[0].length; i++){
			min[i-1] = newData[0][i];
			max[i-1] = newData[0][i];
		}
		
		for(int i = 1; i < newData.length; i++) {
			for(int j = 1; j < newData[i].length; j++) {
				if(min[j-1] < newData[i][j]){
					min[j-1] = newData[i][j];
				}
				if(max[j-1] > newData[i][j]){
					max[j-1] = newData[i][j];
				}
			}
		}
		
	}
	
	public double[] getMin() {
		return min;
	}
	
	public double[] getMax() {
		return max;
	}
	
	/*private void setMinMax() {

		for(int i = 0; i < 5; i++){
			min[i] = newData[0][i+1];
			max[i] = newData[0][i+1];
		}
		
		for(int i = 1; i < newData.length; i++) {
			for(int j = 1; j < 6; j++) {
				if(min[i] < newData[i][j]){
					min[i] = newData[i][j];
				}
				if(max[i] > newData[i][j]){
					max[i] = newData[i][j];
				}
			}
		}
	}*/
	
	
}

import java.util.ArrayList;
import java.util.List;

public class LinearRegression1 {

	private double slope;
	private double b;
	
	public LinearRegression1(ArrayList<double[]> arr) {
		
		/*for(int i = 0; i < arr.size(); i++){
			System.out.print("[" + arr.get(i)[0] + ", " + arr.get(i)[1] + "]\t");
		}
		System.out.print("\n\nLine Equation: ");*/

		slope = calcSlope(arr);
		System.out.print("Slope: " + slope + "\t");
		b = (slope * -xAvg(arr)) + yAvg(arr);
		System.out.println(String.format("y - (%.2f) = %.2f(x - (%.2f))", yAvg(arr), slope, xAvg(arr)));
		
	}
	
	private double calcSlope(ArrayList<double[]> arr) {
		double xAverage = xAvg(arr);
		double yAverage = yAvg(arr);
		double sumX = 0, sumY = 0;
		
		for(int i = 0; i < arr.size(); i++) {
			sumX += (arr.get(i)[0] - xAverage)*(arr.get(i)[0] - xAverage);
			sumY += (arr.get(i)[1] - yAverage)*(arr.get(i)[0] - xAverage);
			System.out.println(sumX+"_"+sumY);
		}
		System.out.println(yAverage + " " + xAverage);

		return (sumY/arr.size())/(sumX/arr.size());
	}
	
	private double xAvg(ArrayList<double[]> arr) {
		double sum = 0;
		for(int i = 0; i < arr.size(); i++) {
			sum += arr.get(i)[0];
		}
		return sum/arr.size();
	}
	
	private double yAvg(ArrayList<double[]> arr) {
		double sum = 0;
		for(int i = 0; i < arr.size(); i++) {
			sum += arr.get(i)[1];
		}
		return sum/arr.size();
	}
	
	public double function(double x) {
		return (slope * x) + b;
	}
	
}

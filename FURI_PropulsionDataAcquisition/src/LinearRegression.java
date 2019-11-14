public class LinearRegression {
	
	private double[] slope;
	private double[] b;
	
	public LinearRegression(double[][] arr) {
		//CALC Averages
		double[] avg = new double[arr[0].length];
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[i].length; j++){
				avg[j] += arr[i][j];
			}
		}
		for(int i = 0; i < arr[0].length; i++){
			avg[i] /= arr.length;
		}
		
		
		//CALC Slope
		double slopeDen = 0;
		for(int i = 0; i < arr.length; i++) {
			slopeDen += Math.pow(arr[i][0] - avg[0], 2);
		}
		slopeDen /= arr.length;
		
		double[] slopeNum = new double[arr[0].length - 1];
		for(int i = 0; i < arr.length; i++) {
			for(int j = 1; j < arr[0].length; j++) {
				slopeNum[j-1] += (arr[i][j] - avg[j]) * (arr[i][0] - avg[0]);
			}
		}
		for(int i = 0; i < slopeNum.length; i++) {
			slopeNum[i] /= arr.length;
		}
		
		slope = new double[arr[0].length - 1];
		for(int i = 0; i < slopeNum.length; i++) {
			slope[i] = slopeNum[i]/slopeDen;
		}
		
		//CALC b Constant
		b = new double[arr[0].length - 1];
		for(int i = 0; i < b.length; i++) {
			b[i] = arr[0][i+1];
		}
		
	}
	
	public double[] getSlope() {
		return slope;
	}
	
	public double[] getB() {
		return b;
	}
	
	public double[] function(double x) {
		double[] temp = new double[slope.length+1];
		temp[0] = x;
		for(int i = 0; i < slope.length; i++) {
			temp[i+1] = (slope[i] * x) + b[i];
		}
		
		return temp;
	}
	
	public String toString() {
		String str = "";
		for(int i = 0; i < slope.length; i++) {
			str += ("y = " + String.format("%.2f", slope[i]) + "x +\t" + String.format("%.2f", b[i])) + "\n";
		}
		
		return str;
	}
	
	
	
	
	
	//DRIVER FOR TESTING
	/*public static void main(String[] args) {
		
		ArrayList<double[]> points = new ArrayList<double[]>();
		//ArrayList<double[]> points = generatePoints(10, -10, 10, -10, 10);
		double[] a = {1, 2, 2};
		double[] b = {2, 4, 4};
		double[] c = {3, 5, 5};
		double[] d = {4, 4, 4};
		double[] e = {5, 5, 5};
		points.add(a);
		points.add(b);
		points.add(c);
		points.add(d);
		points.add(e);
		
		for(int i = 0; i < points.size(); i++){
			System.out.print("[" + points.get(i)[0] + ", " + points.get(i)[1] + "]\t");
		}
		System.out.print("\n\nLine Equation(s): ");
		LinearRegression l = new LinearRegression(points);
		
	}*/
	
	//METHOD FOR TESTING
	/*public static ArrayList<double[]> generatePoints(int num, int lowX, int highX, int lowY, int highY) {
		ArrayList<double[]> temp = new ArrayList<double[]>();
		double x, y;
		
		for(int i = 0; i < num; i++) {
			x = (Math.random() * (highX - lowX)) + lowX;
			y = (Math.random() * (highY - lowY)) + lowY;
			
			double[] t = {x, y};
			temp.add(t);	
		}
		
		return temp;
	}*/

}
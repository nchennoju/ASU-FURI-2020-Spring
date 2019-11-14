import java.util.Arrays;
import java.util.Scanner;

public class testDriver {
	public static void main(String[] args) {
		
		FileName fN = new FileName();
		System.out.println(fN);
		
		FileParse fp1 = new FileParse(fN.toString());
		
		fp1.linearRegression(10);
		
		fp1.oscillationRedFilter();
		
		//test method
		//fp1.writeDeriv("deriv.txt");
		
		fp1.movAvgFilter(4);
		//fp1.derivRedFilter();
		fp1.writeFile(fN.toString());
		fp1.setMinMax();
		
		fp1.linearRegression(10);
		
		CalibrateData cD = new CalibrateData(fp1.getData(), 0, fp1.getData().length);
		System.out.println("MIN: " + Arrays.toString(cD.getMin()));
		System.out.println("MAX: " + Arrays.toString(cD.getMax()));
		
		System.out.println("\nDONE");
		
	}
}

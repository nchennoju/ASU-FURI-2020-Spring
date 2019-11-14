import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileName {

	private String recentFileName;
	
	public FileName() {
		Scanner in;
		try {
			in = new Scanner(new File("E:\\inc.txt"));
			while(in.hasNextLine()){
				recentFileName = in.nextLine();
			}
			recentFileName = "E:\\\\" + (Integer.parseInt(recentFileName) - 1) + ".txt";
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("No SD card found");
			
			JFileChooser chooser = new JFileChooser("C:\\Users\\nchennoju\\Documents\\Eclipse\\FURI_PropulsionDataAcquisition");
		    int returnVal = chooser.showOpenDialog(null);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		       System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
		    }
		    
		    recentFileName = chooser.getSelectedFile().getAbsolutePath();
		}
	}
	
	public String toString() {
		return recentFileName;
	}
	
}

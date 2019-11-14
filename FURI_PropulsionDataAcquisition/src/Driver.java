import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Driver extends JFrame {

    public static void main(String[] args) {
		//-------GRAPH----------------------------
    	
    	
    	FileName fN = new FileName();
		System.out.println(fN);
    	
    	SwingUtilities.invokeLater(() -> {
            Driver ex = new Driver(fN, 1);
            ex.setVisible(true);
            Driver ex2 = new Driver(fN, 0);
            ex2.setVisible(true);
        });
    }
	
    public Driver(FileName fN, int n) {
    	//MY CODE
		FileParse fp1 = new FileParse(fN.toString());
		
		if(n == 0) {
			
			
			fp1.oscillationRedFilter();
					
			fp1.movAvgFilter(4);
			fp1.derivRedFilter();
			fp1.writeFile(fN.toString());
			fp1.setMinMax();
			
			fp1.linearRegression(2000);
			
			CalibrateData cD = new CalibrateData(fp1.getData(), 0, fp1.getData().length);
			System.out.println("MIN: " + Arrays.toString(cD.getMin()));
			System.out.println("MAX: " + Arrays.toString(cD.getMax()));
			
			
			
			System.out.println("\nDONE");			
		}
    	
		// ------------ METHOD CALL --------------
    	initUI(fp1, fN, (n != 0));
    }

    private void initUI(FileParse f, FileName fN, boolean isRaw) {
    	XYDataset dataset = createDataset(f, fN.toString());
    	JFreeChart chart = createChart(dataset, isRaw);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Data Visual Representation");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private XYDataset createDataset(FileParse f, String fN) {

    	String[] headers = f.getHeaders();
    	double[][] d = f.getData();
    	
    	XYSeriesCollection dataset = new XYSeriesCollection();
    	XYSeries series;
    	for(int i = 1; i < headers.length; i++) {
        	series = new XYSeries(headers[i]);
        	for(int j = 0; j < d.length; j++) {
        		series.add(d[j][0], d[j][i]);
        	}
        	
        	dataset.addSeries(series);
    	}
    	
        

        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset, boolean isRaw) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                ((isRaw) ? "Raw Data" : "Filterred Data"), //Name of graph 
                "Time (ms)", //X Axis Title
                "Sensor VAL", // Y - Axis Title
                dataset, 
                PlotOrientation.VERTICAL,
                true, 
                true, 
                false 
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(0.1f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle(((isRaw) ? "Raw Data" : "Filterred Data"),
                        new Font("Arial", java.awt.Font.BOLD, 18)
                )
        );

        return chart;

    }

}
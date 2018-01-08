package core;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Histogram extends ApplicationFrame {

    private String chartTitle;

    JFreeChart chart;

    private DefaultCategoryDataset dataset;

    public Histogram(String applicationTitle, String pChartTitle, String pXAxisName, String pYAxisName) {
        super(applicationTitle);
        setDefaultCloseOperation(ApplicationFrame.HIDE_ON_CLOSE);
        chartTitle=pChartTitle;
        dataset = new DefaultCategoryDataset();
        createGraph(pXAxisName, pYAxisName);
    }

    public void addData(double pValue, String pRowKey, String pColKey) {
        try {
            dataset.incrementValue(1,pRowKey,pColKey);
        } catch (Exception ex) {
            dataset.addValue(pValue,pRowKey,pColKey);
        }



//        if(dataset.getColumnIndex(pColKey)) {
//            dataset.incrementValue(1,pRowKey,pColKey);
//        } else {
//            dataset.addValue(pValue,pRowKey,pColKey);
//        }




    }

    public int getDataSize() {
        return dataset.getColumnCount();
    }

    public void createGraph(String pXAxisName, String pYAxisName) {
        chart = ChartFactory.createBarChart(chartTitle,pXAxisName,pYAxisName,dataset, PlotOrientation.HORIZONTAL,false,false,false);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.getRenderer().setSeriesPaint(0, Color.BLUE);

        ChartPanel chartPanel=new ChartPanel(chart);
        chartPanel.setPreferredSize( new Dimension(560, 367));
        setContentPane(chartPanel);
    }

    public void init() {
        this.pack( );
        RefineryUtilities.centerFrameOnScreen( this );
        this.setVisible( true );
    }

    public void initRange(int pMax,String pKey) {
        for(int i=1;i<=pMax;i++) {
            addData(0,pKey,Integer.toString(i));
        }
    }

    public void resetHistogram() {
        dataset.clear();
    }

    /*
     * Chart test
     */
    public static void main(String[] args) {
        
//        chart.addData(10,"aaa","3");
//        chart.addData(5,"aaa","4");
//        chart.reduceData(50);

//        chart.addData(7,"aaa","5");
//        chart.addData(9,"aaa","6");
//        chart.addData(15,"aaa","7");
//        chart.addData(18,"aaa","8");
//        chart.reduceData(50);
    }
}
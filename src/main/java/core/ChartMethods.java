/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import static java.awt.image.ImageObserver.WIDTH;
import javax.swing.JFrame;

/**
 *
 * @author Silent1
 */
public class ChartMethods {
    
    public ChartMethods() {
        
    }
    
    public Histogram getNewHistogram(String pTitle, String pXAxisName, String pYAxisName) {
        Histogram h = new Histogram(pTitle,pTitle,pXAxisName,pYAxisName);       
        h.setSize(600, 400);
        h.setVisible(true);
        return h;
    }
    
    public String test() {
        return "test";
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataDB;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mato
 */
public class DBPripojenie {

    public DBPripojenie() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        naplnTypyVagonov();
    }
    
    
    
    public void naplnTypyVagonov() throws SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","polnik2","9504208252");
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("typy.txt"));
            String line;
            try {
                while((line = br.readLine()) != null) {
                    String[] splitedLine = line.split(" ");
                    String rad = splitedLine[0];
                    int kod = Integer.parseInt(splitedLine[1]);
                    System.out.println(kod);
                    int interabilita = Integer.parseInt(splitedLine[2]);
                    double dlzka = Double.parseDouble(splitedLine[3].replaceAll(",", "."));
                    double hmotnost = Double.parseDouble(splitedLine[4].replaceAll(",", "."));
                    Double lozHmotnost = splitedLine[5].equals("-") ? null : Double.parseDouble(splitedLine[5].replaceAll(",", "."));
                    Double lozDlzka =  splitedLine[6].equals("-") ? null : Double.parseDouble(splitedLine[6].replaceAll(",", "."));
                    Double lozSirka = splitedLine[7].equals("-") ? null : Double.parseDouble(splitedLine[7].replaceAll(",", "."));
                    Double lozPlocha = splitedLine[8].equals("-") ? null : Double.parseDouble(splitedLine[8].replaceAll(",", "."));
                    Double lozVyska = splitedLine[9].equals("-") ? null : Double.parseDouble(splitedLine[9].replaceAll(",", "."));
                    Double lozObjem = splitedLine[10].equals("-") ? null : Double.parseDouble(splitedLine[10].replaceAll(",", "."));
                    String poznamka;
                    if( splitedLine.length > 10 ) {
                        poznamka = "";
                        for (int i = 11; i < splitedLine.length; i++) {
                            poznamka += splitedLine[i];
                            if ( i < splitedLine.length - 1 ) {
                                poznamka += " ";
                            }
                        }
                    } else {
                        poznamka = null;
                    }
                    Statement stmt = connection.createStatement();
        
                    String sql = "INSERT INTO TYP_VOZNA " +
                               "VALUES ('" + rad + "'," + kod + "," + interabilita + ","  + dlzka + "," + hmotnost + ","
                               + lozHmotnost + ","  + lozDlzka + ","  + lozSirka + ","  + lozPlocha + "," + lozVyska + ","  
                            + lozObjem + ",'" + poznamka + "',null)";
                    stmt.executeUpdate(sql);                
                }
            } catch (IOException ex) {
                Logger.getLogger(DBPripojenie.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBPripojenie.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            connection.close();
        }
    }
}

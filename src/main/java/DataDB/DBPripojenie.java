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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mato
 */
public class DBPripojenie {

    private ArrayList<Integer> kodyTypu;
    private String connString = "jdbc:oracle:thin:@localhost:1521:xe";
    private String meno ="polnik2";
    private String heslo = "9504208252";
    
    public DBPripojenie() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        kodyTypu = new ArrayList<>(100);
        //naplnTypyVagonov();
        //naplnVozne(100);
        //naplnStanice();
        //naplnKolaje();
        naplnSmimace();
    }
     
    public void naplnTypyVagonov() throws SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection(connString,meno,heslo);
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("typy.txt"));
            String line;
            try {
                while((line = br.readLine()) != null) {
                    String[] splitedLine = line.split(" ");
                    String rad = splitedLine[0];
                    int kod = Integer.parseInt(splitedLine[1]);
                    kodyTypu.add(kod);
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
    
    public void naplnVozne( int pocet) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connString,meno,heslo);
            /*Statement stmt = connection.createStatement();
            String sql = "INSERT INTO SPOLOCNOST (nazov) VALUES('AWT Rail SK')";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO SPOLOCNOST (nazov) VALUES('BF Logistics')";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO SPOLOCNOST (nazov) VALUES('Deutsche Bahn')";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO SPOLOCNOST (nazov) VALUES('Express Rail')";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO SPOLOCNOST (nazov) VALUES('U.S. Steel Kosice')";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO SPOLOCNOST (nazov) VALUES('UNIPETROL DOPRAVA')";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO SPOLOCNOST (nazov) VALUES('TSS GRADE')";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO SPOLOCNOST (nazov) VALUES('SLOV-VAGON')";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO SPOLOCNOST (nazov) VALUES('Petrolsped')";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO SPOLOCNOST (nazov) VALUES('OHL ŽS')";
            stmt.executeUpdate(sql);*/
            Statement stmt = connection.createStatement();
            String sql = "SELECT id_spolocnosti FROM Spolocnost";
            ResultSet rs = stmt.executeQuery(sql);
            ArrayList<Integer> spolocnosti = new ArrayList<>(10);
            while(rs.next()){
                int id = rs.getInt("id_spolocnosti");
                spolocnosti.add(id);
            }
            ArrayList<Integer> typy = new ArrayList<>(100);
            sql = "SELECT kod FROM TYP_VOZNA";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                int kod = rs.getInt("kod");
                typy.add(kod);
            }
            Random rand = new Random();
            for (int i = 0; i < typy.size(); i++) {
                int kod = typy.get(i);         
                for (int j = 0; j < pocet; j++) {
                    int idSpol = rand.nextInt(spolocnosti.size());
                    double prst = rand.nextDouble();
                    if( prst < 0.03 ) {
                        sql = "INSERT INTO vozen VALUES(" + (j + 1) + "," + kod + "," + "'N'" + "," + spolocnosti.get(idSpol) + ")";
                    } else {
                        sql = "INSERT INTO vozen VALUES(" + (j + 1) + "," + kod + "," + "'A'" + "," + spolocnosti.get(idSpol) + ")";
                    }
                    stmt.executeUpdate(sql);
                }
            }      
        } catch (SQLException ex) {
            Logger.getLogger(DBPripojenie.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBPripojenie.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void naplnStanice() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connString,meno,heslo);
            Statement stmt = connection.createStatement();
            Random rand = new Random();
            
            BufferedReader br = new BufferedReader(new FileReader("stanice_small.txt"));
            String line;
            while((line = br.readLine()) != null) {
                line = line.trim();
                if( line.length() > 2 ) {
                    float sirka = ( rand.nextInt(10) + 50 + rand.nextFloat());
                    float vyska = ( rand.nextInt(10) + 40 + rand.nextFloat());
                    String sql = "INSERT INTO stanica (nazov,gps_sirka,gps_dlzka) VALUES('" + line + "'," + sirka + "," +  vyska + ")";
                    stmt.executeUpdate(sql);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBPripojenie.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBPripojenie.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBPripojenie.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBPripojenie.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void naplnKolaje() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connString,meno,heslo);
            Random rand = new Random();
            Statement stmt = connection.createStatement();
            String sql = "SELECT id_stanice FROM stanica";
            ResultSet rs = stmt.executeQuery(sql);
            ArrayList<Integer> stanice = new ArrayList<>(10);
            while(rs.next()){
                int id = rs.getInt("id_stanice");
                stanice.add(id);
            }
            for (int i = 0; i < stanice.size(); i++) {
                int pocet = rand.nextInt(5) + 3;
                for (int j = 1; j <= pocet; j++) {
                    int dlzka = rand.nextInt(1000) + 400;
                    sql = "INSERT INTO kolaj VALUES(" + j + "," + dlzka + "," + stanice.get(i) + ")";
                    stmt.executeUpdate(sql);
                }
            } 
        } catch (SQLException ex) {
            Logger.getLogger(DBPripojenie.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBPripojenie.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void naplnSmimace() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connString,meno,heslo);
            Random rand = new Random();
            Statement stmt = connection.createStatement();
            Statement stmtUpdate = connection.createStatement();
            String sql = "SELECT cislo, id_stanice,GPS_SIRKA,GPS_DLZKA from stanica JOIN kolaj USING(id_stanice)";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                int cislo = rs.getInt("cislo");
                int idStanice = rs.getInt("id_stanice");
                float sirka = rs.getFloat("GPS_SIRKA");
                float dlzka = rs.getFloat("GPS_DLZKA");
                int pocet = rand.nextInt(2) + 1;
                for (int i = 0; i < pocet; i++) {
                    float newSirka = sirka + (float) (rand.nextFloat()/1000.0 - 0.00005);
                    float newDlzka = dlzka + (float) (rand.nextFloat()/1000.0 - 0.00005);
                    sql = "INSERT INTO snimac (gps_sirka,gps_dlzka,cislo,id_stanice) VALUES(" + newSirka + "," + newDlzka + "," + cislo + "," + idStanice + ")";
                    stmtUpdate.executeUpdate(sql);
                }               
            }
            int a = 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBPripojenie.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBPripojenie.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

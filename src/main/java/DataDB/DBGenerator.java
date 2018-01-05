/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataDB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mato
 */
public class DBGenerator {

    private String connString = "jdbc:oracle:thin:@asterix.fri.uniza.sk:1521/orclpdb.fri.uniza.sk";
    private String meno       = "XXX";
    private String heslo      = "XXX";
    
    public DBGenerator() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //updateBlobs();
            //naplnTypyVagonov();
            //naplnVozne(15);
            //naplnStanice();
            //naplnKolaje();
            //naplnSmimace();
            //naplnPrvePohyby();
            //naplnPohyby(2);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
     
    public void naplnTypyVagonov() {
        Connection connection = null;
        BufferedReader br;
        try {
            connection = DriverManager.getConnection(connString,meno,heslo);
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
                    System.out.println(kod);
                    String sql = "INSERT INTO TYP_VOZNA " +
                               "VALUES ('" + rad + "'," + kod + "," + interabilita + ","  + dlzka + "," + hmotnost + ","
                               + lozHmotnost + ","  + lozDlzka + ","  + lozSirka + ","  + lozPlocha + "," + lozVyska + ","  
                            + lozObjem + ",'" + poznamka + "',null)";
                    stmt.executeUpdate(sql);                
                }
            } catch (IOException ex) {
                Logger.getLogger(DBGenerator.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(DBGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void naplnVozne( int pocet) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connString,meno,heslo);
            Statement stmt = connection.createStatement();
            String sql;
            sql = "INSERT INTO SPOLOCNOST (nazov) VALUES('AWT Rail SK')";
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
            stmt.executeUpdate(sql);
            sql = "SELECT id_spolocnosti FROM Spolocnost";
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
                        sql = "INSERT INTO vozen VALUES(T_VOZEN(" + (j + 1) + "," + kod + "," + "'N'" + "," + spolocnosti.get(idSpol) + "))";
                    } else {
                        sql = "INSERT INTO vozen VALUES(T_VOZEN(" + (j + 1) + "," + kod + "," + "'A'" + "," + spolocnosti.get(idSpol) + "))";
                    }
                    stmt.executeUpdate(sql);
                }
            }      
        } catch (SQLException ex) {
            Logger.getLogger(DBGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBGenerator.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DBGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBGenerator.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DBGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBGenerator.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(DBGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void naplnPrvePohyby() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connString,meno,heslo);
            Random rand = new Random();
            Statement stmt = connection.createStatement();
            String sql = "SELECT id_vozna, kod FROM vozen";
            ResultSet rs = stmt.executeQuery(sql);
            ArrayList<Integer> idcka = new ArrayList<>(100);
            ArrayList<Integer> kody = new ArrayList<>(100);
            while(rs.next()){
                int id = rs.getInt("id_vozna");
                idcka.add(id);
                int kod = rs.getInt("kod");
                kody.add(kod);
            }
            sql = "SELECT id_snimaca FROM snimac";
            rs = stmt.executeQuery(sql);
            ArrayList<Integer> snimace = new ArrayList<>(100);
            while(rs.next()){
                int id = rs.getInt("id_snimaca");
                snimace.add(id);
            }
            for (int i = 0; i < idcka.size(); i++) {
                int idSnimaca = snimace.get( rand.nextInt(snimace.size()) );
                sql = "INSERT INTO presun (id_snimaca_z, id_snimaca_na) VALUES( null, + " + idSnimaca + ")";
                stmt.executeUpdate(sql);
            } 
            sql = "SELECT id_presunu FROM presun";
            rs = stmt.executeQuery(sql);
            ArrayList<Integer> presuny = new ArrayList<>(1000);
            while(rs.next()){
                int id = rs.getInt("id_presunu");
                presuny.add(id);
            }
            Timestamp timestamp = new Timestamp(2016, 1, 1, 12, 0, 0, 0);
            PreparedStatement prepared = connection.prepareStatement("INSERT INTO pohyb (id_presunu, datum_od, id_vozna, kod ) VALUES ( ?,?,?,? )");
            int i = 0;
            for (Integer presun : presuny) {
                prepared.setInt(1, presun);
                prepared.setTimestamp(2, timestamp);
                prepared.setInt(3, idcka.get(i));
                prepared.setInt(4, kody.get(i));
                i++;
                prepared.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void naplnPohyby(int pocetCyklov) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connString,meno,heslo);
            Random rand = new Random();   
            for (int i = 0; i < pocetCyklov; i++) {
                String sql = "SELECT id_snimaca_na, id_snimaca, id_pohybu, datum_od, id_vozna, kod FROM pohyb" +
                                    "        LEFT JOIN presun pr USING(id_presunu) " +
                                    "            LEFT JOIN pohyb_vozna_vlak pv USING(id_zaradenia)" +
                                    "               WHERE datum_do IS NULL";
                Statement stmt = connection.createStatement();
                PreparedStatement preparedPresun = connection.prepareStatement("INSERT INTO presun (id_snimaca_z, id_snimaca_na) VALUES(?,?)");
                PreparedStatement updatePohyb = connection.prepareStatement("UPDATE pohyb SET datum_do = ? WHERE datum_do IS NULL AND id_vozna = ? AND kod = ?");
                PreparedStatement preparedPohyb = connection.prepareStatement("INSERT INTO pohyb (id_presunu, datum_od, id_vozna, kod) VALUES ( ?,?,?,? )");
                PreparedStatement preparedStanica = connection.prepareStatement("SELECT id_stanice FROM snimac WHERE id_snimaca = ?");
                PreparedStatement preparedSnimace = connection.prepareStatement("SELECT id_snimaca FROM snimac WHERE id_stanice = ?");
                PreparedStatement preparedMax = connection.prepareStatement("SELECT max(id_presunu) as max FROM presun");
                ResultSet rsPohyby = stmt.executeQuery(sql);
                sql = "SELECT max(id_presunu) as max FROM presun";
                while(rsPohyby.next()){
                    double prst = rand.nextDouble();
                    if(prst < 0.2) {
                        Timestamp datum_do = rsPohyby.getTimestamp("datum_od");
                        int id_vozna = rsPohyby.getInt("id_vozna");
                        int kod = rsPohyby.getInt("kod");
                        Integer id_snimaca_na = rsPohyby.getInt("id_snimaca_na");
                        Integer id_snimaca = rsPohyby.getInt("id_snimaca");
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(datum_do);
                        int seconds = rand.nextInt(3600 * 24 * 3) + 1000;
                        cal.add(Calendar.SECOND, seconds);
                        datum_do = new Timestamp(cal.getTime().getTime());
                        updatePohyb.setTimestamp(1, datum_do);
                        updatePohyb.setInt(2, id_vozna);
                        updatePohyb.setInt(3, kod);
                        updatePohyb.executeUpdate();
                        if(id_snimaca_na == 0) {
                            preparedStanica.setInt(1, id_snimaca);
                        } else {
                            preparedStanica.setInt(1, id_snimaca_na);
                        }
                        ResultSet stanice = preparedStanica.executeQuery();
                        stanice.next();
                        int id_stanice = stanice.getInt("id_stanice");
                        preparedSnimace.setInt(1, id_stanice);
                        ResultSet snimace = preparedSnimace.executeQuery();
                        ArrayList<Integer> id_snimacov = new ArrayList<>();
                        while(snimace.next()) {
                            id_snimacov.add(snimace.getInt("id_snimaca"));
                        }
                        int indexIdSnimaca = rand.nextInt(id_snimacov.size());
                        int id_snimacaNovy = id_snimacov.get(indexIdSnimaca);
                        if(id_snimaca_na == 0) {
                            preparedPresun.setInt(1, id_snimaca);
                        } else {
                            preparedPresun.setInt(1, id_snimaca_na);
                        }
                        preparedPresun.setInt(2, id_snimacaNovy);
                        preparedPresun.executeUpdate();
                        ResultSet rsMax = preparedMax.executeQuery();
                        rsMax.next();
                        int max = rsMax.getInt(1);
                        preparedPohyb.setInt(1, max);
                        preparedPohyb.setTimestamp(2, datum_do);
                        preparedPohyb.setInt(3, id_vozna);
                        preparedPohyb.setInt(4, kod);
                        preparedPohyb.executeUpdate();
                    }                 
                }
                PreparedStatement preparedVlak = connection.prepareStatement("INSERT INTO vlak (zaciatok, ciel, typ, dat_vypravenia, dat_dorazenia, vozne) VALUES( ? , ? ,? ,? ,?, T_VOZNE() )");
                PreparedStatement preparedVlakMax = connection.prepareStatement("SELECT max(id_vlaku) as max FROM VLAK");
                PreparedStatement preparedVozneStanica = connection.prepareStatement("SELECT id_vozna, kod, datum_od FROM pohyb" +
                "        LEFT JOIN presun pr USING(id_presunu)" +
                "            LEFT JOIN pohyb_vozna_vlak pv USING(id_zaradenia)" +
                "                WHERE datum_do IS NULL" +
                "                    AND ((SELECT id_stanice FROM snimac WHERE id_snimaca = pr.ID_SNIMACA_NA) = ?" +
                "                        OR (SELECT id_stanice FROM snimac WHERE id_snimaca = pv.ID_SNIMACA) = ? ) ");
                PreparedStatement preparedVozneStanicaCount = connection.prepareStatement("SELECT count(*) as rowcount FROM pohyb" +
                "        LEFT JOIN presun pr USING(id_presunu)" +
                "            LEFT JOIN pohyb_vozna_vlak pv USING(id_zaradenia)" +
                "                WHERE datum_do IS NULL" +
                "                    AND ((SELECT id_stanice FROM snimac WHERE id_snimaca = pr.ID_SNIMACA_NA) = ?" +
                "                        OR (SELECT id_stanice FROM snimac WHERE id_snimaca = pv.ID_SNIMACA) = ? ) ");
                sql = "SELECT id_stanice FROM stanica";
                ResultSet rs = stmt.executeQuery(sql);
                PreparedStatement preparedSnimaceStanica = connection.prepareStatement("SELECT id_snimaca FROM snimac WHERE id_stanice = ?");
                
                CallableStatement stmZarad = connection.prepareCall("{call zarad_vozen_do_vlaku_pom(?,?,?,?,?)}");
                CallableStatement stmVyrad = connection.prepareCall("{call vyrad_vozen_z_vlaku_pom(?,?,?,?,?)}");
                
                ArrayList<Integer> idcka_stanic = new ArrayList<>(100);
                while(rs.next()){
                    int id = rs.getInt("id_stanice");
                    idcka_stanic.add(id);
                }
                for (int j = 0; j < idcka_stanic.size(); j++) {
                    double prst = rand.nextDouble();
                    if(prst < 0.2) {
                        int zaciatok = idcka_stanic.get(j);
                        int ciel;
                        do
                        {
                            ciel = idcka_stanic.get( rand.nextInt(idcka_stanic.size()) );
                        } while( zaciatok == ciel );
                        preparedVozneStanica.setInt(1, zaciatok);
                        preparedVozneStanica.setInt(2, zaciatok);
                        ResultSet vozne = preparedVozneStanica.executeQuery();
                        preparedVozneStanicaCount.setInt(1, zaciatok);
                        preparedVozneStanicaCount.setInt(2, zaciatok);
                        ResultSet count = preparedVozneStanicaCount.executeQuery();
                        count.next();
                        int rowcount = count.getInt("rowcount");
                        ArrayList<Integer> zaradeneKody = new ArrayList<>();
                        ArrayList<Integer> zaradeneId = new ArrayList<>();
                        Timestamp maxTimestamp = null;
                        int pocetVoznov = (int) (rowcount * ((rand.nextDouble() * 0.08) + 0.25));
                        Timestamp prev = null;
                        for (int k = 0; k < pocetVoznov; k++) {
                            vozne.next();
                            int id_vozna = vozne.getInt("id_vozna");
                            int kod = vozne.getInt("kod");
                            Timestamp cas = vozne.getTimestamp("datum_od");
                            if( prev != null ) {
                                if(!prev.equals(cas)) {
                                    int a = 0;
                                }
                            }
                            if(maxTimestamp == null) {
                                maxTimestamp = cas;
                            } else {
                                if( cas.compareTo(maxTimestamp) > 0 ) {
                                    maxTimestamp = cas;
                                }
                            }
                            
                            prev = cas;
                            zaradeneKody.add(kod);
                            zaradeneId.add(id_vozna);
                        }
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(maxTimestamp);
                        cal.add(Calendar.MINUTE, rand.nextInt(180) + 180);
                        Timestamp dat_vyrazenia = new Timestamp(cal.getTime().getTime());
                        int typ = rand.nextInt(7) + 1;
                        cal = Calendar.getInstance();
                        cal.setTime(dat_vyrazenia);
                        cal.add(Calendar.MINUTE, rand.nextInt(1440) + 720);
                        Timestamp dat_dorazenia = new Timestamp(cal.getTime().getTime());
                        
                        preparedVlak.setInt(1, zaciatok);
                        preparedVlak.setInt(2, ciel);
                        preparedVlak.setInt(3, typ);
                        preparedVlak.setTimestamp(4, dat_vyrazenia);
                        preparedVlak.setTimestamp(5, dat_dorazenia);
                        preparedVlak.executeUpdate();
                        ResultSet maxIDVlakuRS = preparedVlakMax.executeQuery();
                        maxIDVlakuRS.next();
                        preparedSnimaceStanica.setInt(1, zaciatok);
                        ResultSet snimaceStanice = preparedSnimaceStanica.executeQuery();
                        ArrayList<Integer> snimace = new ArrayList<>();
                        while(snimaceStanice.next()){
                            snimace.add(snimaceStanice.getInt(1));
                        }
                        int id_snimac_vlak = snimace.get(rand.nextInt(snimace.size()));
                        int id_vlaku = maxIDVlakuRS.getInt("max");
                        for (int k = 0; k < zaradeneId.size(); k++) {
                            stmZarad.setInt(1, id_vlaku);
                            stmZarad.setInt(2, zaradeneId.get(k));
                            stmZarad.setInt(3, zaradeneKody.get(k));
                            
                            stmZarad.setInt(4, id_snimac_vlak);
                            
                            cal = Calendar.getInstance();
                            cal.setTime(dat_vyrazenia);
                            cal.add(Calendar.MINUTE, rand.nextInt(180) * -1);
                            Timestamp dat_zaradenia = new Timestamp(cal.getTime().getTime());
                            stmZarad.setTimestamp(5, dat_zaradenia);
                            stmZarad.executeUpdate();
                        }
                        preparedSnimaceStanica.setInt(1, ciel);
                        snimaceStanice = preparedSnimaceStanica.executeQuery();
                        snimace = new ArrayList<>();
                        while(snimaceStanice.next()){
                            snimace.add(snimaceStanice.getInt(1));
                        }
                        id_snimac_vlak = snimace.get(rand.nextInt(snimace.size()));
                        for (int k = 0; k < zaradeneId.size(); k++) {
                            stmVyrad.setInt(1, id_vlaku);
                            stmVyrad.setInt(2, zaradeneId.get(k));
                            stmVyrad.setInt(3, zaradeneKody.get(k));
                            
                            stmVyrad.setInt(4, id_snimac_vlak);
                            
                            cal = Calendar.getInstance();
                            cal.setTime(dat_dorazenia);
                            cal.add(Calendar.MINUTE, rand.nextInt(100));
                            Timestamp dat_zaradenia = new Timestamp(cal.getTime().getTime());
                            stmVyrad.setTimestamp(5, dat_zaradenia);
                            stmVyrad.executeUpdate();
                        }
                    }
                    
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void updateBlobs() {
        try {
            Connection connection = DriverManager.getConnection(connString,meno,heslo);
            PreparedStatement pstmt = connection.prepareStatement ("UPDATE typ_vozna SET obrazok = ?");
            File fBlob = new File ( "D:\\PDBSSem\\DBSem\\src\\main\\resources\\1.jpg" );
            FileInputStream is = new FileInputStream ( fBlob );
            pstmt.setBinaryStream (1, is, (int) fBlob.length() );
            pstmt.execute ();
        } catch (SQLException ex) {
            Logger.getLogger(DBGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
}

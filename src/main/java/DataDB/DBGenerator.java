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
    private String meno       = "alfa_sp";
    private String heslo      = "vsetcimajua";
    
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
            //naplnVlaky(20, 3);
            //naplnePrvePresuny();
            //naplnZaradenia();
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
                sql = "INSERT INTO presun (id_vozna, id_snimaca_z, id_snimaca_na, kod) VALUES(" + idcka.get(i) + ",null," + idSnimaca + "," + kody.get(i) + ")";
                stmt.executeUpdate(sql);
            } 
            sql = "SELECT id_presunu, id_vozna FROM presun";
            rs = stmt.executeQuery(sql);
            ArrayList<Integer> presuny = new ArrayList<>(1000);
            while(rs.next()){
                int id = rs.getInt("id_presunu");
                presuny.add(id);
            }
            Timestamp timestamp = new Timestamp(2016, 1, 1, 12, 0, 0, 0);
            PreparedStatement prepared = connection.prepareStatement("INSERT INTO pohyb (id_presunu, datum_od ) VALUES ( ?,? )");
            for (Integer presun : presuny) {
                prepared.setInt(1, presun);
                prepared.setTimestamp(2, timestamp);
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
    
    public void naplnVlaky( int pocetStarych, int pocetAktualnych) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connString,meno,heslo);
            Random rand = new Random();
            PreparedStatement preparedStmt = connection.prepareStatement("INSERT INTO vlak (zaciatok, ciel, typ, dat_vypravenia, dat_dorazenia) VALUES( ? , ? ,? ,? ,? )");
            String sql = "SELECT id_stanice FROM stanica";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ArrayList<Integer> idcka = new ArrayList<>(100);
            while(rs.next()){
                int id = rs.getInt("id_stanice");
                idcka.add(id);
            }
            for (int i = 0; i < pocetStarych; i++) {
                int zaciatok = idcka.get( rand.nextInt(idcka.size()) );
                int ciel = -1;
                do
                {
                    ciel = idcka.get( rand.nextInt(idcka.size()) );
                } while( zaciatok == ciel );
                long casMinus = rand.nextLong();
                if( casMinus < 0 ) {
                    casMinus *= -1;
                }
                casMinus += 86400000 * 5;
                long cin = 315360000;
                long cin2 = 100;
                long rok = cin * cin2;
                casMinus %= rok;
                long doba = rand.nextLong();
                if( doba < 0 ) {
                    doba *= -1;
                }
                doba %= 86400000;
                int typVlaku = rand.nextInt(7) + 1;
                Timestamp casZ = new Timestamp(Calendar.getInstance().getTimeInMillis() - casMinus);
                Timestamp casDo = new Timestamp(Calendar.getInstance().getTimeInMillis() - casMinus + doba);
                preparedStmt.setInt(1, zaciatok);
                preparedStmt.setInt(2, ciel);
                preparedStmt.setInt(3, typVlaku);
                preparedStmt.setTimestamp(4, casZ);
                preparedStmt.setTimestamp(5, casDo);
                preparedStmt.executeUpdate();
            } 
            preparedStmt = connection.prepareStatement("INSERT INTO vlak (zaciatok, ciel, typ, dat_vypravenia ) VALUES( ? , ? ,? ,? )");
            for (int i = 0; i < pocetAktualnych; i++) {
                int zaciatok = idcka.get( rand.nextInt(idcka.size()) );
                int ciel = -1;
                do
                {
                    ciel = idcka.get( rand.nextInt(idcka.size()) );
                } while( zaciatok == ciel );
                long doba = rand.nextLong();
                if( doba < 0 ) {
                    doba *= -1;
                }
                doba %= 86400000;
                int typVlaku = rand.nextInt(7) + 1;
                Timestamp casZ = new Timestamp(Calendar.getInstance().getTimeInMillis() - doba);
                preparedStmt.setInt(1, zaciatok);
                preparedStmt.setInt(2, ciel);
                preparedStmt.setInt(3, typVlaku);
                preparedStmt.setTimestamp(4, casZ);
                preparedStmt.executeUpdate();
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
    
    public void naplnePrvePresuny() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connString,meno,heslo);
            Random rand = new Random();
            String sql = "SELECT * FROM presun JOIN snimac sn1 ON ( presun.ID_SNIMACA_NA = sn1.id_snimaca)";
            Statement stmt = connection.createStatement();
            Statement stmt2 = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            //ArrayList<Integer> idcka = new ArrayList<>(100);
            PreparedStatement prepared = connection.prepareStatement("INSERT INTO pohyb (id_presunu, datum_od ) VALUES ( ?,? )");
            while(rs.next()){
                double prst = rand.nextDouble();
                if( prst < 0.3 ) {
                    int id_stanice = rs.getInt("id_stanice");
                    int id_vozna = rs.getInt("id_vozna");
                    int id_snimacaZ = rs.getInt("id_snimaca_na");
                    int kod = rs.getInt("kod");
                    int terajsia = rs.getInt("cislo");
                    sql = "SELECT * FROM kolaj WHERE id_stanice = " + id_stanice;
                    ResultSet rs2 = stmt2.executeQuery(sql);
                    int count = rand.nextInt(5);
                    int a = 0;
                    int kolaj = -1;
                    while(rs2.next()){                       
                        if ( terajsia != rs2.getInt("cislo")) {
                            kolaj = rs2.getInt("cislo");
                            a++;
                            if( a >= count ) {
                                break;
                            }
                        } 
                    }
                    sql = "SELECT id_snimaca FROM snimac WHERE id_stanice = " + id_stanice + " AND cislo=" + kolaj;
                    rs2 = stmt2.executeQuery(sql);
                    ArrayList<Integer> snimaceId = new ArrayList<>();
                    while(rs2.next()){      
                        snimaceId.add( rs2.getInt("id_snimaca"));
                    }
                    sql = "INSERT INTO presun (id_vozna, id_snimaca_z, id_snimaca_na, kod) VALUES(" + id_vozna + "," + id_snimacaZ + "," + snimaceId.get(rand.nextInt(snimaceId.size())) + "," + kod + ")";
                    stmt2.executeUpdate(sql);
                    sql = "SELECT max(id_presunu) as max FROM presun";
                    rs2 = stmt2.executeQuery(sql);
                    rs2.next();
                    int max = rs2.getInt(1);
                    prepared.setInt(1, max);
                    Timestamp stamp = new Timestamp(2016, rand.nextInt(4) + 2, rand.nextInt(25) + 2, rand.nextInt(20), rand.nextInt(60), count, max);
                    Timestamp timestampOd = new Timestamp(2016, 1, 1, 12, 0, 0, 0);
                    prepared.setTimestamp(2, stamp);
                    prepared.executeUpdate();
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
    
    public void naplnZaradenia() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connString,meno,heslo);
            Random rand = new Random();           
            Statement stmt = connection.createStatement();
            Statement stmt2 = connection.createStatement();
            String sql = "SELECT id_vlaku,zaciatok, ciel, dat_vypravenia, dat_dorazenia FROM vlak";
            ArrayList<Integer> vlaky = new ArrayList<>(100);
            ArrayList<Integer> zaciatky = new ArrayList<>(100);
            ArrayList<Integer> konce = new ArrayList<>(100);
            ArrayList<Timestamp> vypravenia = new ArrayList<Timestamp>(100);
            ArrayList<Timestamp> dorazenia = new ArrayList<Timestamp>(100);
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int id = rs.getInt("id_vlaku");
                vlaky.add(id);
                id = rs.getInt("zaciatok");
                zaciatky.add(id);
                id = rs.getInt("ciel");
                konce.add(id);
                Timestamp stamp = rs.getTimestamp("dat_vypravenia");
                vypravenia.add(stamp);
                stamp = rs.getTimestamp("dat_dorazenia");
                dorazenia.add(stamp);            
            }
            PreparedStatement prepared = connection.prepareStatement("INSERT INTO pohyb (id_zaradenia, datum_od ) VALUES ( ?,? )");
            for (int i = 0; i < vlaky.size(); i++) {
                int pocetVoznov = rand.nextInt(4) + 4;
                sql = "SELECT id_snimaca FROM stanica JOIN kolaj USING(id_stanice) JOIN snimac USING(id_stanice,cislo) WHERE id_stanice = " + zaciatky.get(i);
                ArrayList<Integer> snimaceStart = new ArrayList<>(2);
                rs = stmt.executeQuery(sql);
                while(rs.next()){
                    snimaceStart.add(rs.getInt(1));
                }
                sql = "SELECT id_snimaca FROM stanica JOIN kolaj USING(id_stanice) JOIN snimac USING(id_stanice,cislo) WHERE id_stanice = " + konce.get(i);
                ArrayList<Integer> snimaceEnd = new ArrayList<>(2);
                rs = stmt.executeQuery(sql);
                while(rs.next()){
                    snimaceEnd.add(rs.getInt(1));
                }
                sql = "SELECT id_pohybu, id1, kod1, id_snimaca_na FROM (\n" +
                            "SELECT id_pohybu, p1.id_vozna as id1, p2.id_vozna, p1.kod as kod1, p2.kod, id_snimaca_na, datum_od, row_number() over ( partition by p1.kod, p1.id_vozna order by datum_od desc) as poradie\n" +
                            "                            FROM presun p1 JOIN pohyb USING (id_presunu) LEFT JOIN pohyb_vozna_vlak p2 USING(id_zaradenia) \n" +
                            "                            WHERE (typ_pohybu IS NULL OR typ_pohybu = 'V')\n" +
                            "                                    AND( id_snimaca_na = 1" + snimaceStart.get(0) + " OR id_snimaca = " + snimaceStart.get(0) + " )" +
                            "              ) WHERE poradie = 1";
                rs = stmt.executeQuery(sql);
                ArrayList<Integer> idcka = new ArrayList<>(100);
                ArrayList<Integer> kody = new ArrayList<>(100);
                ArrayList<Integer> snimace = new ArrayList<>(100);
                while(rs.next()){
                    int id = rs.getInt("id1");
                    idcka.add(id);
                    int kod = rs.getInt("kod1");
                    kody.add(kod);
                    int snimac = rs.getInt("id_snimaca_na");
                    snimace.add(snimac);
                }
                if(idcka.size() < pocetVoznov ) {
                    continue;
                }
                for (int j = 0; j < pocetVoznov; j++) {                                       
                    int index = rand.nextInt(idcka.size());
                    int id_vozna = idcka.remove(index);
                    int kod = kody.remove(index);
                    int snimac = snimace.remove(index);
                    sql = "INSERT INTO pohyb_vozna_vlak (id_vozna,typ_pohybu,id_snimaca,kod,id_vlaku) VALUES (" + 
                            id_vozna + ",'Z'," + snimac + "," + kod + "," + vlaky.get(i) + ")";
                    stmt.executeUpdate(sql);
                    sql = "SELECT max(id_zaradenia) FROM pohyb_vozna_vlak";
                    rs = stmt.executeQuery(sql);
                    rs.next();
                    int id_zaradenia = rs.getInt(1);
                    prepared.setInt(1, id_zaradenia);
                    prepared.setTimestamp(2, vypravenia.get(i));
                    prepared.executeUpdate();                    
                    if( dorazenia.get(i) != null ) {
                        sql = "INSERT INTO pohyb_vozna_vlak (id_vozna,typ_pohybu,id_snimaca,kod,id_vlaku) VALUES (" + 
                            id_vozna + ",'V'," + snimaceEnd.get(0) + "," + kod + "," + vlaky.get(i) + ")";
                        stmt.executeUpdate(sql);
                        sql = "SELECT max(id_zaradenia) FROM pohyb_vozna_vlak";
                        rs = stmt.executeQuery(sql);
                        rs.next();
                        id_zaradenia = rs.getInt(1);
                        prepared.setInt(1, id_zaradenia);
                        prepared.setTimestamp(2, dorazenia.get(i));
                        prepared.executeUpdate();
                        
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

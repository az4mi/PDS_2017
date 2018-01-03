package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by mi5ho on 03.01.2018.
 */
public class DBmethods {

    private String connString = "jdbc:oracle:thin:@asterix.fri.uniza.sk:1521/orclpdb.fri.uniza.sk";
    private String meno       = "alfa_sp";
    private String heslo      = "vsetcimajua";

    public DBmethods() {
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void vytvorVlak(int pZaciatok, int pCiel, int pTyp, String pDatVyp, String pDatDor) {

        Connection connection = null;
        Statement stmt;

        try {

            connection = DriverManager.getConnection(connString,meno,heslo);
            stmt       = connection.createStatement();

            String sql;

            if (!pDatDor.equals("")||!pDatDor.equals(" ")||!pDatDor.isEmpty()) {

                sql = "INSERT INTO vlak (zaciatok,ciel,typ,dat_vypravenia,dat_dorazenia) VALUES("+pZaciatok+","+pCiel+","+pTyp+",to_timestamp('"+pDatVyp+"','DD-MM-YY'),to_timestamp('"+pDatDor+"','DD-MM-YY'))";
                stmt.executeUpdate(sql);

            } else {

                sql = "INSERT INTO vlak (zaciatok,ciel,typ,dat_vypravenia) VALUES("+pZaciatok+","+pCiel+","+pTyp+",to_timestamp('"+pDatVyp+"','DD-MM-YY'))";
                stmt.executeUpdate(sql);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void vytvorPohybVoznaVlak(char pTypPohybu, int pIdSnimaca, int pIdVlaku) {

        Connection connection = null;
        Statement stmt;

        try {

            connection = DriverManager.getConnection(connString,meno,heslo);
            stmt       = connection.createStatement();

            String sql;

            sql = "INSERT INTO pohyb_vozna_vlak (typ_pohybu,id_snimaca,id_vlaku) VALUES("+pTypPohybu+","+pIdSnimaca+","+pIdVlaku+")";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public String zobrazVlak() {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        try {
                
            connection = DriverManager.getConnection(connString,meno,heslo);
            stmt       = connection.createStatement();
            
            sql          = "SELECT id_vlaku, zaciatok, ciel, typ, dat_vypravenia, dat_dorazenia FROM vlak";
            ResultSet rs = stmt.executeQuery(sql);
            
     
            while(rs.next()){
                
                result += "Vlak\n"
                        + " > id              = "+rs.getString("id_vlaku")+"\n"
                        + " > zaciatok        = "+rs.getString("zaciatok")+"\n"
                        + " > ciel            = "+rs.getString("ciel")+"\n"
                        + " > typ             = "+rs.getString("typ")+"\n"
                        + " > dat. vypravenia = "+rs.getString("dat_vypravenia")+"\n"
                        + " > dat. dorazenia  = "+rs.getString("dat_dorazenia")+"\n\n";        
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public String zobrazPohybVoznaVlak() {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        try {
                
            connection = DriverManager.getConnection(connString,meno,heslo);
            stmt       = connection.createStatement();
            
            sql          = "SELECT id_zaradenia, typ_pohybu, id_snimaca, id_vlaku FROM pohyb_vozna_vlak";
            ResultSet rs = stmt.executeQuery(sql);
            
     
            while(rs.next()){
                
                result += "Pohyb vozna vlak\n"
                        + " > id zaradenia = "+rs.getString("id_zaradenia")+"\n"
                        + " > typ pohybu   = "+rs.getString("typ_pohybu")+"\n"
                        + " > id snimaca   = "+rs.getString("id_snimaca")+"\n"
                        + " > id vlaku     = "+rs.getString("id_vlaku")+"\n\n";
                                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public void pridajVozen(int pIdSpolocnosti, int pKod, int pIdVozna, int pIdSnimaca) {
        
        Connection connection = null;
        Statement stmt;

        try {

            connection = DriverManager.getConnection(connString,meno,heslo);
            stmt       = connection.createStatement();

            String sql;

            sql = "execute pridaj_vozen("+pIdSpolocnosti+","+pKod+","+pIdVozna+","+pIdSnimaca+")";
            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}

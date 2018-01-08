package core;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

/**
 * Created by mi5ho on 03.01.2018.
 */
public class DBmethods {

    private String connString = "jdbc:oracle:thin:@asterix.fri.uniza.sk:1521/orclpdb.fri.uniza.sk";
    private String meno       = "alfa_sp";
    private String heslo      = "vsetcimajua";
    
    private String prihlasenyPouzivatel_meno;
    private String prihlasenyPouzivatel_priezvisko;

    public DBmethods() {
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public boolean prihlasenie(String pMeno, String pPriezvisko, String pHeslo) {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        try {
                
            connection = DriverManager.getConnection(connString,meno,heslo);
            stmt       = connection.createStatement();
            
            sql          = "SELECT meno, priezvisko, heslo FROM pouzivatel";
            ResultSet rs = stmt.executeQuery(sql);          
     
            while(rs.next()){
                
                if (rs.getString("meno").equals(pMeno) &&
                    rs.getString("priezvisko").equals(pPriezvisko) &&
                    rs.getString("heslo").equals(pHeslo)) 
                {
                    prihlasenyPouzivatel_meno       = pMeno;
                    prihlasenyPouzivatel_priezvisko = pPriezvisko;                  
                    return true;                                   
                }       
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
        
    }
    
    public void vytvorStanicu(String pNazov, float pGpsSirka, float pGpsDlzka) {
        
        Connection connection = null;
        Statement stmt;

        try {

            connection = DriverManager.getConnection(connString,meno,heslo);
            stmt       = connection.createStatement();

            String sql;      

            sql = "INSERT INTO stanica (nazov, gps_sirka, gps_dlzka) VALUES('"+pNazov+"',"+pGpsSirka+","+pGpsDlzka+")";
            stmt.executeUpdate(sql);


        } catch (SQLException e) {
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
            
            sql          = "SELECT id_vlaku, zaciatok, ciel, typ, dat_vypravenia, dat_dorazenia, vozne FROM vlak";
            ResultSet rs = stmt.executeQuery(sql);
            
     
            while(rs.next()){
                
                result += "Vlak\n"
                        + " > id              = "+rs.getString("id_vlaku")+"\n"
                        + " > zaciatok        = "+rs.getString("zaciatok")+"\n"
                        + " > ciel            = "+rs.getString("ciel")+"\n"
                        + " > typ             = "+rs.getString("typ")+"\n"
                        + " > dat. vypravenia = "+rs.getString("dat_vypravenia")+"\n"
                        + " > dat. dorazenia  = "+rs.getString("dat_dorazenia")+"\n"
                        + " > vozne           = "+rs.getObject("vozne")+"\n\n";        
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
    
    public String zobrazPohyb() {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        try {
                
            connection   = DriverManager.getConnection(connString,meno,heslo);
            stmt         = connection.createStatement();
            sql          = "SELECT id_pohybu, id_presunu, id_zaradenia, datum_od, datum_do, id_vozna, kod, poznamka FROM pohyb";
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                
                result += "Pohyb\n"
                        + " > id pohybu     = "+rs.getString("id_pohybu")+"\n"
                        + " > id presunu    = "+rs.getString("id_presunu")+"\n"
                        + " > id zaradenia  = "+rs.getString("id_zaradenia")+"\n"
                        + " > datum od      = "+rs.getString("datum_od")+"\n"
                        + " > datum do      = "+rs.getString("datum_do")+"\n"
                        + " > id_vozna      = "+rs.getString("id_vozna")+"\n"
                        + " > kod           = "+rs.getString("kod")+"\n"
                        + " > poznamka      = "+rs.getString("poznamka")+"\n\n";
                                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public String zobrazSnimac() {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        try {
                
            connection   = DriverManager.getConnection(connString,meno,heslo);
            stmt         = connection.createStatement();
            sql          = "SELECT id_snimaca, gps_sirka, gps_dlzka, cislo, id_stanice FROM snimac";
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                
                result += "Snimac\n"
                        + " > id snimaca = "+rs.getString("id_snimaca")+"\n"
                        + " > gps sirka  = "+rs.getString("gps_sirka")+"\n"
                        + " > gps dlzka  = "+rs.getString("gps_dlzka")+"\n"
                        + " > cislo      = "+rs.getString("cislo")+"\n"
                        + " > id stanice = "+rs.getString("id_stanice")+"\n\n";
                    
                                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public String zobrazKolaj() {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        try {
                
            connection = DriverManager.getConnection(connString,meno,heslo);
            stmt       = connection.createStatement();
            
            sql          = "SELECT cislo, dlzka, id_stanice FROM kolaj";
            ResultSet rs = stmt.executeQuery(sql);
            
     
            while(rs.next()){
                
                result += "Kolaj\n"
                        + " > cislo      = "+rs.getString("cislo")+"\n"
                        + " > dlzka      = "+rs.getString("dlzka")+"\n"
                        + " > id stanice = "+rs.getString("id_stanice")+"\n\n";
                                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public String zobrazStanice() {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        try {
                
            connection = DriverManager.getConnection(connString,meno,heslo);
            stmt       = connection.createStatement();
            
            sql          = "SELECT id_stanice, nazov, gps_sirka, gps_dlzka FROM stanica";
            ResultSet rs = stmt.executeQuery(sql);
            
     
            while(rs.next()){
                
                result += "Stanica\n"
                        + " > id stanice = "+rs.getString("id_stanice")+"\n"
                        + " > nazov      = "+rs.getString("nazov")+"\n"
                        + " > gps sirka  = "+rs.getString("gps_sirka")+"\n"
                        + " > gps dlzka  = "+rs.getString("gps_dlzka")+"\n\n";
                                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public String zobrazVozen() {
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        try {
                
            connection   = DriverManager.getConnection(connString,meno,heslo);
            stmt         = connection.createStatement();          
            sql          = "SELECT id_vozna, kod, v_prevadzke, id_spolocnosti FROM vozen";
            ResultSet rs = stmt.executeQuery(sql);
     
            while(rs.next()){
                
                result += "Vozen\n"
                        + " > id vozna       = "+rs.getString("id_vozna")+"\n"
                        + " > kod            = "+rs.getString("kod")+"\n"
                        + " > v prevadzke    = "+rs.getString("v_prevadzke")+"\n"
                        + " > id spolocnosti = "+rs.getString("id_spolocnosti")+"\n\n";
                                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public String zobrazPresun() {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        try {
                
            connection   = DriverManager.getConnection(connString,meno,heslo);
            stmt         = connection.createStatement();          
            sql          = "SELECT id_presunu, id_snimaca_z, id_snimaca_na FROM presun";
            ResultSet rs = stmt.executeQuery(sql);
     
            while(rs.next()){
                
                result += "Presun\n"
                        + " > id presunu    = "+rs.getString("id_presunu")+"\n"
                        + " > id snimaca z  = "+rs.getString("id_snimaca_z")+"\n"
                        + " > id snimaca na = "+rs.getString("id_snimaca_na")+"\n\n";
                                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public String zobrazSpolocnost() {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        try {
                
            connection   = DriverManager.getConnection(connString,meno,heslo);
            stmt         = connection.createStatement();          
            sql          = "SELECT id_spolocnosti, nazov FROM spolocnost";
            ResultSet rs = stmt.executeQuery(sql);
     
            while(rs.next()){
                
                result += "Spolocnost\n"
                        + " > id spolocnosti = "+rs.getString("id_spolocnosti")+"\n"
                        + " > nazov          = "+rs.getString("nazov")+"\n\n";
                                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public String zobrazTypVozna() {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        try {
                
            connection   = DriverManager.getConnection(connString,meno,heslo);
            stmt         = connection.createStatement();          
            sql          = "SELECT rad, "
                                + "kod, "
                                + "interabilita, "
                                + "dlzka, "
                                + "hmotnost, "
                                + "loz_hmotnost, "
                                + "loz_dlzka, "
                                + "loz_sirka, "
                                + "loz_plocha, "
                                + "loz_vyska, "
                                + "loz_objem, "
                                + "poznamka, "
                                + "obrazok "
                            + "FROM typ_vozna";
            ResultSet rs = stmt.executeQuery(sql);
     
            while(rs.next()){
                
                result += "Typ vozna\n"
                        + " > rad           = "+rs.getString("rad")+"\n"
                        + " > kod           = "+rs.getString("kod")+"\n"
                        + " > interabilita  = "+rs.getString("interabilita")+"\n"
                        + " > dlzka         = "+rs.getString("dlzka")+"\n"
                        + " > hmotnost      = "+rs.getString("hmotnost")+"\n"
                        + " > loz. hmotnost = "+rs.getString("loz_hmotnost")+"\n"
                        + " > loz. dlzka    = "+rs.getString("loz_dlzka")+"\n"
                        + " > loz. sirka    = "+rs.getString("loz_sirka")+"\n"
                        + " > loz. plocha   = "+rs.getString("loz_plocha")+"\n"
                        + " > loz. vyska    = "+rs.getString("loz_vyska")+"\n"
                        + " > loz. objem    = "+rs.getString("loz_objem")+"\n"
                        + " > poznamka      = "+rs.getString("poznamka")+"\n"
                        + " > obrazok       = "+rs.getBlob("obrazok")+"\n\n";
                                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public String zobrazPouzivatel() {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        try {
                
            connection   = DriverManager.getConnection(connString,meno,heslo);
            stmt         = connection.createStatement();          
            sql          = "SELECT id_pouzivatela, rod_cislo, meno, priezvisko FROM pozivatel";
            ResultSet rs = stmt.executeQuery(sql);
     
            while(rs.next()){
                
                result += "Pouzivatel\n"
                        + " > id pouzivatela = "+rs.getString("id_pouzivatela")+"\n"
                        + " > rodne cislo    = "+rs.getString("rod_cislo")+"\n"
                        + " > meno           = "+rs.getString("meno")+"\n"
                        + " > priezvisko     = "+rs.getString("priezvisko")+"\n\n";
                                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public String zobrazZaznam() {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        try {
                
            connection   = DriverManager.getConnection(connString,meno,heslo);
            stmt         = connection.createStatement();          
            sql          = "SELECT datum, tabulka, id_pouzivatela FROM zaznam";
            ResultSet rs = stmt.executeQuery(sql);
     
            while(rs.next()){
                
                result += "Zaznam\n"
                        + " > datum          = "+rs.getString("datum")+"\n"
                        + " > tabulka        = "+rs.getString("tabulka")+"\n"
                        + " > id pouzivatela = "+rs.getString("id_pouzivatela")+"\n\n";
                                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public void pridajVozen(int pIdSpolocnosti, int pKod, int pIdVozna, int pIdSnimaca) {
        
        Connection connection = null;
        CallableStatement stmt;

        try {

            connection = DriverManager.getConnection(connString,meno,heslo);
            stmt       = connection.prepareCall("call pridaj_vozen(?,?,?,?)");

            stmt.setInt(1, pIdSpolocnosti);
            stmt.setInt(2, pKod);
            stmt.setInt(3, pIdVozna);
            stmt.setInt(4, pIdSnimaca);
            
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void vyradVozen(int pKod, int pIdVozna) {
        
        Connection connection = null;
        CallableStatement stmt;

        try {

            connection = DriverManager.getConnection(connString,meno,heslo);
            stmt       = connection.prepareCall("call vyrad_vozen_z_prevadzky(?,?)");

            stmt.setInt(1, pKod);
            stmt.setInt(2, pIdVozna);
            
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void zaradVozenDoVlaku(int pIdVlaku, int pIdVozna, int pKod, int pIdSnimaca) {
        
        Connection connection = null;
        CallableStatement stmt;

        try {

            connection = DriverManager.getConnection(connString,meno,heslo);
            stmt       = connection.prepareCall("call zarad_vozen_do_vlaku(?,?,?,?)");

            stmt.setInt(1, pIdVlaku);
            stmt.setInt(2, pIdVozna);
            stmt.setInt(3, pKod);
            stmt.setInt(4, pIdSnimaca);
            
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void vyradVozenZVlaku(int pIdVlaku, int pIdVozna, int pKod, int pIdSnimaca) {
        
        Connection connection = null;
        CallableStatement stmt;

        try {

            connection = DriverManager.getConnection(connString,meno,heslo);
            stmt       = connection.prepareCall("call vyrad_vozen_z_vlaku(?,?,?,?)");

            stmt.setInt(1, pIdVlaku);
            stmt.setInt(2, pIdVozna);
            stmt.setInt(3, pKod);
            stmt.setInt(4, pIdSnimaca);
            
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void zmenPoholuVoznaVStanici(int pIdVozna, int pKod, int pIdSnimaca, String pPoznamka) {
        
        Connection connection = null;
        CallableStatement stmt;

        try {

            connection = DriverManager.getConnection(connString,meno,heslo);
            stmt       = connection.prepareCall("call presun_vozen(?,?,?,?)");

            stmt.setInt(1, pIdVozna);
            stmt.setInt(2, pKod);
            stmt.setInt(3, pIdSnimaca);
            stmt.setString(4, pPoznamka);
            
            
            
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /***************************************************************************
    * Vystupy
    */
    
    public String zoznamVoznovVStanici(boolean pVPrevadzke, int pIdStanice, Timestamp pOd, Timestamp pDo) {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        String vPrevadzke;
        if(pVPrevadzke) {
            vPrevadzke = "A";
        } else {
            vPrevadzke = "N";
        }
        
        try {
                
            connection   = DriverManager.getConnection(connString,meno,heslo);
            stmt         = connection.createStatement();          
            sql          = "SELECT id_vozna, id_spolocnosti, kod, nazov_spolocnosti, datum_od, datum_do, nazov_stanice from ZOZNAM_VOZNOV_V_STANICI"
                         + " where "
                         + " v_prevadzke = '"+vPrevadzke+"' and "
                         + " id_stanice = "+pIdStanice+" and "
                         + " datum_od >= to_timestamp('"+pOd.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF') and "
                         + " (datum_do is null OR datum_do <= to_timestamp('"+pDo.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF'))";
                  
                    
            ResultSet rs = stmt.executeQuery(sql);
                     
     
            while(rs.next()){
                               
                result += "Vozen\n"
                        + " > V prevadzke          = "+vPrevadzke+"\n"   
                        + " > ID vozna             = "+rs.getString("id_vozna")+"\n"                     
                        + " > Patriaci spolocnosti = "+rs.getString("nazov_spolocnosti")+"\n"
                        + " > ID spolocnosti       = "+rs.getString("id_spolocnosti")+"\n"
                        + " > Kod                  = "+rs.getString("kod")+"\n"
                        + " > Nazov stanice        = "+rs.getString("nazov_stanice")+"\n"
                        + " > Cas od               = "+rs.getString("datum_od")+"\n"
                        + " > Cas do               = "+rs.getString("datum_do")+"\n\n";
                                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
        
    }
    
    public String zoznamVoznovVoVlakoch(String pIdVlaku, int pHmotnostOd, int pHmotnostDo, Timestamp pDatumOd, Timestamp pDatumDo) {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        String volitelnaPodmienka = "";
        if(!pIdVlaku.isEmpty() && !pIdVlaku.equals("") && !pIdVlaku.equals(" ")) {
            volitelnaPodmienka = " and id_vlaku = "+pIdVlaku;
        }
        
        try {
                
            connection   = DriverManager.getConnection(connString,meno,heslo);
            stmt         = connection.createStatement();          
            sql          = "select id_vozna, nazov_spolocnosti, rad, kod, interabilita, hmotnost, poznamka, id_vlaku, dat_vypravenia, zaciatok, ciel\n"
                         + " from VIEW_VOZNE_VLAKU "
                         + " where "
                         + " hmotnost >= "+pHmotnostOd+" and "
                         + " hmotnost <= "+pHmotnostDo+" and "
                         + " dat_vypravenia >= to_timestamp('"+pDatumOd.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF') and "
                         + " dat_vypravenia <= to_timestamp('"+pDatumDo.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF')"
                         + volitelnaPodmienka;
                  
            System.out.println(sql);        
            
            ResultSet rs = stmt.executeQuery(sql);
                     
     
            while(rs.next()){
                               
                result += "Vozen\n"
                        + " > ID vozna             = "+rs.getString("id_vozna")+"\n"                     
                        + " > Patriaci spolocnosti = "+rs.getString("nazov_spolocnosti")+"\n"
                        + " > Rad                  = "+rs.getString("rad")+"\n"
                        + " > Kod                  = "+rs.getString("kod")+"\n"
                        + " > Interabilita         = "+rs.getString("interabilita")+"\n"
                        + " > Hmotnost [t]         = "+rs.getString("hmotnost")+"\n"
                        + " > Poznamka             = "+rs.getString("poznamka")+"\n"
                        + " > ID vlaku             = "+rs.getString("id_vlaku")+"\n"
                        + " > Datum vypravenia     = "+rs.getString("dat_vypravenia")+"\n"
                        + " > Zaciatok             = "+rs.getString("zaciatok")+"\n"
                        + " > Ciel                 = "+rs.getString("ciel")+"\n\n";
                                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;    
    }
	
    public String zobrazAktualnuPolohuVozna(int pIdVozna, int pKodVozna) {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";

        try {

            connection   = DriverManager.getConnection(connString,meno,heslo);
            stmt         = connection.createStatement();          
            sql          = "select id_vozna, kod, poznamka, gps_sirka, gps_dlzka, datum_do from VIEW_POLOHA_VOZNOV"
                         + " where id_vozna = "+pIdVozna+" and "
                         + " kod = "+pKodVozna+" and "
                         + " datum_do is null";
            
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){

                result += "Vozen\n"
                        + " > ID vozna  = "+rs.getString("id_vozna")+"\n"
                        + " > Kod       = "+rs.getString("kod")+"\n"
                        + " > Poznamka  = "+rs.getString("poznamka")+"\n"
                        + " > GPS sirka = "+rs.getString("gps_sirka")+"\n"
                        + " > GPS dlzka = "+rs.getString("gps_dlzka")+"\n\n";

            }

        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
	
    public String zobrazHistoriuVyskytuVoznaZaObdobie(int pIdVozna, int pKodVozna, Timestamp pOd, Timestamp pDo) {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";

        try {

            connection   = DriverManager.getConnection(connString,meno,heslo);
            stmt         = connection.createStatement();          
            sql          = "select id_vozna, rad, kod, v_prevadzke, interabilita, dlzka, hmotnost, poznamka, nazov_spolocnosti, id_spolocnosti, gps_sirka, gps_dlzka, nazov, typ_pohybu, datum_od, datum_do "
                         + " from VIEW_POLOHA_VOZNOV"
                         + " where "
                         + " id_vozna = "+pIdVozna+" and "
                         + " kod = "+pKodVozna+" and "
                         + " datum_od >= to_timestamp('"+pOd.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF') and "
                         + " (datum_do is null OR datum_do <= to_timestamp('"+pDo.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF'))";
            
            ResultSet rs = stmt.executeQuery(sql);               
            
            boolean firstOutput = false;

            while(rs.next()){
                
                if(!firstOutput) {
                    
                    result += "Vozen\n"
                        + " > ID vozna             = "+rs.getString("id_vozna")+"\n"                     
                        + " > Patriaci spolocnosti = "+rs.getString("nazov_spolocnosti")+"\n"
                        + " > ID spolocnosti       = "+rs.getString("id_spolocnosti")+"\n"
                        + " > Rad                  = "+rs.getString("rad")+"\n"
                        + " > Kod                  = "+rs.getString("kod")+"\n"
                        + " > V prevadzke          = "+rs.getString("v_prevadzke")+"\n"
                        + " > Interabilita         = "+rs.getString("interabilita")+"\n"
                        + " > Dlzka                = "+rs.getString("dlzka")+"\n"
                        + " > Hmotnost [t]         = "+rs.getString("hmotnost")+"\n"
                        + " > Poznamka             = "+rs.getString("poznamka")+"\n\n"
                        + "   > Pohyb vozna\n"
                            + "      > Nazov miesta = "+rs.getString("nazov")+"\n"
                            + "      > GPS sirka    = "+rs.getString("gps_sirka")+"\n"
                            + "      > GPS dlzka    = "+rs.getString("gps_dlzka")+"\n"
                            + "      > Typ pohybu   = "+rs.getString("typ_pohybu")+"\n"
                            + "      > Datum od     = "+rs.getString("datum_od")+"\n"
                            + "      > Datum do     = "+rs.getString("datum_do")+"\n\n";
                    
                    
                    firstOutput = true;
                    
                } else {
                    
                    result += "      > Nazov miesta = "+rs.getString("nazov")+"\n"
                            + "      > GPS sirka    = "+rs.getString("gps_sirka")+"\n"
                            + "      > GPS dlzka    = "+rs.getString("gps_dlzka")+"\n"
                            + "      > Typ pohybu   = "+rs.getString("typ_pohybu")+"\n"
                            + "      > Datum od     = "+rs.getString("datum_od")+"\n"
                            + "      > Datum do     = "+rs.getString("datum_do")+"\n\n";
            
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
	
    public String zobrazenieAktualnejPolohyVoznov(String pIdSpolocnosti, String pKod, int pHmotnostOd, int pHmotnostDo, int pDlzkaOd, int pDlzkaDo, int pVPrevadzke) {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        String podmienka_idSpolocnosti = "";
        String podmienka_kod           = "";
        String podmienka_vPrevadzke    = "";
        
        if(!pIdSpolocnosti.isEmpty() && !pIdSpolocnosti.equals("") && !pIdSpolocnosti.equals(" ")) {
            podmienka_idSpolocnosti = " id_spolocnosti = "+pIdSpolocnosti+" and ";
        }
        
        if(!pKod.isEmpty() && !pKod.equals("") && !pKod.equals(" ")) {
            podmienka_kod = " kod = "+pKod+" and ";
        }
        
        if        (pVPrevadzke == 0) {
            podmienka_vPrevadzke = " v_prevadzke = 'N' and ";
        } else if (pVPrevadzke == 1) {
            podmienka_vPrevadzke = " v_prevadzke = 'A' and ";
        }

        try {

            connection   = DriverManager.getConnection(connString,meno,heslo);
            stmt         = connection.createStatement();          
            sql          = "select id_vozna, kod, rad, v_prevadzke, interabilita, dlzka, hmotnost, poznamka, nazov_spolocnosti, id_spolocnosti, gps_sirka, gps_dlzka, nazov from VIEW_POLOHA_VOZNOV"
                         + " where "
                         + " hmotnost >= "+pHmotnostOd+" and "
						 + " hmotnost <= "+pHmotnostDo+" and "
                         + " dlzka >= "+pDlzkaOd+" and "
                         + " dlzka <= "+pDlzkaDo+" and "
                         + podmienka_idSpolocnosti
                         + podmienka_kod
                         + podmienka_vPrevadzke
                         + " datum_do is null";
            
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){

                result += "Vozen\n"
                        + " > ID vozna             = "+rs.getString("id_vozna")+"\n"                     
                        + " > Patriaci spolocnosti = "+rs.getString("nazov_spolocnosti")+"\n"
                        + " > ID spolocnosti       = "+rs.getString("id_spolocnosti")+"\n"
                        + " > Rad                  = "+rs.getString("rad")+"\n"
                        + " > Kod                  = "+rs.getString("kod")+"\n"
                        + " > V prevadzke          = "+rs.getString("v_prevadzke")+"\n"
                        + " > Interabilita         = "+rs.getString("interabilita")+"\n"
                        + " > Dlzka                = "+rs.getString("dlzka")+"\n"
                        + " > Hmotnost [t]         = "+rs.getString("hmotnost")+"\n"
                        + " > Poznamka             = "+rs.getString("poznamka")+"\n"
                        + " > Aktualna poloha vozna\n"
                        + "    > Nazov miesta = "+rs.getString("nazov")+"\n"
                        + "    > GPS sirka    = "+rs.getString("gps_sirka")+"\n"
                        + "    > GPS dlzka    = "+rs.getString("gps_dlzka")+"\n\n";
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
		
        return result;
    }
        
    public String zobrazSkupinyVoznov(
           String pKod, 
           String pRad,
           int    pVPrevadzke,
           int    pInterabilitaOd, 
           int    pInterabilitaDo,
           int    pDlzkaOd,
           int    pDlzkaDo,
           int    pHmotnostOd,
           int    pHmotnostDo,
           String pIdSpolocnosti) 
    {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        String podmienka_idSpolocnosti = "";
        String podmienka_kod           = "";
        String podmienka_vPrevadzke    = "";
        String podmienka_rad           = "";
        
        if(!pIdSpolocnosti.isEmpty() && !pIdSpolocnosti.equals("") && !pIdSpolocnosti.equals(" ")) {
            podmienka_idSpolocnosti = " id_spolocnosti = "+pIdSpolocnosti+" and ";
        }
        
        if(!pKod.isEmpty() && !pKod.equals("") && !pKod.equals(" ")) {
            podmienka_kod = " kod = "+pKod+" and ";
        }
        
        if(!pRad.isEmpty() && !pRad.equals("") && !pRad.equals(" ")) {
            podmienka_rad = " rad = '"+pRad+"' and ";
        }
        
        if        (pVPrevadzke == 0) {
            podmienka_vPrevadzke = " v_prevadzke = 'N' and ";
        } else if (pVPrevadzke == 1) {
            podmienka_vPrevadzke = " v_prevadzke = 'A' and ";
        }
        
        try {

            connection   = DriverManager.getConnection(connString,meno,heslo);
            stmt         = connection.createStatement();          
            sql          = "select id_vozna, kod, rad, v_prevadzke, interabilita, dlzka, hmotnost, loz_hmotnost, loz_dlzka, loz_sirka, loz_plocha, loz_vyska, loz_objem, poznamka, nazov, id_spolocnosti from VIEW_VSETKY_VOZNE"
                         + " where "
                         + podmienka_idSpolocnosti
                         + podmienka_kod
                         + podmienka_rad
                         + podmienka_vPrevadzke  
                         + " hmotnost >= "+pHmotnostOd+" and "
			 + " hmotnost <= "+pHmotnostDo+" and "
                         + " dlzka >= "+pDlzkaOd+" and "
                         + " dlzka <= "+pDlzkaDo+" and "
                         + " interabilita >= "+pInterabilitaOd+" and "
                         + " interabilita <= "+pInterabilitaDo;
                                  
            
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){

                result += "Vozen\n"
                        + " > ID vozna             = "+rs.getString("id_vozna")+"\n"                     
                        + " > Patriaci spolocnosti = "+rs.getString("nazov")+"\n"
                        + " > ID spolocnosti       = "+rs.getString("id_spolocnosti")+"\n"
                        + " > Rad                  = "+rs.getString("rad")+"\n"
                        + " > Kod                  = "+rs.getString("kod")+"\n"
                        + " > V prevadzke          = "+rs.getString("v_prevadzke")+"\n"
                        + " > Interabilita         = "+rs.getString("interabilita")+"\n"
                        + " > Dlzka                = "+rs.getString("dlzka")+"\n"
                        + " > Hmotnost [t]         = "+rs.getString("hmotnost")+"\n"
                        + " > loz. hmotnost        = "+rs.getString("loz_hmotnost")+"\n"
                        + " > loz. dlzka           = "+rs.getString("loz_dlzka")+"\n"
                        + " > loz. sirka           = "+rs.getString("loz_sirka")+"\n"
                        + " > loz. plocha          = "+rs.getString("loz_plocha")+"\n"
                        + " > loz. vyska           = "+rs.getString("loz_vyska")+"\n"
                        + " > loz. objem           = "+rs.getString("loz_objem")+"\n"
                        + " > Poznamka             = "+rs.getString("poznamka")+"\n\n";
                        
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
		
        return result;
        
    }
	
	//**************************************************************************************
	//vystupy -> modely tabulky
	
	
	public DefaultTableModel tableModelVsetkyStanice(){
		try {
			Connection connection = null;
			Statement  stmt;
			String     sql;
			String     result = "";
			
			
			connection = DriverManager.getConnection(connString,meno,heslo);
			stmt       = connection.createStatement();
			
			sql          = "SELECT id_stanice, nazov, gps_sirka, gps_dlzka FROM stanica";
			ResultSet rs = stmt.executeQuery(sql);
			
			return buildTableModel(rs);
		} catch (SQLException ex) {
			Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
	
	public DefaultTableModel tableModelVoznovVStanici(boolean pVPrevadzke, int pIdStanice, Timestamp pOd, Timestamp pDo) {
        
		try {
			Connection connection = null;
			Statement  stmt;
			String     sql;
			String     result = "";
			
			String vPrevadzke;
			if(pVPrevadzke) {
				vPrevadzke = "A";
			} else {
				vPrevadzke = "N";
			}
	
			connection   = DriverManager.getConnection(connString,meno,heslo);
			stmt         = connection.createStatement();
			sql          = "SELECT id_vozna, id_spolocnosti, kod, nazov_spolocnosti, datum_od, datum_do, nazov_stanice from ZOZNAM_VOZNOV_V_STANICI"
					+ " where "
					+ " v_prevadzke = '"+vPrevadzke+"' and "
					+ " id_stanice = "+pIdStanice+" and "
					+ " datum_od >= to_timestamp('"+pOd.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF') and "
					+ " (datum_do is null OR datum_do <= to_timestamp('"+pDo.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF'))";
			
			
			ResultSet rs = stmt.executeQuery(sql);
			
			return buildTableModel(rs);
			
		} catch (SQLException ex) {
			Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
	
	public DefaultTableModel tableModelVoznovVoVlakoch(String pIdVlaku, int pHmotnostOd, int pHmotnostDo, Timestamp pDatumOd, Timestamp pDatumDo) {
        
		try {
			Connection connection = null;
			Statement  stmt;
			String     sql;
			String     result = "";
			
			String volitelnaPodmienka = "";
			if(!pIdVlaku.isEmpty() && !pIdVlaku.equals("") && !pIdVlaku.equals(" ")) {
				volitelnaPodmienka = " and id_vlaku = "+pIdVlaku;
			}
			
			
			
			connection   = DriverManager.getConnection(connString,meno,heslo);
			stmt         = connection.createStatement();
			sql          = "select id_vozna, nazov_spolocnosti, rad, kod, interabilita, hmotnost, poznamka, id_vlaku, dat_vypravenia, zaciatok, ciel\n"
					+ " from VIEW_VOZNE_VLAKU "
					+ " where "
					+ " hmotnost >= "+pHmotnostOd+" and "
					+ " hmotnost <= "+pHmotnostDo+" and "
					+ " dat_vypravenia >= to_timestamp('"+pDatumOd.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF') and "
					+ " dat_vypravenia <= to_timestamp('"+pDatumDo.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF')"
					+ volitelnaPodmienka;
			
			System.out.println(sql);
			
			ResultSet rs = stmt.executeQuery(sql);
			
			return buildTableModel(rs);
			
		} catch (SQLException ex) {
			Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
	
	public DefaultTableModel tableModelAktualnaPolohuVozna(int pIdVozna, int pKodVozna) {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";

        try {

            connection   = DriverManager.getConnection(connString,meno,heslo);
            stmt         = connection.createStatement();          
            sql          = "select id_vozna, kod, poznamka, gps_sirka, gps_dlzka, datum_do from VIEW_POLOHA_VOZNOV"
                         + " where id_vozna = "+pIdVozna+" and "
                         + " kod = "+pKodVozna+" and "
                         + " datum_do is null";
            
            ResultSet rs = stmt.executeQuery(sql);
			
			return buildTableModel(rs);

        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
	
	public DefaultTableModel tableModelHistoriaVyskytuVoznaZaObdobie(int pIdVozna, int pKodVozna, Timestamp pOd, Timestamp pDo) {
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";

        try {

            connection   = DriverManager.getConnection(connString,meno,heslo);
            stmt         = connection.createStatement();          
            sql          = "select id_vozna, rad, kod, v_prevadzke, interabilita, dlzka, hmotnost, poznamka, nazov_spolocnosti, id_spolocnosti, gps_sirka, gps_dlzka, nazov, typ_pohybu, datum_od, datum_do "
                         + " from VIEW_POLOHA_VOZNOV"
                         + " where "
                         + " id_vozna = "+pIdVozna+" and "
                         + " kod = "+pKodVozna+" and "
                         + " datum_od >= to_timestamp('"+pOd.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF') and "
                         + " (datum_do is null OR datum_do <= to_timestamp('"+pDo.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF'))";
            
            ResultSet rs = stmt.executeQuery(sql);               
			
			return buildTableModel(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
	
	public DefaultTableModel tableModelAktualnaPolohyVoznov(String pIdSpolocnosti, String pKod, int pHmotnostOd, int pHmotnostDo, int pDlzkaOd, int pDlzkaDo, int pVPrevadzke) {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        String podmienka_idSpolocnosti = "";
        String podmienka_kod           = "";
        String podmienka_vPrevadzke    = "";
        
        if(!pIdSpolocnosti.isEmpty() && !pIdSpolocnosti.equals("") && !pIdSpolocnosti.equals(" ")) {
            podmienka_idSpolocnosti = " id_spolocnosti = "+pIdSpolocnosti+" and ";
        }
        
        if(!pKod.isEmpty() && !pKod.equals("") && !pKod.equals(" ")) {
            podmienka_kod = " kod = "+pKod+" and ";
        }
        
        if        (pVPrevadzke == 0) {
            podmienka_vPrevadzke = " v_prevadzke = 'N' and ";
        } else if (pVPrevadzke == 1) {
            podmienka_vPrevadzke = " v_prevadzke = 'A' and ";
        }

        try {

            connection   = DriverManager.getConnection(connString,meno,heslo);
            stmt         = connection.createStatement();          
            sql          = "select id_vozna, kod, rad, v_prevadzke, interabilita, dlzka, hmotnost, poznamka, nazov_spolocnosti, id_spolocnosti, gps_sirka, gps_dlzka, nazov from VIEW_POLOHA_VOZNOV"
                         + " where "
                         + " hmotnost >= "+pHmotnostOd+" and "
						 + " hmotnost <= "+pHmotnostDo+" and "
                         + " dlzka >= "+pDlzkaOd+" and "
                         + " dlzka <= "+pDlzkaDo+" and "
                         + podmienka_idSpolocnosti
                         + podmienka_kod
                         + podmienka_vPrevadzke
                         + " datum_do is null";
            
            ResultSet rs = stmt.executeQuery(sql);

			return buildTableModel(rs);
			
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
		
        return null;
    }
	
	private DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {

		ResultSetMetaData metaData = rs.getMetaData();

		// names of columns
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for (int column = 1; column <= columnCount; column++) {
		    columnNames.add(metaData.getColumnName(column));
		}

		// data of the table
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				vector.add(rs.getObject(columnIndex));
			}
			data.add(vector);
		}
		return new DefaultTableModel(data, columnNames);
	}
	
	public void zobrazObrazok(String pKod, JDialog dialog, JLabel image){
		try {
			Connection connection = null;
			Statement  stmt;
			String     sql;
			String     result = "";
			
			connection   = DriverManager.getConnection(connString,meno,heslo);
			stmt         = connection.createStatement();
			sql          = "select obrazok from typ_vozna where kod = " + pKod;
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()){
				InputStream in = rs.getBinaryStream("OBRAZOK");
				
				BufferedImage im = ImageIO.read(in); 
				BufferedImage outimage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
				Graphics2D g = outimage.createGraphics();

				dialog.setSize((int)outimage.getWidth()+50, (int)outimage.getHeight()+90);
				image.setSize((int)outimage.getWidth(), (int)outimage.getHeight());
				
				
				float xScale = (float)image.getWidth() / outimage.getWidth();
				float yScale = (float)image.getHeight() / outimage.getHeight();

				AffineTransform at = AffineTransform.getScaleInstance(xScale,yScale);
				g.drawRenderedImage(im,at);
				g.dispose();
				Image scaledImage = outimage.getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_SMOOTH);
				ImageIcon icon = new ImageIcon(scaledImage);
				image.setIcon(icon);
				image.revalidate();
				
			}
			rs.close();
		} catch (SQLException ex) {
			Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
        
}

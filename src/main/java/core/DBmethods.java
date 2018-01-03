package main.java.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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

    public void vytvorPohybVoznaVlak(int pTypPohybu, int pIdSnimaca, int pIdVlaku) {

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



}

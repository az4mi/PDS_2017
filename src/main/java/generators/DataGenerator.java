package generators;

import core.DBmethods;

/**
 * Created by mi5ho on 03.01.2018.
 */
public class DataGenerator {

    public static void main(String[] args) {

        DBmethods dbmethods = new DBmethods();
        
        //System.out.println(dbmethods.zobrazVlak());
        System.out.println(dbmethods.zobrazPohybVoznaVlak());

//        dbmethods.vytvorVlak(1,9,1,"22-08-17","");

//        dbmethods.vytvorPohybVoznaVlak('a',1,61);

    }

}

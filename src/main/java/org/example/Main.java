package org.example;

import java.util.ArrayList;
import java.sql.SQLException;
import java.util.Date;
import java.sql.ResultSet;

/**
 1.	In dieser Klasse wird die main-Methode definiert.
 2.	Es wird eine ArrayList ps von Person-Objekten erstellt.
 3.	Es wird eine Verbindung zur Datenbank hergestellt und eine Abfrage (select * from person) ausgeführt, um Daten aus der Datenbank abzurufen und in ps zu speichern.
 4.	Ein Objekt der Klasse PersonView wird erstellt, um das Hauptfenster für die Anzeige von Personendaten zu erzeugen.
 */

public class Main {
    ArrayList<Person> ps;

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ArrayList<Person> ps = new  ArrayList<Person>() ;
        Connection1 con=new Connection1();
        String qd="select * from person ";
        ResultSet res= con.statement.executeQuery(qd);
        while(res.next())
        {
            Date d=res.getDate(5);
            Person p =new Person(res.getInt(1),res.getString(2),res.getString(3),res.getString(4),res.getString(6),res.getString(7),d,res.getInt(8));
            ps.add(p);
        }
        PersonView f =new PersonView(ps,con);
    }
}



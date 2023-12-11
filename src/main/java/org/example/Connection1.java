package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 1.	Diese Klasse ist für die Datenbankverbindung gemacht.
 2.	Sie verwendet JDBC, um eine Verbindung zur Datenbank herzustellen und ein Statement für Datenbankabfragen zu erstellen.
 */
public class Connection1 {

    String url = "jdbc:mysql://localhost:3306/ali_projekt";
    String user = "root";
    String password = "";
    Connection connection;
    Statement statement;       //App                             // Database
    public Connection1() throws ClassNotFoundException, SQLException{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        statement= connection.createStatement();
    }

}


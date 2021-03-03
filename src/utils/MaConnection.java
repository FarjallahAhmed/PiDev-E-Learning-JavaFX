/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author AMINE N
 */
public class MaConnection {
    final static String URL="jdbc:mysql://127.0.0.1:3306/projetpidev";
    final static String LOGIN="root";
    final static String PWD="";
    static MaConnection instance=null;
    private Connection cnx;
    private MaConnection()
    {
        try {
            cnx=DriverManager.getConnection(URL,LOGIN,PWD);
            System.out.println("Connexion etablie");
        } catch (SQLException e) {
            System.out.println("pas de connexion");
        }
    }
    public static MaConnection getInstance()
    {
        if(instance==null)
        {
            instance=new MaConnection();
            
        }
        return instance;
    }
    public Connection getConnection()
    {
        return cnx;
    }
}

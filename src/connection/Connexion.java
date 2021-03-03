/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

/**
 *
 * @author dell
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
        
public class Connexion {
    private String
    url="jdbc:mysql://localhost:3306/elearning";
    private String login="root";
    private String pwd="";
    private Connection connexion;
    private static Connexion instance;
    public Connexion()
    {
        try {
            connexion = DriverManager.getConnection(url,login,pwd);
            System.out.println("connexion établie");
        } catch (SQLException ex) {
            System.err.println("connexion n'est pas établie");
        }
    }
    public static Connexion getIstance()
    {
        if(instance==null)
            instance = new Connexion();
        return instance;
    }
    public Connection getConnection()
    {
        return connexion;
    }
}

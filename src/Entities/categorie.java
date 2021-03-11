/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

//import com.sun.org.glassfish.gmbal.Description;

/**
 *
 * @author AMINE N
 */
public class categorie {
    private int id_categorie;
    private String nom;
    private String type;
    private String Description ;
    

    public categorie(int id_categorie, String nom, String type, String Description) {
        this.id_categorie = id_categorie;
        this.nom = nom;
        this.type = type;
        this.Description = Description;
    }

    public categorie(int id_categorie, String nom) {
        this.id_categorie = id_categorie;
        this.nom = nom;
    }

    public categorie() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(int id_categorie) {
        this.id_categorie = id_categorie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }
    
}

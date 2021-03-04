/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author rania
 */
public class categorie {
    private int id_categorie;
    private String nom;
    private String type;
    private String description;

    public categorie() {
    }

    public categorie(int id_categorie, String nom, String type, String description) {
        this.id_categorie = id_categorie;
        this.nom = nom;
        this.type = type;
        this.description = description;
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
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "categorie{" + "id_categorie=" + id_categorie + ", nom=" + nom + ", type=" + type + ", description=" + description + '}';
    }
    
    
}

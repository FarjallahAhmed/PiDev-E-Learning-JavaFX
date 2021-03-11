/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Date;

/**
 *
 * @author Asus
 */
public class projet{
    
    protected int id;
    protected String nom;
    protected String sujet;
    protected String description;
    protected Date date_creation;

    public projet(String nom, String sujet, String description, Date date_creation) {
        this.nom = nom;
        this.sujet = sujet;
        this.description = description;
        this.date_creation = date_creation;
    }

    public projet() {
    }
    
    

    public projet(int id, String nom, String sujet, String description, Date date_creation) {
        this.id = id;
        this.nom = nom;
        this.sujet = sujet;
        this.description = description;
        this.date_creation = date_creation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    @Override
    public String toString() {
        return "projet{" + "id=" + id + ", nom=" + nom + ", sujet=" + sujet + ", description=" + description + ", date_creation=" + date_creation + '}';
    }
    
    

    
    
    

}

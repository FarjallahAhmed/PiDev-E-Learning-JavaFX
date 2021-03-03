/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.Date;

/**
 *
 * @author Asus
 */
public class Activite {
    
    protected int id_activite;
    protected String nom;
    protected String sujet;
    protected String description;
    protected Date date_creation;

    public Activite() {
    }

    public Activite(int id_activite, String nom, String sujet, String description, Date date_creation) {
        this.id_activite = id_activite;
        this.nom = nom;
        this.sujet = sujet;
        this.description = description;
        this.date_creation = date_creation;
    }

    public int getId_activite() {
        return id_activite;
    }

    public void setId_activite(int id_activite) {
        this.id_activite = id_activite;
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
        return "Activite{" + "id_activite=" + id_activite + ", nom=" + nom + ", sujet=" + sujet + ", description=" + description + ", date_creation=" + date_creation + '}';
    }
    

    
}

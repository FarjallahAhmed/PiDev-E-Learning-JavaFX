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
public class Activite extends projet{
    
    protected int id;
    protected String responsable;
    protected String periode;
    protected String nom;

  
    
    
    
    public Activite() {
    }

    public Activite(int id, String responsable, String periode, String nom) {
        this.id = id;
        this.responsable = responsable;
        this.periode = periode;
        this.nom = nom;
    }

    public Activite(String responsable, String periode, String nom) {
        this.responsable = responsable;
        this.periode = periode;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public String getResponsable() {
        return responsable;
    }

    public String getPeriode() {
        return periode;
    }

    public String getNom() {
        return nom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Activite{" + "id=" + id + ", responsable=" + responsable + ", periode=" + periode + ", nom=" + nom + '}';
    }
    
    
    
    
    

    

    
    

    
}

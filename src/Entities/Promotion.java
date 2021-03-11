/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Date;

/**
 *
 * @author dell
 */
public class Promotion {
    
    private int id;
    private String type;
    private Date dateDebut;
    private Date dateFin;
    private float prix;

    public Promotion() {
    }

    public Promotion(int id, String type, Date dateDebut, Date dateFin, float prix) {
        this.id = id;
        this.type = type;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public float getPrix() {
        return prix;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Promotion{" + "id=" + id + ", type=" + type + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", prix=" + prix + '}';
    }
    
    
    
    
}

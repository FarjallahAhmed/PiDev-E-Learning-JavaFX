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

    
    private int id_f;
    
    
    
    //attribut join
    
    private String objet;

    private float cout_f;

    public Promotion(Date dateDebut, Date dateFin, float prix, String objet, String type, float cout_f) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prix = prix;
        this.objet = objet;
        this.type = type;
        this.cout_f = cout_f;
    }
    
    

    public Promotion(int id, int id_f, Date dateDebut, Date dateFin, float prix) {
        this.id = id;
        this.id_f = id_f;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prix = prix;
    }


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


    public int getId_f() {
        return id_f;
    }

    public void setId_f(int id_f) {
        this.id_f = id_f;
    }

    public String getObjet() {
        return objet;
    }

    public float getCout_f() {
        return cout_f;
    }
    
    
    
    
    
    
    
}

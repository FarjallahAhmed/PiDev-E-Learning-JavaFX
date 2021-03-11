/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Date;

/**
 *
 * @author User
 */
public class Commande {
    private int id;
    private Date date;
    private float prix;
    private String etat;
    private int id_client;
    private int id_formation;

    public Commande() {
    }

    public Commande(int id, Date date, float prix, String etat) {
        this.id = id;
        this.date = date;
        this.prix = prix;
        this.etat = etat;
    }

    public Commande(Date date, float prix, String etat, int id_client, int id_formation) {
        this.date = date;
        this.prix = prix;
        this.etat = etat;
        this.id_client = id_client;
        this.id_formation = id_formation;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public int getId_formation() {
        return id_formation;
    }

    public void setId_formation(int id_formation) {
        this.id_formation = id_formation;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Commande{" + "id=" + id + ", date=" + date + ", prix=" + prix + ", etat=" + etat + "}\n";
    }
    
    
    
}

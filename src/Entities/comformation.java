/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author User
 */
public class comformation {
    private String Objet;
    private String Type;
    private String Objectif;
      private float prix;
    private String etat;
    private int id;
    private int id_formation;

    public comformation(String Objet, String Type, String Objectif, float prix, String etat, int id_formation) {
        this.Objet = Objet;
        this.Type = Type;
        this.Objectif = Objectif;
        this.prix = prix;
        this.etat = etat;
       
        this.id_formation = id_formation;
    }

     public comformation(String Objet, String Type, String Objectif, float prix, String etat, int id_formation, int id) {
        this.Objet = Objet;
        this.Type = Type;
        this.Objectif = Objectif;
        this.prix = prix;
        this.etat = etat;
       this.id = id;
        this.id_formation = id_formation;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_formation() {
        return id_formation;
    }

    public void setId_formation(int id_formation) {
        this.id_formation = id_formation;
    }

    public comformation(String Objet, String Type, String Objectif, float prix, String etat) {
        this.Objet = Objet;
        this.Type = Type;
        this.Objectif = Objectif;
        this.prix = prix;
        this.etat = etat;
    }

    public String getObjet() {
        return Objet;
    }

    public void setObjet(String Objet) {
        this.Objet = Objet;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getObjectif() {
        return Objectif;
    }

    public void setObjectif(String Objectif) {
        this.Objectif = Objectif;
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
    
    
    
    
}

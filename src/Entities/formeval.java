/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Date;

/**
 *
 * @author AMINE N
 */
public class formeval {
     //private int Id;
    private String Objet;
    private String Type;
    private String Objectif;
    private int nb_participants;
    private float cout_hj;
    private int nb_jour;
    private float cout_fin;
    private Date date_reelle;
    private Date date_prevu;
    private String path;
     private int note;
    private String rapport;
    private int id_formation;

    public formeval(String Objet, String Type, String Objectif, int nb_participants, float cout_hj, int nb_jour, float cout_fin, int note, String rapport) {
        this.Objet = Objet;
        this.Type = Type;
        this.Objectif = Objectif;
        this.nb_participants = nb_participants;
        this.cout_hj = cout_hj;
        this.nb_jour = nb_jour;
        this.cout_fin = cout_fin;
        //this.path = path;
        this.note = note;
        this.rapport = rapport;
        
       // this.id_formation = id_formation;
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

    public int getNb_participants() {
        return nb_participants;
    }

    public void setNb_participants(int nb_participants) {
        this.nb_participants = nb_participants;
    }

    public float getCout_hj() {
        return cout_hj;
    }

    public void setCout_hj(float cout_hj) {
        this.cout_hj = cout_hj;
    }

    public int getNb_jour() {
        return nb_jour;
    }

    public void setNb_jour(int nb_jour) {
        this.nb_jour = nb_jour;
    }

    public float getCout_fin() {
        return cout_fin;
    }

    public void setCout_fin(float cout_fin) {
        this.cout_fin = cout_fin;
    }

    public Date getDate_reelle() {
        return date_reelle;
    }

    public void setDate_reelle(Date date_reelle) {
        this.date_reelle = date_reelle;
    }

    public Date getDate_prevu() {
        return date_prevu;
    }

    public void setDate_prevu(Date date_prevu) {
        this.date_prevu = date_prevu;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getRapport() {
        return rapport;
    }

    public void setRapport(String rapport) {
        this.rapport = rapport;
    }

    public int getId_formation() {
        return id_formation;
    }

    public void setId_formation(int id_formation) {
        this.id_formation = id_formation;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.sql.Time;
import java.sql.Date;

/**
 *
 * @author dell
 */
public class Workshop {
    
    private int id;
    private String nomCalendar;
    private String nomEvent;
    private Date dateDebut;
    private Date dateFin;
    private Time hDebut;
    private Time hFin;
    private String lieu;
    private int nbParticipant;
    private String type;
    private String description;
    private float prix;

    public Workshop() {
    }

    public Workshop(int id, String nomEvent, Date dateDebut, Date dateFin, Time hDebut, Time hFin, String lieu, int nbParticipant, String type, String description, float prix) {
        this.id = id;
        this.nomEvent = nomEvent;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.hDebut = hDebut;
        this.hFin = hFin;
        this.lieu = lieu;
        this.nbParticipant = nbParticipant;
        this.type = type;
        this.description = description;
        this.prix = prix;
    }

    public Workshop(int id, String nomCalendar, String nomEvent, Date dateDebut, Date dateFin, Time hDebut, Time hFin, String lieu, int nbParticipant, String type, String description, float prix) {
        this.id = id;
        this.nomCalendar = nomCalendar;
        this.nomEvent = nomEvent;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.hDebut = hDebut;
        this.hFin = hFin;
        this.lieu = lieu;
        this.nbParticipant = nbParticipant;
        this.type = type;
        this.description = description;
        this.prix = prix;
    }
    



    public int getId() {
        return id;
    }

    public String getNomEvent() {
        return nomEvent;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public Time gethDebut() {
        return hDebut;
    }

    public Time gethFin() {
        return hFin;
    }

    public String getLieu() {
        return lieu;
    }

    public int getNbParticipant() {
        return nbParticipant;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public float getPrix() {
        return prix;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNomEvent(String nomEvent) {
        this.nomEvent = nomEvent;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public void sethDebut(Time hDebut) {
        this.hDebut = hDebut;
    }

    public void sethFin(Time hFin) {
        this.hFin = hFin;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public void setNbParticipant(int nbParticipant) {
        this.nbParticipant = nbParticipant;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getNomCalendar() {
        return nomCalendar;
    }

    public void setNomCalendar(String nomCalendar) {
        this.nomCalendar = nomCalendar;
    }
    

    @Override
    public String toString() {
        return "Workshop{" + "id=" + id + 
                ", nomEvent=" + nomEvent + 
                ", dateDebut=" + dateDebut + 
                ", dateFin=" + dateFin + 
                ", hDebut=" + hDebut + 
                ", hFin=" + hFin + 
                ", lieu=" + lieu + 
                ", nbParticipant=" + nbParticipant + 
                ", type=" + type + 
                ", description=" + description + 
                ", prix=" + prix + '}'+"\n";
    }
    
    
    
}

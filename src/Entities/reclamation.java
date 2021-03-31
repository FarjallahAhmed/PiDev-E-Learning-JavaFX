/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.sql.Date;

/**
 *
 * @author rania
 */
public class reclamation {
   private int id_reclamation;
   private Utilisateurs user;
   private String objet;
   private Message message;
   private Date date;

    public reclamation() {
    }

    public reclamation(int id_reclamation, Utilisateurs user, String objet, Message message) {
        this.id_reclamation = id_reclamation;
        this.user = user;
        this.objet = objet;
        this.message = message;
    }

    public int getId_reclamation() {
        return id_reclamation;
    }

    public void setId_reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }

    public Utilisateurs getId_user() {
        return user;
    }

    public void setId_user(Utilisateurs user) {
        this.user = user;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "reclamation{" + "id_reclamation=" + id_reclamation + ", user=" + user + ", objet=" + objet + ", message=" + message + '}';
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public reclamation(int id_reclamation, Utilisateurs user, String objet, Message message, Date date) {
        this.id_reclamation = id_reclamation;
        this.user = user;
        this.objet = objet;
        this.message = message;
        this.date = date;
    }

    
   
   
   
    
}

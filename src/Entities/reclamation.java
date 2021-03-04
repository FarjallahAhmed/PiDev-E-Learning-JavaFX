/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author rania
 */
public class reclamation {
   private int id_reclamation;
   private int id_user;
   private String objet;
   private String message;

    public reclamation() {
    }

    public reclamation(int id_reclamation, int id_user, String objet, String message) {
        this.id_reclamation = id_reclamation;
        this.id_user = id_user;
        this.objet = objet;
        this.message = message;
    }

    public int getId_reclamation() {
        return id_reclamation;
    }

    public void setId_reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "reclamation{" + "id_reclamation=" + id_reclamation + ", id_user=" + id_user + ", objet=" + objet + ", message=" + message + '}';
    }
   
   
   
    
}

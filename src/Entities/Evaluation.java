/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author AMINE N
 */
public class Evaluation {
    private int id;
    private int note;
    private String rapport;
    private int id_formation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Evaluation{" + "id=" + id + ", note=" + note + ", rapport=" + rapport + ", id_formation=" + id_formation + '}';
    }
    
}

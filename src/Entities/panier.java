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
public class panier {
    private int id;
    private int id_client;
    private float prix_total;
    private int nombre;

    public panier() {
       
    }

    public panier(int id_client) {
        this.id_client = id_client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public float getPrix_total() {
        return prix_total;
    }

    public void setPrix_total(float prix_total) {
        this.prix_total = prix_total;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public panier(int id, int id_client, float prix_total, int nombre) {
        this.id = id;
        this.id_client = id_client;
        this.prix_total = prix_total;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "panier{" + "id=" + id + ", id_client=" + id_client + ", prix_total=" + prix_total + ", nombre=" + nombre + '}';
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.sql.Date;

/**
 *
 * @author Mehdi
 */
public class Utilisateurs {
    
    protected int id;
    protected String nom;
    protected String prenom;
    protected Date dateNaissance;
    protected String cin;
    protected String email;
    protected String login;
    protected String password;
    protected String num;
    protected String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    

    public Utilisateurs(int id, String nom, String prenom, String cin, String email, String login, String password, String num) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = null;
        this.cin = cin;
        this.email = email;
        this.login = login;
        this.password = password;
        this.num = num;
    }
    
    
    public Utilisateurs ()
    {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Utilisateurs{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", dateNaissance=" + dateNaissance + ", cin=" + cin + ", email=" + email + ", login=" + login + ", password=" + password + ", num=" + num + '}';
    }

   
    
    
    
    
    
    
    
}

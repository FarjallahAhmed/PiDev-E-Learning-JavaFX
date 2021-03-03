/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Etities;

import java.io.File;

/**
 *
 * @author Mehdi
 */
public class Formateurs extends Utilisateurs  {
    
    private String specialite;
    private String justificatif;
    private String portefolio;
    private String cv;

    public Formateurs() {
        super();
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getJustificatif() {
        return justificatif;
    }

    public void setJustificatif(String justificatif) {
        this.justificatif = justificatif;
    }

    public String getPortefolio() {
        return portefolio;
    }

    public void setPortefolio(String portefolio) {
        this.portefolio = portefolio;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }
    
    
    
    
    
    
    
}

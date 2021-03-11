/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author Mehdi
 */
public class Participants extends Utilisateurs {
    
    
    private String niveauEtude;
    private int certificatsObtenus;
    private String interssePar;
    private int nombreDeFormation;
    
    
    public Participants ()
    {
        super();
    }

    public String getNiveauEtude() {
        return niveauEtude;
    }

    public void setNiveauEtude(String niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    public int getCertificatsObtenus() {
        return certificatsObtenus;
    }

    public void setCertificatsObtenus(int certificatsObtenus) {
        this.certificatsObtenus = certificatsObtenus;
    }

    public String getInterssePar() {
        return interssePar;
    }

    public void setInterssePar(String interssePar) {
        this.interssePar = interssePar;
    }

    public int getNombreDeFormation() {
        return nombreDeFormation;
    }

    public void setNombreDeFormation(int nombreDeFormation) {
        this.nombreDeFormation = nombreDeFormation;
    }

    @Override
    public String toString() {
        
        return super.toString()+"Participants{" + "niveauEtude=" + niveauEtude + ", certificatsObtenus=" + certificatsObtenus + ", interssePar=" + interssePar + ", nombreDeFormation=" + nombreDeFormation + '}';
    }

   
    
    
    
    
}

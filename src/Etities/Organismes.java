/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Etities;

/**
 *
 * @author Mehdi
 */
public class Organismes extends Utilisateurs {
    
    private int idOrganisme;
    private String nomOrganisme;
    private String typeOrganisme;
    
    
    public Organismes ()
    {
        
    }

    public int getIdOrganisme() {
        return idOrganisme;
    }

    public void setIdOrganisme(int idOrganisme) {
        this.idOrganisme = idOrganisme;
    }

    public String getNomOrganisme() {
        return nomOrganisme;
    }

    public void setNomOrganisme(String nomOrganisme) {
        this.nomOrganisme = nomOrganisme;
    }

    public String getTypeOrganisme() {
        return typeOrganisme;
    }

    public void setTypeOrganisme(String typeOrganisme) {
        this.typeOrganisme = typeOrganisme;
    }
    
    
}

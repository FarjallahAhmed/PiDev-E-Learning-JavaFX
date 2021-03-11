/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.categorie;
import javafx.collections.ObservableList;

/**
 *
 * @author rania
 */
public interface IServicecategorie {
          public void ajouter_categorie(categorie c);
    public ObservableList<categorie>Afficher_categorie();
    public void supprimer_categorie(int id_categorie);
    public void modifier_categorie (categorie c);
    
}

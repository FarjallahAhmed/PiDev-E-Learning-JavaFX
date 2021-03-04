/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.reclamation;
import javafx.collections.ObservableList;

/**
 *
 * @author rania
 */
public interface IServicereclamation {
      public void ajouter_reclamation(reclamation r);
    public ObservableList<reclamation>Afficher_reclamation();
    public void supprimer_reclamation(int id_reclamation);
    public void modifier_reclamation (reclamation r);
}

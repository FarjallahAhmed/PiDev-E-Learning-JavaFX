/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Commande;
import Entities.Formations;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author User
 */
public interface IServiceCommande {
    public void AjouterCommande(Commande c);
    public ObservableList<Commande>AfficherCommande(int id);
    public void supprimercommande(int id);
    public void ModifierCommande(Commande c);
}

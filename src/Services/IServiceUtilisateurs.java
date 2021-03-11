/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Utilisateurs;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Mehdi
 */
public interface IServiceUtilisateurs  {
    
    
    public int AjouterUtilisateur(Utilisateurs u);
    public ObservableList <Utilisateurs> AfficherUtlisaterus() throws SQLException;
    public void SupprimerUtilisateur(int id);
    public ObservableList <Utilisateurs> getSelectedUser(int id) throws SQLException;
    public void ModifierUtilisateur(Utilisateurs u) throws SQLException;
    
    
    
    
}

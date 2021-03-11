/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Activite;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author Asus
 */
public interface ActiviteService<T> {
    
    void ajouter(T t) throws SQLException;
    void delete(int t) throws SQLException;
    void update(T t) throws SQLException;
    ObservableList<T> readAll() throws SQLException;
}

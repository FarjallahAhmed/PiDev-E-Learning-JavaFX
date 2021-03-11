/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Workshop;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author dell
 * @param <T>
 */
public interface workshopService<T> {
    void ajouter(T t) throws SQLException;
    void delete(int t) throws SQLException;
    void update(T t) throws SQLException;
    ObservableList<T> readAll() throws SQLException;
    T find(int id) throws SQLException;
}

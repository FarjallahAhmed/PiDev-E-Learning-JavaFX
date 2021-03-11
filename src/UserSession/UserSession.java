/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserSession;

import java.util.HashSet;
import java.util.Set;

  public final class UserSession {

    private static UserSession instance;
    private String userName;
    private int id;
    private String type;
   

    private UserSession(String userName, int id,String type) {
        
        this.userName = userName;
        this.id = id;
        this.type = type;
        
    }

    public static UserSession getInstace(String userName, int id , String type) {
        if(instance == null) {
            instance = new UserSession(userName, id,type);
        }
        return instance;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getUserName() {
        return userName;
    }

    public static void cleanUserSession() {
      instance = null;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "userName='" + userName + '\'' +             
                ",id=" + id +
                ",type=" + type +
                '}';
    }
}
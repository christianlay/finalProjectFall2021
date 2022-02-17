/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.sait.itsd;

/**
 *
 * @author Administrator
 */
public class User {
    String username;
    int usertype;
    int locked;

    public User(String username, int usertype, int locked) {
        this.username = username;
        this.usertype = usertype;
        this.locked = locked;
    }

    public String getUsername() {
        return username;
    }

    public int getUsertype() {
        return usertype;
    }

    public int getLocked() {
        return locked;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }
    
    
    
}

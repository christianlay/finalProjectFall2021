/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.sait.itsd;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 *
 * @author Administrator
 */
public class DBoperations {
    
    public String getUser() {
        String user="";
        
        ConnectionPool cp = ConnectionPool.getInstance();
        
        try {
            Connection conn = cp.getConnection();
            String sql = "select username, usertype, locked from users;";
            PreparedStatement st = conn.prepareStatement(sql);
            String userType;
            String locked;
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                userType = (rs.getString(2).equals("0")? "Normal":"Admin");
                locked = (rs.getString(3).equals("0")? "Unlocked":"Locked");
                user = user + rs.getString(1) + "," + rs.getString(2) + "." + rs.getString(3) + "!" + userType + "@" + locked + ";";
                
               
            }
            
            rs.close();
            st.close();
            cp.freeConnection(conn);
            
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return user;
    }
    
    //gets the user type
    public String getUserType(String username) {
        String userType="";
        
        ConnectionPool cp = ConnectionPool.getInstance();
        
        try {
            Connection conn = cp.getConnection();
            String sql = "select usertype from users where username=?;";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1,username);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                userType = userType + rs.getString(1);
            }
            
            rs.close();
            st.close();
            cp.freeConnection(conn);
            
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return userType;
    }
    
    //get user locked status
    public String getUserLockStatus(String username) {
        String userType="";
        
        ConnectionPool cp = ConnectionPool.getInstance();
        
        try {
            Connection conn = cp.getConnection();
            String sql = "select locked from users where username=?;";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1,username);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                userType = userType + rs.getString(1);
            }
            
            rs.close();
            st.close();
            cp.freeConnection(conn);
            
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return userType;
    }
    
    //get user locked status
    public int getAmountOfAdminsLocked() {
        int count=0;
        
        ConnectionPool cp = ConnectionPool.getInstance();
        
        try {
            
            Connection conn = cp.getConnection();
            String sql = "select locked from users where usertype=1;";
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                if(rs.getString(1).equals("0")){
                    count++;
                }
            }
            
            rs.close();
            st.close();
            cp.freeConnection(conn);
            
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return count;
    }
    
    //adding new username and password to database
    public boolean updateUsername(String newUsername,String oldUsername) {
        boolean result=false;
        
        String sql="update users set username=? where username=?;"; 
        ConnectionPool cp = ConnectionPool.getInstance();
        
        try {

            Connection conn = cp.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            
            st.setString(1,newUsername);
            st.setString(2, oldUsername);
      
            
            int rowsAffected = st.executeUpdate();
            
            result = (rowsAffected>0);
            
            st.close();
            conn.close(); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return result;
    }
    
    //change user type
    public boolean toggleUserType(String username,int toggleUserType) {
        boolean result=false;
        String sql;
        if(toggleUserType ==0){
            sql="update users set usertype=1 where username=?;"; 
        }
        else{
            sql="update users set usertype=0 where username=?;"; 
        }
        
        ConnectionPool cp = ConnectionPool.getInstance();
        
        try {

            Connection conn = cp.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            
            st.setString(1,username);
          
      
            
            int rowsAffected = st.executeUpdate();
            
            result = (rowsAffected>0);
            
            st.close();
            conn.close(); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return result;
    }
    
    //change locked/unlocked accounts
    public boolean toggleLocked(String username,int toggleLocked) {
        boolean result=false;
        String sql;
        if(toggleLocked ==0){
            sql="update users set locked=1 where username=?;"; 
        }
        else{
            sql="update users set locked=0 where username=?;"; 
        }
        
        ConnectionPool cp = ConnectionPool.getInstance();
        
        try {

            Connection conn = cp.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            
            st.setString(1,username);
          
      
            
            int rowsAffected = st.executeUpdate();
            
            result = (rowsAffected>0);
            
            st.close();
            conn.close(); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return result;
    }
    
    //compare username and password to the one stored in database
    public boolean compareUsernamePassword(String username, String password) {
        String sql="select username,password from users;";
        
        boolean result=false;
        ConnectionPool cp = ConnectionPool.getInstance();
        
        try {
            Connection conn = cp.getConnection();
            
            PreparedStatement st = conn.prepareStatement(sql);
            
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
                if(username.equals(rs.getString(1)) && password.equals(rs.getString(2))){
                    result = true;
                }
               
            }
            
            rs.close(); //!!!!!
            st.close();
            conn.close(); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            
            
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return result;
    }
    
    //check to see if there is more then one admin users in the system
    public boolean adminUserType() {
        String sql="select usertype from users;";
        
        boolean result=false;
        ConnectionPool cp = ConnectionPool.getInstance();
        
        try {
            Connection conn = cp.getConnection();
            int count = 0;
            PreparedStatement st = conn.prepareStatement(sql);
            
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
                if(rs.getInt(1) == 1){
                    count++;
                }
               
            }
            
            if(count>1){
                result = true;
            }
            
            rs.close(); //!!!!!
            st.close();
            conn.close(); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            
            
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return result;
    }
    
    //adding new username and password to database
    public boolean addUsernamePassword(String username, String password) {
        boolean result=false;
        
        String sql="insert into users set username=?, password=?;"; 
        ConnectionPool cp = ConnectionPool.getInstance();
        
        try {

            Connection conn = cp.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            
            st.setString(1,username);
            st.setString(2,password);
            
            int rowsAffected = st.executeUpdate();
            
            result = (rowsAffected>0);
            
            st.close();
            conn.close(); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return result;
    }
    
    //delete user
    public boolean deleteUser(String username) {
        boolean result=false;
        
        String sql="delete from users where username=?;"; 
        ConnectionPool cp = ConnectionPool.getInstance();
        
        try {

            Connection conn = cp.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            
            st.setString(1,username);
  
            
            int rowsAffected = st.executeUpdate();
            
            result = (rowsAffected>0);
            
            st.close();
            conn.close(); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return result;
    }
}

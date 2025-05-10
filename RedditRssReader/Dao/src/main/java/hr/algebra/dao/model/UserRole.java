/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dao.model;

/**
 *
 * @author miki
 */
public enum UserRole {
    
    ADMIN(100),
    USER(10);
    
    private final int userRole;
    
    private UserRole(int userRole){
        this.userRole = userRole;
    }
    
    public int getUserRole(){
        return userRole;
    }
    
    public static UserRole from(int userRole){
        for (UserRole  value : values()) {
            if(value.userRole == userRole){
                return value;
            }
        }
        throw new RuntimeException("No such user role");
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package librarymanagmentsystem;

/**
 *
 * @author colmj
 */
/** Admin class extends user*/
public class AdminUser implements User {
    public void displayMenu() {
        System.out.println("Admin Panel: 1. Manage Books, 2. Manage Users");
    }
}

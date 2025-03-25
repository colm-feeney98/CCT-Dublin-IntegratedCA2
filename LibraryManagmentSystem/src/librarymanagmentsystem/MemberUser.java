/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package librarymanagmentsystem;

/**
 *
 * @author colmj
 */
/**
 * The MemberUser class implements the User interface.
 *  represents a user with member privileges, such as borrowing and returning books.
 */
public class MemberUser implements User { 

    /**
     * Displays the menu options available to a member user.
     */
    public void displayMenu() {
        System.out.println("Member Panel: 1. Borrow/Return Books, 2. View Issued Books");
    }
}


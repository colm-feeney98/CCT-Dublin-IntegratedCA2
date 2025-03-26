/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package librarymanagmentsystem;

/**
 *
 * @author colmj
 */
public class UserFactory {
    public static User getUser(String role) {
        switch (role.toLowerCase()) {
            case "admin": return new AdminUser();
            case "member": return new MemberUser();
            default: throw new IllegalArgumentException("Invalid role");
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package librarymanagmentsystem;

/**
 *
 * @author colmj
 */
import java.sql.*; // Importing SQL package for database connectivity
import java.time.LocalDate; // Importing LocalDate for handling date-related operations

public class Book {
    // Attributes of the book
    private int id;            // Unique identifier for the book
    private String title;      // Title of the book
    private String author;     // Author of the book
    private String genre;      // Genre of the book (e.g., Fiction, Non-Fiction)
    private boolean available; // Availability status (true = available, false = borrowed)

   
    public Book(int id, String title, String author, String genre, boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.available = available;
    }

    // Getter methods to retrieve book information
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public boolean isAvailable() { return available; }

    // Setter method to update availability status
    public void setAvailable(boolean available) { this.available = available; }

  
    public static void borrowBook(int bookId, int userId) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            conn.setAutoCommit(false); // Disable auto-commit to allow transaction management

            // Query to check if the book is available
            PreparedStatement checkStmt = conn.prepareStatement("SELECT availability FROM books WHERE id = ?");
            checkStmt.setInt(1, bookId);
            ResultSet rs = checkStmt.executeQuery(); // Execute query

            if (rs.next() && rs.getBoolean("availability")) { // If book exists and is available
                // Insert a transaction record for borrowing
                PreparedStatement borrowStmt = conn.prepareStatement(
                    "INSERT INTO transactions (bookID, userID, issueDate, status) VALUES (?, ?, ?, 'borrowed')"
                );
                borrowStmt.setInt(1, bookId);
                borrowStmt.setInt(2, userId);
                borrowStmt.setDate(3, Date.valueOf(LocalDate.now())); // Store current date as issue date
                borrowStmt.executeUpdate(); // Execute insertion query

                // Update book availability status to 'false' (borrowed)
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE books SET availability = false WHERE id = ?");
                updateStmt.setInt(1, bookId);
                updateStmt.executeUpdate(); // Execute update query

                conn.commit(); // Commit transaction
                System.out.println("Book borrowed successfully."); // Success message
            } else {
                System.out.println("Book is not available."); // Error message if book is already borrowed
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print SQL exception details if an error occurs
        }
    }

    public static void returnBook(int bookId, int userId) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            conn.setAutoCommit(false); // Disable auto-commit to handle transactions

            // Query to check if there is an active borrow record for this book by the user
            PreparedStatement checkStmt = conn.prepareStatement(
                "SELECT status FROM transactions WHERE bookID = ? AND userID = ? AND status = 'borrowed'"
            );
            checkStmt.setInt(1, bookId);
            checkStmt.setInt(2, userId);
            ResultSet rs = checkStmt.executeQuery(); // Execute query

            if (rs.next()) { // If an active borrow record exists
                // Update the transactions table to mark the book as returned
                PreparedStatement returnStmt = conn.prepareStatement(
                    "UPDATE transactions SET returnDate = ?, status = 'returned' WHERE bookID = ? AND userID = ?"
                );
                returnStmt.setDate(1, Date.valueOf(LocalDate.now())); // Store current date as return date
                returnStmt.setInt(2, bookId);
                returnStmt.setInt(3, userId);
                returnStmt.executeUpdate(); // Execute update query

                // Update the book's availability status to 'true' (available)
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE books SET availability = true WHERE id = ?");
                updateStmt.setInt(1, bookId);
                updateStmt.executeUpdate(); // Execute update query

                conn.commit(); // Commit transaction
                System.out.println("Book returned successfully."); // Success message
            } else {
                System.out.println("No active borrow record found."); // Error message if no matching borrow record is found
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print SQL exception details if an error occurs
        }
    }
}


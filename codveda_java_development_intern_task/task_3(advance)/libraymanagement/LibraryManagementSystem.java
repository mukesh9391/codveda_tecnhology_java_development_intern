import java.sql.*;
import java.util.Scanner;

public class LibraryManagementSystem {
    private static final String URL = "jdbc:mysql://localhost:3306/librarydb";
    private static final String USER = "root";     
    private static final String PASSWORD = "password";  
    private Connection conn;

    public LibraryManagementSystem() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("? Connected to Database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   
    public void addUser(String name) {
        String sql = "INSERT INTO Users (name) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
            System.out.println("?? User added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(int userId, String newName) {
        String sql = "UPDATE Users SET name=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newName);
            stmt.setInt(2, userId);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("?? User updated successfully.");
            } else {
                System.out.println("? User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int userId) {
        String sql = "DELETE FROM Users WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("??? User deleted successfully.");
            } else {
                System.out.println("? User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewUsers() {
        String sql = "SELECT * FROM Users";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n=== User List ===");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void addBook(String title, String author) {
        String sql = "INSERT INTO Books (title, author, available) VALUES (?, ?, TRUE)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.executeUpdate();
            System.out.println("?? Book added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewBooks() {
        String sql = "SELECT * FROM Books";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n=== Book List ===");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Title: " + rs.getString("title")
                        + ", Author: " + rs.getString("author") + ", Available: " + rs.getBoolean("available"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ----------------- TRANSACTIONS -----------------

    public void borrowBook(int userId, int bookId) {
        try {
            conn.setAutoCommit(false);

            String check = "SELECT available FROM Books WHERE id=?";
            PreparedStatement stmt1 = conn.prepareStatement(check);
            stmt1.setInt(1, bookId);
            ResultSet rs = stmt1.executeQuery();

            if (rs.next() && rs.getBoolean("available")) {
                String updateBook = "UPDATE Books SET available=FALSE WHERE id=?";
                PreparedStatement stmt2 = conn.prepareStatement(updateBook);
                stmt2.setInt(1, bookId);
                stmt2.executeUpdate();

                String insertTrans = "INSERT INTO Transactions (user_id, book_id, action) VALUES (?, ?, 'BORROW')";
                PreparedStatement stmt3 = conn.prepareStatement(insertTrans);
                stmt3.setInt(1, userId);
                stmt3.setInt(2, bookId);
                stmt3.executeUpdate();

                conn.commit();
                System.out.println("? Book borrowed successfully.");
            } else {
                System.out.println("? Book is not available.");
            }
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    public void returnBook(int userId, int bookId) {
        try {
            conn.setAutoCommit(false);

            String updateBook = "UPDATE Books SET available=TRUE WHERE id=?";
            PreparedStatement stmt1 = conn.prepareStatement(updateBook);
            stmt1.setInt(1, bookId);
            stmt1.executeUpdate();

            String insertTrans = "INSERT INTO Transactions (user_id, book_id, action) VALUES (?, ?, 'RETURN')";
            PreparedStatement stmt2 = conn.prepareStatement(insertTrans);
            stmt2.setInt(1, userId);
            stmt2.setInt(2, bookId);
            stmt2.executeUpdate();

            conn.commit();
            System.out.println("? Book returned successfully.");
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

   
    public void viewTransactions() {
        String sql = "SELECT t.id, u.name, b.title, t.action, t.timestamp " +
                     "FROM Transactions t " +
                     "JOIN Users u ON t.user_id = u.id " +
                     "JOIN Books b ON t.book_id = b.id " +
                     "ORDER BY t.timestamp DESC";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n=== Transaction History ===");
            while (rs.next()) {
                System.out.println("TxnID: " + rs.getInt("id") +
                        ", User: " + rs.getString("name") +
                        ", Book: " + rs.getString("title") +
                        ", Action: " + rs.getString("action") +
                        ", Time: " + rs.getTimestamp("timestamp"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ----------------- MAIN MENU -----------------

    public static void main(String[] args) {
        LibraryManagementSystem lms = new LibraryManagementSystem();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Library Menu ===");
            System.out.println("1. Add User");
            System.out.println("2. Update User");
            System.out.println("3. Delete User");
            System.out.println("4. View Users");
            System.out.println("5. Add Book");
            System.out.println("6. View Books");
            System.out.println("7. Borrow Book");
            System.out.println("8. Return Book");
            System.out.println("9. View Transactions");
            System.out.println("10. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    sc.nextLine();
                    String name = sc.nextLine();
                    lms.addUser(name);
                    break;

                case 2:
                    System.out.print("Enter user ID: ");
                    int uid = sc.nextInt();
                    System.out.print("Enter new name: ");
                    sc.nextLine();
                    String newName = sc.nextLine();
                    lms.updateUser(uid, newName);
                    break;

                case 3:
                    System.out.print("Enter user ID: ");
                    int delId = sc.nextInt();
                    lms.deleteUser(delId);
                    break;

                case 4:
                    lms.viewUsers();
                    break;

                case 5:
                    System.out.print("Enter title: ");
                    sc.nextLine();
                    String title = sc.nextLine();
                    System.out.print("Enter author: ");
                    String author = sc.nextLine();
                    lms.addBook(title, author);
                    break;

                case 6:
                    lms.viewBooks();
                    break;

                case 7:
                    System.out.print("Enter user ID: ");
                    int userId = sc.nextInt();
                    System.out.print("Enter book ID: ");
                    int bookId = sc.nextInt();
                    lms.borrowBook(userId, bookId);
                    break;

                case 8:
                    System.out.print("Enter user ID: ");
                    int uId = sc.nextInt();
                    System.out.print("Enter book ID: ");
                    int bId = sc.nextInt();
                    lms.returnBook(uId, bId);
                    break;

                case 9:
                    lms.viewTransactions();
                    break;

                case 10:
                    System.out.println("?? Goodbye!");
                    sc.close();
                    System.exit(0);
            }
        }
    }
}

package com.bookmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private Connection conn;

    public BookDAO(Connection conn){
        this.conn = conn;
    }

    public void setupTable() throws SQLException {
        try(Statement stmt = conn.createStatement()){
            stmt.execute("CREATE TABLE IF NOT EXISTS books (id INT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255) NOT NULL, author VARCHAR(255))");
        }
    }

    public boolean insert(Book book) throws SQLException {
        try(PreparedStatement pstmt = conn.prepareStatement("INSERT INTO books (title, author) VALUES (?, ?)")){
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        }
    }

    public List<Book> findAll() throws SQLException {
        String selectSQL = "SELECT id, title, author FROM books";        
        List<Book> books = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(selectSQL);
                ResultSet rs = pstmt.executeQuery();) {

                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String title = rs.getString("title");
                        String author = rs.getString("author");
                        Book book = new Book(id, title, author);
                        
                        //Listに追加
                        books.add(book);
                    }
                    
                    return books;
        } 
    }

    public boolean deleteById(int id) throws SQLException{
        String deleteSQL = "DELETE FROM books where id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                return true;
            } else{
                return false;
            }
        } 
    }
}

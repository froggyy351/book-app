package com.bookmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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
}

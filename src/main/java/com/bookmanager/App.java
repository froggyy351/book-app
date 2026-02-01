package com.bookmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
/**
 * Hello world!
 *
 */
public class App 
{
    private static final String URL = "jdbc:h2:./.bookdb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    public static void main( String[] args ){
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = conn.createStatement()){
                    String sql = "CREATE TABLE IF NOT EXISTS books ("
                                + "id INT AUTO_INCREMENT PRIMARY KEY"
                                + "title VARCHAR(255) NOT NULL"
                                + "author VARCHAR(255))";

                    stmt.execute(sql);
                    System.out.println("データベース接続に成功しました。");
            } catch (SQLException e) {
                System.err.println("データベース関連例外が発生しました。");
                e.getStackTrace();
            }
    }
}

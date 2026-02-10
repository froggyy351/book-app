package com.bookmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.SQLException;
/**
 *書籍管理システム
 */
public class App 
{
    private static final String URL = "jdbc:h2:./.bookdb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static void main( String[] args ){
        //SJISとしてユーザからの入力を受け取る
        //はじめ何も指定しなかったため、UTF-8として読んで文字化けしてDB登録されてしまった
        Scanner scanner = new Scanner(System.in, "MS932");

        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);){
            //テーブル作成    
            setupTable(conn);

            //ユーザから入力を受け取る
            System.out.println("====本の登録====");
            System.out.println("タイトル：");
            String title = scanner.nextLine();
            System.out.println("著者　　：");
            String author = scanner.nextLine();

            //Insert文
            String insertSQL = "INSERT INTO books (title, author) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setString(1, title);
                pstmt.setString(2, author);

                int rowsInserted = pstmt.executeUpdate();
                if(rowsInserted > 0){
                    System.out.println("データベース保存しました！");
                }
            }

            //select文
            System.out.println("====蔵書一覧====");

            String selectSQL = "SELECT id, title, author FROM books";
            try (PreparedStatement pstmt = conn.prepareStatement(selectSQL);
                ResultSet rs = pstmt.executeQuery()) {
                    
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String bTitle = rs.getString("title");
                        String bAuthor = rs.getString("author");
                        System.out.println(id + ": " + bTitle + "（" + bAuthor + "）");
                    }
                
            }

            //delete文
            System.out.println("====蔵書削除====");

            //ユーザーから入力を受け取る
            System.out.println("削除したい蔵書のIDを入力してね");
            int deleteId = scanner.nextInt();

            String deleteSQL = "DELETE FROM books where id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
                pstmt.setInt(1, deleteId );
                int rowsDeleted = pstmt.executeUpdate();
                if(rowsDeleted > 0){
                    System.out.println("ID: " + deleteId + "の書籍をDBから削除しました。");
                } else {
                    System.out.println("該当するIDが見つかりませんでした。");
                }
            }

            System.out.println("=============");

        } catch (SQLException e) {
            System.err.println("データベース関連例外が発生しました");
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    public static void setupTable(Connection conn) throws SQLException {
        try(Statement stmt = conn.createStatement()){
            stmt.execute("CREATE TABLE IF NOT EXISTS books (id INT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255) NOT NULL, author VARCHAR(255))");
        }
    }
}

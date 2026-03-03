package com.bookmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
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

            BookDAO dao = new BookDAO(conn);
            //テーブル作成    
            dao.setupTable();

            //ユーザから入力を受け取る
            System.out.println("====本の登録====");
            System.out.println("タイトル：");
            String title = scanner.nextLine();
            System.out.println("著者　　：");
            String author = scanner.nextLine();

            //Insert文
            Book insertBook = new Book(title, author);
            if(dao.insert(insertBook)){
                System.out.println("保存しました。");
            }


            //select文
            System.out.println("====蔵書一覧====");

            List<Book> books = dao.findAll();
            for(Book book : books){
                System.out.println(book.getId() + " : " + book.getTitle() + "（" + book.getAuthor() + "）");
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

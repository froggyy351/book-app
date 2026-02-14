package com.bookmanager;

/**
 * 「本」を表すEntityクラス
 *
 * DBの booksテーブルの1行 = このクラスの1インスタンス
 * データを「入れ物」としてまとめる役割だけを持つ。
 * DB操作やUI表示のロジックはここに書かない。
 */
public class Book {
    private int id;
    private String title;
    private String author;

    // INSERT用（idはDBが自動採番するので不要）
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    // SELECT用（DBから取得した既存データにはidがある）
    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    // --- Getter / Setter ---
    // 外部からフィールドにアクセスするための窓口
    // フィールドをprivateにして、getter/setterを通すのが「カプセル化」

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

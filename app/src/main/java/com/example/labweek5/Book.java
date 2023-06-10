package com.example.labweek5;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "books")
public class Book {
    public static String TABLE_NAME = "books";
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "Row")
    private int row;
    @ColumnInfo(name = "ID")
    private String id;
    @ColumnInfo(name = "Title")
    private String Title;
    @ColumnInfo(name = "Author")
    private String Author;
    @ColumnInfo(name = "Price")
    private String price;
    @ColumnInfo(name = "ISBN")
    private String isbn;
    @ColumnInfo(name = "Description")
    private String description;
    public Book(String Title, String Author, String price, String id, String isbn, String description) {
        this.Title = Title;
        this.Author = Author;
        this.price = price;
        this.id = id;
        this.isbn = isbn;
        this.description = description;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }


    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

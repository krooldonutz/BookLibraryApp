package com.example.labweek5.provider;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.labweek5.Book;

import java.util.ArrayList;
import java.util.List;
@Dao
public interface BookDAO {
    @Query("select * from books")
    LiveData<List<Book>> getAllBooks();

    @Query("select * from books where title=:name")
    List<Book> getBook(String name);

    @Insert
    void addBook(Book book);

    @Query("delete from books where title= :name")
    void deleteBook(String name);

    @Query("delete FROM books")
    void deleteAllBooks();

    @Query("delete from books where row= :rowNum")
    void removeRow(int rowNum);

    @Query("delete from books where author = 'unknown'")
    void deleteUnknown();
}


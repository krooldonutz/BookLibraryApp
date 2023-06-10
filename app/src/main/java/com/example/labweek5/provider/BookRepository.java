package com.example.labweek5.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.labweek5.Book;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    private BookDAO mBookDao;
    private LiveData<List<Book>> mAllBooks;

    BookRepository(Application application) {
        BookDatabase db = BookDatabase.getDatabase(application);
        mBookDao = db.bookDao();
        mAllBooks = mBookDao.getAllBooks();
    }
    LiveData<List<Book>> getAllBooks() {
        return mAllBooks;
    }
    void insert(Book book) {
        BookDatabase.databaseWriteExecutor.execute(() -> mBookDao.addBook(book));
    }
    void removeRow(int row){
        BookDatabase.databaseWriteExecutor.execute(() -> mBookDao.removeRow(row));
    }

    void deleteAll(){
        BookDatabase.databaseWriteExecutor.execute(()->{
            mBookDao.deleteAllBooks();
        });
    }

    void deleteUnknown(){
        BookDatabase.databaseWriteExecutor.execute(() -> mBookDao.deleteUnknown());
    }
}

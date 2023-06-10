package com.example.labweek5.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.labweek5.Book;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class BookViewModel extends AndroidViewModel {
    private BookRepository mRepository;
    private LiveData<List<Book>> mAllBooks;

    public BookViewModel(@NonNull Application application) {
        super(application);
        mRepository = new BookRepository(application);
        mAllBooks = mRepository.getAllBooks();
    }

    public LiveData<List<Book>> getAllBooks() {
        return mAllBooks;
    }

    public void insert(Book book) {
        mRepository.insert(book);
    }
    public void removeRow(int row){
        mRepository.removeRow(row);
    }
    public void deleteAll(){
        mRepository.deleteAll();
    }
    public void deleteUnknown(){
        mRepository.deleteUnknown();
    }
}

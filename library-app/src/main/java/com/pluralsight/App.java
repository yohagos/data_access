package com.pluralsight;

import com.pluralsight.model.Book;
import com.pluralsight.repository.BookDataAccessObject;
import com.pluralsight.repository.DataAccessObject;

import java.util.List;
import java.util.Optional;

public class App {
    public static void main( String[] args ) {
        DataAccessObject<Book> bookDao = new BookDataAccessObject();
        List<Book> books = bookDao.findAll();

        for (Book book: books)
            System.out.println(book.toString());

        Optional<Book> optBook = bookDao.findByID(2);
        if (optBook.isPresent()) {
            Book book = optBook.get();
            System.out.println(book);
        }
    }
}

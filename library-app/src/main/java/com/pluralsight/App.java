package com.pluralsight;

import com.pluralsight.model.Book;
import com.pluralsight.repository.BookDataAccessObject;
import com.pluralsight.repository.DataAccessObject;

import java.util.List;
import java.util.Optional;

public class App {
    public static void main( String[] args ) {
        DataAccessObject<Book> bookDao = new BookDataAccessObject();

        Optional<Book> optBook = bookDao.findByID(1);
        if (optBook.isPresent()) {
            Book book = optBook.get();
            System.out.println(book);

            book.setTitle("Effective Java: Second Edition");
            bookDao.update(book);
        }

        /*Book newBook = new Book();
        newBook.setTitle("The Alchemist");
        newBook = bookDao.create(newBook);
        System.out.println(newBook.toString());*/

        List<Book> books = bookDao.findAll();

        for (Book book: books)
            System.out.println(book.toString());
    }
}

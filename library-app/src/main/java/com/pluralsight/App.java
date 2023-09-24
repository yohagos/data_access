package com.pluralsight;

import com.pluralsight.model.Book;
import com.pluralsight.repository.BookDataAccessObject;
import com.pluralsight.repository.DataAccessObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class App {
    public static void main( String[] args ) {
        DataAccessObject<Book> bookDao = new BookDataAccessObject();
/*
        Optional<Book> optBook = bookDao.findByID(1);
        if (optBook.isPresent()) {
            Book book = optBook.get();
            System.out.println(book);

            book.setTitle("Effective Java: Second Edition");
            bookDao.update(book);
        }*/

        /*Book newBook = new Book();
        newBook.setTitle("The Alchemist");
        newBook = bookDao.create(newBook);
        System.out.println(newBook.toString());*/

        //System.out.println(bookDao.delete(newBook));

        List<Book> books = bookDao.findAll();
        for (Book book: books)
            System.out.println(book.toString());

        System.out.println("#####################################");
        List<Book> rows = new ArrayList<>();
        for (Book book: books) {
            if (book.getId() > 5)
                rows.add(book);
        }
        System.out.println(bookDao.deleteBulk(rows));
        System.out.println("#####################################");
        books = bookDao.findAll();
        for (Book book: books)
            System.out.println(book.toString());
        /*List<Book> updateEntries =
                books.stream()
                    .peek(book -> book.setRating(5))
                    .collect(Collectors.toList());
        bookDao.update(updateEntries);
        System.out.println(updateEntries);*/
    }
}

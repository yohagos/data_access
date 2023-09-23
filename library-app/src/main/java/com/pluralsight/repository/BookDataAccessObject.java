package com.pluralsight.repository;

import com.pluralsight.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BookDataAccessObject
        extends AbstractDataAccessObject
        implements DataAccessObject<Book>{

    @Override
    public List<Book> findAll() {
        List<Book> books = Collections.emptyList();
        String sql = "Select * from BOOK";

        try (
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery(sql);
            ) {
            books = new ArrayList<>();
            while(rset.next()) {
                Book book = new Book();
                book.setId(rset.getLong("id"));
                book.setTitle(rset.getString("title"));
                books.add(book);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return books;
    }

    @Override
    public Optional<Book> findByID(long id) {
        Optional<Book> optBook  = Optional.empty();
        String sql = "Select ID, TITLE from BOOK where ID = ?";

        try (
                Connection con = getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                ) {
            pstmt.setLong(1, id);
            try (ResultSet rset = pstmt.executeQuery();) {
                Book resBook = new Book();
                if (rset.next()) {
                    resBook.setId(rset.getLong("id"));
                    resBook.setTitle(rset.getString("title"));
                }
                optBook = Optional.of(resBook);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return optBook;
    }
}

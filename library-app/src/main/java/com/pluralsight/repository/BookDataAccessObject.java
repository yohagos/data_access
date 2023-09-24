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
            ResultSet rset = stmt.executeQuery(sql)
            ) {
            books = new ArrayList<>();
            while(rset.next()) {
                Book book = new Book();
                book.setId(rset.getLong("id"));
                book.setTitle(rset.getString("title"));
                book.setRating(rset.getInt("rating"));
                books.add(book);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return books;
    }

    @Override
    public Optional<Book> findByID(long id) {
        Optional<Book> optBook;
        String sql = "Select ID, TITLE from BOOK where ID = ?";

        try (
                Connection con = getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)
                ) {
            pstmt.setLong(1, id);
            try (ResultSet rset = pstmt.executeQuery()) {
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

    @Override
    public Book create(Book book) {
        String sql = "insert into BOOK (TITLE) values (?)";

        try (
                Connection con = getConnection();
                PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
                ) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.executeUpdate();

            try (ResultSet genKeys = preparedStatement.getGeneratedKeys()) {
                if (genKeys.next()) {
                    book.setId(genKeys.getLong(1));
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return book;
    }

    @Override
    public Book update(Book book) {
        String sql = "Update BOOK set TITLE = ? WHERE ID = ?";

        try (
                Connection con = getConnection();
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                ) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setLong(2, book.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }

    @Override
    public int[] update(List<Book> books) {
        int[] records = {};
        String sql = "Update BOOK SET TITLE = ?, RATING = ? WHERE ID = ?";

        try (
                Connection con = getConnection();
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                ) {
            for (Book book: books) {
                preparedStatement.setString(1, book.getTitle());
                preparedStatement.setInt(2, book.getRating());
                preparedStatement.setLong(3, book.getId());

                preparedStatement.addBatch();
            }
            records = preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return records;
    }

    @Override
    public int delete(Book book) {
        int rowsAffected = 0;
        String sql = "DELETE from BOOK Where ID = ?";

        try (
                Connection con = getConnection();
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                ) {
            preparedStatement.setLong(1, book.getId());
            rowsAffected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowsAffected;
    }

    @Override
    public int[] deleteBulk(List<Book> books) {
        int[] rowsAffected = {};
        String sql = "Delete from BOOK where ID = ?";

        try (
                Connection con = getConnection();
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                ) {
            for (Book book: books) {
                preparedStatement.setLong(1, book.getId());
                preparedStatement.addBatch();
            }
            rowsAffected = preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowsAffected;
    }
}

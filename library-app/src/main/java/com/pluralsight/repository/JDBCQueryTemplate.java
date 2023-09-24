package com.pluralsight.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class JDBCQueryTemplate<T> extends AbstractDataAccessObject {

    public JDBCQueryTemplate() {}

    public List<T> queryForList(String sql)  {
        List<T> items = new ArrayList<>();

        try (
                Connection con = getConnection();
                Statement statement = con.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
                ) {
            while (resultSet.next()) {
                items.add(mapItem(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    public T queryFindByID(long id, String sql) {
        T item = null;

        try (
                Connection con = getConnection();
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                ) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                item = mapItem(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }

    public long queryCreateItem(String title, int rating, String sql) {
        long item = 0;

        try (
            Connection con = getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, title);
            preparedStatement.setInt(2, rating);
            preparedStatement.executeUpdate();

            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                if (keys.next())
                    item = keys.getLong(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }

    public abstract T mapItem(ResultSet resultSet) throws SQLException;

}

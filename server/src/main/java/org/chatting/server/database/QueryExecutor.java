package org.chatting.server.database;

import org.chatting.server.database.mapper.EntityMapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class QueryExecutor {

    public <T> T getResultSingle(String query, EntityMapper<T> entityMapper) {
        final Connection connection = DatabaseSource.createConnection();
        try {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery(query);
            final T result = entityMapper.extractSingle(resultSet);

            resultSet.close();
            statement.close();
            connection.close();

            return result;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    public <T> List<T> getResultList(String query, EntityMapper<T> entityMapper) {
        final Connection connection = DatabaseSource.createConnection();
        try {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery(query);
            final List<T> result = entityMapper.extractList(resultSet);

            resultSet.close();
            statement.close();
            connection.close();

            return result;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }
}

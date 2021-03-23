package org.chatting.server.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}

package org.chatting.server.database.mapper;

import org.chatting.server.User;
import org.chatting.server.database.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapper implements EntityMapper<User> {

    @Override
    public User extractSingle(ResultSet resultSet) {
        try {
            return extractUser(resultSet);
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public List<User> extractList(ResultSet resultSet) {
        try {
            final List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                final User user = extractUser(resultSet);
                users.add(user);
            }

            return users;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    private User extractUser(ResultSet resultSet) throws SQLException {
        final int id = resultSet.getInt("id");
        final String username = resultSet.getString("username");
        final String password = resultSet.getString("password");
        return new User(id, username, password);
    }
}

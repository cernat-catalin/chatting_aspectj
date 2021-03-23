package org.chatting.server.database;

import org.chatting.server.User;
import org.chatting.server.database.mapper.UserMapper;

import java.text.MessageFormat;
import java.util.List;

public class DatabaseService {
    private final QueryExecutor queryExecutor = new QueryExecutor();

    public User getUserByUsername(String username) {
        final String query = MessageFormat.format("SELECT id, username, password FROM user WHERE username = ''{0}''",
                username);
        final UserMapper userMapper = new UserMapper();
        return queryExecutor.getResultSingle(query, userMapper);
    }

    public List<User> getAllUsers() {
        final String query = "SELECT id, username, password FROM user";
        final UserMapper userMapper = new UserMapper();
        return queryExecutor.getResultList(query, userMapper);
    }
}

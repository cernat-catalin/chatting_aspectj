package org.chatting.server.database;

import org.chatting.server.database.mapper.UserMapper;
import org.chatting.server.entity.UserEntity;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

public class DatabaseService {
    private final QueryExecutor queryExecutor = new QueryExecutor();

    public Optional<UserEntity> getUserByUsername(String username) {
        final String query = MessageFormat.format("SELECT id, username, password FROM user WHERE username = ''{0}''",
                username);
        final UserMapper userMapper = new UserMapper();
        return queryExecutor.getResultSingle(query, userMapper);
    }

    public List<UserEntity> getAllUsers() {
        final String query = "SELECT id, username, password FROM user";
        final UserMapper userMapper = new UserMapper();
        return queryExecutor.getResultList(query, userMapper);
    }
}

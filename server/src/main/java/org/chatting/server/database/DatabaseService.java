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

    public void addUser(String username, String password) {
        final String query = MessageFormat.format("INSERT INTO user (username, password) VALUES (''{0}'', ''{1}'')",
                username, password);

        queryExecutor.executeQuery(query);
        final Optional<UserEntity> userOpt = getUserByUsername(username);
        userOpt.ifPresent(user -> addUserStatistics(user.getId()));
    }

    private void addUserStatistics(int userId) {
        final String query = MessageFormat.format("INSERT INTO user_statistics (user_id) VALUES ({0});", userId);
        queryExecutor.executeQuery(query);
    }

    public void incrementUserLogins(int userId) {
        final String query = MessageFormat.format("UPDATE user_statistics SET n_logins = n_logins + 1 WHERE user_id = {0}",
                userId);
        queryExecutor.executeQuery(query);
    }

    public void incrementUserMessages(int userId) {
        final String query = MessageFormat.format("UPDATE user_statistics SET n_messages = n_messages + 1 WHERE user_id = {0}",
                userId);
        queryExecutor.executeQuery(query);
    }
}

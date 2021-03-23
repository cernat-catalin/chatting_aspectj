package org.chatting.server.database;

import org.chatting.server.User;

public class DatabaseService {
    private final QueryExecutor queryExecutor = new QueryExecutor();

    public User getUser() {
        final String query = "SELECT id, username, password FROM user";
        final UserMapper userMapper = new UserMapper();
        return queryExecutor.getResultSingle(query, userMapper);
    }
}

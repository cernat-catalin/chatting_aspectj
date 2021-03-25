package org.chatting.server.aspect;

import org.chatting.common.message.LoginMessage;
import org.chatting.server.database.DatabaseService;
import org.chatting.server.entity.UserEntity;
import org.chatting.server.network.UserThread;

import java.io.IOException;
import java.util.Optional;

public aspect SecurityAspect {

    private final DatabaseService databaseService = new DatabaseService();

    pointcut userLogin():
            call(void org.chatting.server.network.UserThread.handleUserLogin(LoginMessage));

    void around(UserThread userThread, LoginMessage loginMessage): userLogin()
            && args(loginMessage)
            && target(userThread) {
        final String username = loginMessage.getUsername();
        final String password = loginMessage.getPassword();

        final Optional<UserEntity> userEntity = databaseService.getUserByUsername(username);
        if (userEntity.isPresent() && userEntity.get().getPassword().equals(password)) {
            proceed(userThread, loginMessage);
        } else {
            try {
                userThread.sendLoginResult(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

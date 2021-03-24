package org.chatting.server;

import org.chatting.server.database.DatabaseService;
import org.chatting.server.entity.UserEntity;
import org.chatting.server.network.ChatServer;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        final DatabaseService databaseService = new DatabaseService();

//        final Optional<UserEntity> userEntityOpt = databaseService.getUserByUsername("catalin");
//        final Optional<UserEntity> userEntityOpt = databaseService.getUserByUsername("catalin2");

//        userEntityOpt.ifPresent(userEntity -> {
//            System.out.printf("User: %d %s %s\n", userEntity.getId(), userEntity.getUsername(), userEntity.getPassword());
//        });

        final int port = 8000;
        final ChatServer chatServer = new ChatServer(port);
        chatServer.execute();
    }
}

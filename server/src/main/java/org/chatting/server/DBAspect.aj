package org.chatting.server;

import java.sql.Connection;

public aspect DBAspect percflow(topLevelTransaction()) {

    private Connection connection;

    pointcut createConnection():
            call(Connection org.chatting.server.database.DatabaseSource.createConnection());

    pointcut closeConnection():
            call(void Connection.close());

    pointcut transaction():
            call(* org.chatting.server.database.DatabaseService.*(..));

    pointcut topLevelTransaction():
            transaction() && !cflowbelow(transaction());


    Connection around(): createConnection() && cflow(transaction()) {
        if (connection == null) {
            System.out.println("---- ADVICE CREATE CONNECTION");
            connection = proceed();
            connection.setAutoCommit(false);
        }

        return connection;
    }

    Object around(): topLevelTransaction() {
        System.out.println("---- ADVICE TOP LEVEL");
        try {
            Object result = proceed();
            connection.commit();
            return result;
        } catch (Exception ex) {
            connection.rollback();
            throw new TransactionException(ex);
        } finally {
            System.out.println("---- ADVICE ClOSE CONNECTION");
            connection.close();
        }
    }

    void around(): closeConnection() && !within(DBAspect) {
    }

    private static aspect SoftSQLException {
        declare soft:java.sql.SQLException:
                call (void Connection.close())
                        || call (void Connection.setAutoCommit(boolean))
                        || call (void Connection.rollback())
                        && within(DBAspect);
    }

    public static class TransactionException extends RuntimeException {
        public TransactionException(Throwable cause) {
            super(cause);
        }
    }
}

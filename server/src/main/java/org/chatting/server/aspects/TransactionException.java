package org.chatting.server.aspects;

public class TransactionException extends RuntimeException {
    public TransactionException(Throwable cause) {
        super(cause);
    }
}

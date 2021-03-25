package org.chatting.client.gui.event;

public class SignupResultEvent implements Event {

    private final boolean signupAccepted;

    public SignupResultEvent(boolean signupAccepted) {
        this.signupAccepted = signupAccepted;
    }

    @Override
    public EventType getEventType() {
        return EventType.SIGN_UP_RESULT;
    }

    public boolean isSignupAccepted() {
        return signupAccepted;
    }
}

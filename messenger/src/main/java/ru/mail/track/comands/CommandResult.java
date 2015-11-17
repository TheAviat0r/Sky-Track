package ru.mail.track.comands;

/**
 *
 */
public abstract class CommandResult {
    enum Status {
        OK,
        FAILED,
        NOT_LOGGED,
        NO_CHATS,
        NOT_EXIST,
    }

    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

package ru.mail.track.commands;

/**
 *
 */
public class ServerResponse extends CommandResult {

    public ServerResponse() {
        super();
    }

    public ServerResponse(String response) {
        super();
        this.response = response;
        this.setStatus(CommandResult.Status.OK);
    }

    public ServerResponse(CommandResult.Status result, String response) {
        this(response);
        this.setStatus(result);
    }

    private String response = "";

    public String getResponse() {
        return response;
    }

    public void appendNewLine(String response) {
        this.response += response + "\n";
    }

    public void setResponse(String response) {
        this.response = response;
    }
}

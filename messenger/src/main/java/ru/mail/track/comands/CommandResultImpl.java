package ru.mail.track.comands;

/**
 * Created by aviator on 17.11.15.
 */
public class CommandResultImpl extends CommandResult {

    private String serverResponse;
    private ServerCode serverCode;
    private StringBuilder builder;

    public CommandResultImpl(CommandResult.Status type) {
        this.setStatus(type);
        serverResponse = "";
        builder = new StringBuilder();
    }

    public CommandResultImpl(CommandResult.Status type, String response) {
        this.setStatus(type);
        this.serverResponse = response;
        this.serverCode = ServerCode.OK;
    }

    public void setServerCode(ServerCode serverCode) {
        this.serverCode = serverCode;
    }

    public String getServerResponse() {
        return serverResponse;
    }

    @Deprecated
    public void setServerResponse(String serverResponse) {
        this.serverResponse = serverResponse;
    }

    public void addToServerResponse(String responsePart) {
        builder.append(responsePart);
        builder.append("\n");
    }

    public void finishServerResponse() {
        serverResponse = builder.toString();
    }

    public ServerCode getServerCode() {
        return serverCode;
    }
}

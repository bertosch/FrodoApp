package de.spdmc.frodo;

/**
 * @Author
 * Created by rob on 24.01.16.
 */
public class ChatMessage {
    public boolean left;
    public String message;
    private String dateTime;

    public ChatMessage(boolean left, String message) {
        super();
        this.left = left;
        this.message = message;
    }

    public String getDate() {
        return dateTime;
    }

    public void setDate(String dateTime) {
        this.dateTime = dateTime;
    }

}

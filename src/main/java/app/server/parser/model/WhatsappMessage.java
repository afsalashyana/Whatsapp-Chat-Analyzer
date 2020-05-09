package app.server.parser.model;

import java.time.LocalDateTime;

public class WhatsappMessage {

  private LocalDateTime dateTime;
  private String sender;
  private String message;

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public WhatsappMessage setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
    return this;
  }

  public String getSender() {
    return sender;
  }

  public WhatsappMessage setSender(String sender) {
    this.sender = sender;
    return this;
  }

  public String getMessage() {
    return message;
  }

  public WhatsappMessage setMessage(String message) {
    this.message = message;
    return this;
  }
}

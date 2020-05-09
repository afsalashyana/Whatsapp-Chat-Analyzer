package app.common;

import app.server.parser.model.WhatsappMessage;
import java.util.List;
import java.util.stream.Collectors;

public class MessageUtil {

  public static List<WhatsappMessage> getAllMessagesWithSender(List<WhatsappMessage> messages, String senderName) {
    return messages.stream()
        .filter(whatsappMessage -> whatsappMessage.getSender().equalsIgnoreCase(senderName))
        .collect(Collectors.toList());
  }
}

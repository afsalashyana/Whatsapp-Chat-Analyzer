package app.server.parser;

import app.server.parser.exception.UnsupportedExportFileException;
import app.server.parser.model.IWhatsappMessageParser;
import app.server.parser.model.WhatsappMessage;
import com.google.common.collect.Iterables;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("DuplicatedCode")
public class WhatsappDefaultMessageParser implements IWhatsappMessageParser {

  public static final String MAIN_REGEX = "^(\\d+\\/\\d+\\/\\d+),.(\\d+:\\d+.(pm|am)).-.([^:]*):(.*)";
  private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yy h:mm a", Locale.US);

  @Override
  public List<WhatsappMessage> parseFile(File file) throws IOException, UnsupportedExportFileException {
    List<WhatsappMessage> messages = new ArrayList<>();
    try (Scanner scanner = new Scanner(file)) {
      while (scanner.hasNext()) {
        String line = scanner.nextLine();
        Matcher matcher = Pattern.compile(MAIN_REGEX).matcher(line);
        List<String> parsedGroups = parseGroupsFromRegex(matcher);
        if (parsedGroups.isEmpty()) {
          addLineToPreviousMessage(messages, line);
          continue;
        }
        String date = parsedGroups.get(1);
        String time = parsedGroups.get(2);
        String sender = parsedGroups.get(4);
        String message = parsedGroups.get(5);
        LocalDateTime localDate = LocalDateTime.parse(String.format("%s %s", date, time.toUpperCase()), dateTimeFormatter);

        if (shouldFilterMessage(message)) {
          continue;
        }

        WhatsappMessage whatsappMessage = new WhatsappMessage()
            .setMessage(message)
            .setDateTime(localDate)
            .setSender(sender);
        messages.add(whatsappMessage);
      }
    }
    if (messages.isEmpty()) {
      throw new UnsupportedExportFileException("Regex mismatch");
    }
    return messages;
  }

  private boolean shouldFilterMessage(String message) {
    return message.toLowerCase().contains("<media omitted>");
  }

  private void addLineToPreviousMessage(List<WhatsappMessage> messages, String line) {
    if (messages.isEmpty()) {
      return;
    }
    WhatsappMessage lastMessage = Iterables.getLast(messages);
    lastMessage.setMessage(
        lastMessage.getMessage() + "\n" + line
    );
  }

  private List<String> parseGroupsFromRegex(Matcher matcher) {
    List<String> regexGroups = new ArrayList<>();
    while (matcher.find()) {
      for (int j = 0; j <= matcher.groupCount(); j++) {
        regexGroups.add(matcher.group(j));
      }
    }
    return regexGroups;
  }

  @Override
  public String getRegex() {
    return MAIN_REGEX;
  }
}

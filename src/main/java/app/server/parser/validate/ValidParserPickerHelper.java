package app.server.parser.validate;

import app.server.parser.Whatsapp24HrFormatMessageParser;
import app.server.parser.WhatsappDefaultMessageParser;
import app.server.parser.model.IWhatsappMessageParser;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidParserPickerHelper {

  private List<? extends IWhatsappMessageParser> parsers = Lists.newArrayList(
      new WhatsappDefaultMessageParser(),
      new Whatsapp24HrFormatMessageParser()
  );

  public IWhatsappMessageParser validateFileAgainstParsers(File file) throws Exception {
    List<String> initialLines = readFirst50Lines(file);
    for (IWhatsappMessageParser parser : parsers) {
      if (matchAgainstParser(initialLines, parser)) {
        return parser;
      }
    }
    return null;
  }

  private boolean matchAgainstParser(List<String> lines, IWhatsappMessageParser parser) {
    Pattern pattern = Pattern.compile(parser.getRegex());
    for (String line : lines) {
      Matcher matcher = pattern.matcher(line);
      if (matcher.find()) {
        return true;
      }
    }
    return false;
  }

  private List<String> readFirst50Lines(File file) throws IOException {
    List<String> buffer = new ArrayList<>();
    int counter = 0;
    try (Scanner scanner = new Scanner(file)) {
      while (scanner.hasNext()) {
        buffer.add(scanner.nextLine());
        if (counter++ > 50) {
          break;
        }
      }
    }
    return buffer;
  }
}

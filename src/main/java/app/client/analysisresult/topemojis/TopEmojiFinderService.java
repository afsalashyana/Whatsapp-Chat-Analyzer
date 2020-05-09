package app.client.analysisresult.topemojis;

import app.server.parser.model.WhatsappMessage;
import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;

public class TopEmojiFinderService {

  public XYChart.Series<String, Long> prepareChartData(List<WhatsappMessage> whatsappMessages) {
    XYChart.Series<String, Long> series = new XYChart.Series<>();
    series.setName("Emoji vs Occurrence count");

    Comparator<Entry<String, Long>> comparator = Entry.comparingByValue();
    whatsappMessages.stream()
        .flatMap(whatsappMessage -> extractEmojis(whatsappMessage.getMessage()).stream())
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
        .entrySet().stream()
        .sorted(comparator.reversed())
        .limit(10)
        .forEach(entry -> series.getData().add(new Data<>(getUpdatedEmoji(entry.getKey()), entry.getValue())));
    return series;
  }

  private List<String> extractEmojis(String sentence) {
    List<String> emojis = EmojiParser.extractEmojis(sentence);
    emojis.removeIf(s -> s.isBlank() || isInvalidEmoji(s));
    return emojis;
  }

  @SuppressWarnings("RedundantIfStatement")
  private boolean isInvalidEmoji(String emoji) {
    if (!EmojiManager.isEmoji(emoji)) {
      return true;
    }
    return false;
  }

  private String getUpdatedEmoji(String emoji) {
    if (EmojiParser.parseToAliases(emoji).equalsIgnoreCase(":rolling_on_the_floor_laughing:")) {
      return EmojiParser.parseToUnicode("\uD83D\uDE02");
    }
    return emoji;
  }

  private String convertToUnicodeString(String string) {
    StringBuilder unicodeString = new StringBuilder();
    for (char c : string.toCharArray()) {
      unicodeString.append(convertCharacterToUnicode(c).toLowerCase());
    }
    return unicodeString.toString();
  }

  private String convertCharacterToUnicode(char ch) {
    return String.format("\\\\u%04x", (int) ch);
  }
}

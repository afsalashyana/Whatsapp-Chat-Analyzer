package app.client.analysisresult.topwords;

import app.server.parser.model.WhatsappMessage;
import com.google.common.base.Strings;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;

public class TopWordFinderService {

  public XYChart.Series<String, Long> prepareChartData(List<WhatsappMessage> whatsappMessages) {
    XYChart.Series<String, Long> series = new XYChart.Series<>();
    series.setName("Words vs Occurrence count");

    Comparator<Entry<String, Long>> comparator = Entry.comparingByValue();
    whatsappMessages.stream()
        .flatMap(whatsappMessage -> splitSentenceToWords(whatsappMessage.getMessage()).stream())
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
        .entrySet().stream()
        .sorted(comparator.reversed())
        .limit(10)
        .forEach(entry -> series.getData().add(new Data<>(entry.getKey(), entry.getValue())));
    return series;
  }

  private List<String> splitSentenceToWords(String sentence) {
    String[] words = sentence.split("\\W+");
    return Arrays.stream(words)
        .filter(string -> !Strings.isNullOrEmpty(string))
        .collect(Collectors.toList());
  }
}

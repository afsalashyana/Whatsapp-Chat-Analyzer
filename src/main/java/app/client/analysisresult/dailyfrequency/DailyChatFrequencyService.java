package app.client.analysisresult.dailyfrequency;

import static java.util.stream.Collectors.groupingBy;

import app.server.parser.model.WhatsappMessage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import javafx.scene.chart.XYChart;

public class DailyChatFrequencyService {

  private final DateTimeFormatter chartDateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy EEE");

  public XYChart.Series<String, Long> prepareChartData(List<WhatsappMessage> whatsappMessages) {
    XYChart.Series<String, Long> series = new XYChart.Series<>();
    series.setName("Date vs Number of messages");

    whatsappMessages.stream()
        .collect(groupingBy(whatsappMessage -> whatsappMessage.getDateTime().toLocalDate()))
        .entrySet().stream()
        .sorted(Map.Entry.comparingByKey(LocalDate::compareTo))
        .forEach(((entry) -> {
          LocalDate date = entry.getKey();
          List<WhatsappMessage> messageList = entry.getValue();
          series.getData().add(new XYChart.Data<>(date.format(chartDateTimeFormatter), (long) messageList.size()));
        }));
    return series;
  }
}

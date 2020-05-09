package app.client.analysisresult.messagecount;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import app.server.parser.model.WhatsappMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.chart.PieChart;

public class MessageCountDistributionService {

  public List<PieChart.Data> prepareChartData(List<WhatsappMessage> whatsappMessages) {
    List<PieChart.Data> dataSeries = new ArrayList<>();

    Map<String, Long> groupedData = whatsappMessages.stream()
        .collect(groupingBy(WhatsappMessage::getSender, counting()));

    long total = groupedData.values().stream().mapToLong(value -> value).sum();

    groupedData.forEach((key, value) -> {
      float share = (float) value / total;
      PieChart.Data data = new PieChart.Data(key, value);
      data.setName(String.format("%s (%.2f%%)", key, share*100));
      dataSeries.add(data);
    });
    return dataSeries;
  }
}

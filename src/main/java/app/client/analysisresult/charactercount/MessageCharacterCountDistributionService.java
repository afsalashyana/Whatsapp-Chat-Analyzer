package app.client.analysisresult.charactercount;

import app.server.parser.model.WhatsappMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.chart.PieChart;

public class MessageCharacterCountDistributionService {

  public List<PieChart.Data> prepareChartData(List<WhatsappMessage> whatsappMessages) {
    List<PieChart.Data> dataSeries = new ArrayList<>();

    Map<String, Long> groupedData = new HashMap<>();
    for (WhatsappMessage whatsappMessage : whatsappMessages) {
      long count = groupedData.computeIfAbsent(whatsappMessage.getSender(), k -> 0L);
      int messageLength = UnicodeLengthUtil.getLength(whatsappMessage.getMessage());
      count = count + messageLength;
      groupedData.put(whatsappMessage.getSender(), count);
    }
    long total = groupedData.values().stream().mapToLong(value -> value).sum();

    groupedData.forEach((key, value) -> {
      float share = (float) value / total;
      PieChart.Data data = new PieChart.Data(key, value);
      data.setName(String.format("%s (%.2f%%) (%d chars)", key, share * 100, value));
      dataSeries.add(data);
    });
    return dataSeries;
  }
}

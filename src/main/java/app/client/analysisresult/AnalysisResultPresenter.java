package app.client.analysisresult;

import app.client.analysisresult.dailyfrequency.DailyChatFrequencyService;
import app.client.analysisresult.topwords.TopWordFinderService;
import app.server.parser.model.WhatsappMessage;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

public class AnalysisResultPresenter implements Initializable {

  private List<WhatsappMessage> whatsappMessages;

  @FXML
  public AnchorPane rootPane;
  @FXML
  public TabPane tabPane;
  @FXML
  public Tab tabChatFrequencyByDate;
  @FXML
  public LineChart<String, Long> lineChartChatFrequencyByDate;
  @FXML
  public BarChart<String, Long> barChartWordCount;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }

  public void setData(List<WhatsappMessage> whatsappMessages) {
    this.whatsappMessages = whatsappMessages;
  }

  public void refresh() {
    populateFrequencyGraph();
    populateWordCountGraph();
  }

  private void populateWordCountGraph() {
    barChartWordCount.getData().clear();
    XYChart.Series<String, Long> wordCountData = new TopWordFinderService().prepareChartData(whatsappMessages);
    barChartWordCount.getData().add(wordCountData);
  }

  private void populateFrequencyGraph() {
    lineChartChatFrequencyByDate.getData().clear();
    XYChart.Series<String, Long> chatFrequencyData = new DailyChatFrequencyService().prepareChartData(whatsappMessages);
    lineChartChatFrequencyByDate.getData().add(chatFrequencyData);
  }
}

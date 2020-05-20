package app.client.analysisresult;

import app.client.analysisresult.charactercount.MessageCharacterCountDistributionService;
import app.client.analysisresult.dailyfrequency.DailyChatFrequencyService;
import app.client.analysisresult.messagecount.MessageCountDistributionService;
import app.client.analysisresult.topemojis.TopEmojiFinderService;
import app.client.analysisresult.topwords.TopWordFinderService;
import app.server.parser.model.WhatsappMessage;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
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
  @FXML
  public PieChart pieChartSenderDistribution;
  @FXML
  public BarChart<String, Long> barChartEmojiCount;
  @FXML
  public PieChart pieChartCharacterCount;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }

  public void setData(List<WhatsappMessage> whatsappMessages) {
    this.whatsappMessages = whatsappMessages;
  }

  public void refresh() {
    populateFrequencyGraph();
    populateWordCountGraph();
    populateSenderDistributionGraph();
    populateEmojiCountGraph();
    populateCharacterDistributionGraph();
  }

  private void populateEmojiCountGraph() {
    barChartEmojiCount.getData().clear();
    XYChart.Series<String, Long> emojiCountData = new TopEmojiFinderService().prepareChartData(whatsappMessages);
    barChartEmojiCount.getData().add(emojiCountData);
  }

  private void populateSenderDistributionGraph() {
    pieChartSenderDistribution.getData().clear();
    List<PieChart.Data> dataSeries = new MessageCountDistributionService().prepareChartData(whatsappMessages);
    pieChartSenderDistribution.getData().addAll(dataSeries);
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

  private void populateCharacterDistributionGraph() {
    pieChartCharacterCount.getData().clear();
    List<PieChart.Data> dataSeries = new MessageCharacterCountDistributionService().prepareChartData(whatsappMessages);
    pieChartCharacterCount.getData().addAll(dataSeries);
  }
}

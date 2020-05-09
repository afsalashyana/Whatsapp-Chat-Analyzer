package app.client.selectfile;

import app.client.navigation.NavigationService;
import app.server.parser.WhatsappDefaultMessageParser;
import app.server.parser.model.WhatsappMessage;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class SelectFilePresenter implements Initializable {

  private static final Logger log = Logger.getLogger(SelectFilePresenter.class.getName());

  @FXML
  public AnchorPane rootPane;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }

  public void handleButtonSelectFileAction(ActionEvent actionEvent) throws Exception {
    File file = selectFile();
    if (file != null) {
      log.log(Level.INFO, "File selected: {0}", file.getAbsolutePath());
      WhatsappDefaultMessageParser parser = new WhatsappDefaultMessageParser();
      List<WhatsappMessage> messageList = parser.parseFile(file);
      NavigationService.getInstance().launchResultView(getStage(), messageList);
    }
  }

  private File selectFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    fileChooser.setTitle("Select whatsapp chat export file");
    fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
    return fileChooser.showOpenDialog(getStage());
  }

  private Stage getStage() {
    return (Stage) rootPane.getScene().getWindow();
  }
}

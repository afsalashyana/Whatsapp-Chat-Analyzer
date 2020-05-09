package app.client.navigation;

import app.client.analysisresult.AnalysisResultPresenter;
import app.server.parser.model.WhatsappMessage;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NavigationService {

  public static final NavigationService instance = new NavigationService();

  public static NavigationService getInstance() {
    return instance;
  }

  public void launchResultView(Stage stage, List<WhatsappMessage> messages) throws Exception {
    FXMLLoader loader = new FXMLLoader();
    Parent root = loader.load(getClass().getResourceAsStream("/app/client/analysisresult/analysisresult.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.sizeToScene();
    stage.setMaximized(true);

    AnalysisResultPresenter presenter = loader.getController();
    presenter.setData(messages);
    Platform.runLater(presenter::refresh);
  }
}

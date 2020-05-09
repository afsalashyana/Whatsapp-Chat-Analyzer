package app;

import app.common.constants.CommonConstants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/app/client/selectfile/selectfile.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.sizeToScene();
    stage.setTitle(CommonConstants.APPLICATION_NAME);
    stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
    stage.show();
  }
}

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage stage) throws Exception{
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/auth/login.fxml"))));
        stage.setTitle("Clothify");
        stage.show();
    }
}
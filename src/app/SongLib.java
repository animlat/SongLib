package app;

//--module-path "C:\javafx-sdk-13\lib" --add-modules javafx.controls,javafx.fxml

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.SongLibController;

public class SongLib extends Application {

	@Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/SongLib.fxml"));
        primaryStage.setTitle("Song Library");
<<<<<<< HEAD
        primaryStage.setScene(new Scene(root, 800, 700));
=======
        primaryStage.setScene(new Scene(root, 800, 600));
>>>>>>> c62e2a23264ad567d47df2e82eb8496481a84d50
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

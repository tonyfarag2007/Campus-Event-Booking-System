import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/*
 * Main Entry Point for the Campus Event Booking System.
 */
public class Main extends Application {

    @Override
    public void start(Stage main_stage) {
        try {
            // Loading the FXML hierarchy from the src directory.
            // Using getClass().getResource() ensures the file is found relative to the class path.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main_view.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            main_stage.setTitle("Campus Event Booking System");
            main_stage.setScene(scene);
            main_stage.show();

        } catch (IOException e) {
            // Catching IO issues during FXML loading to prevent silent failures at runtime.
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
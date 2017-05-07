package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// init();
		try {
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("Sample.fxml"));
			Scene scene = new Scene(root, 800, 500);
			// scene.getStylesheets().add(getClass().getResource("styles/styles.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

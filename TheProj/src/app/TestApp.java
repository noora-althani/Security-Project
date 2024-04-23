package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TestApp extends Application{
	 @Override 
	    public void start(Stage stage) throws Exception{
	        Pane root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
	        stage.setScene(new Scene(root));
	        stage.setTitle("Login Dialog");
	        stage.show();
	    }
	 
	 public static void main(String[] args) {
		launch(args);
	} 
}

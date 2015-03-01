package org.robert.taanalys.ria;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApplication extends Application {

	public static void main(String[] args) {
		Application.launch(MainApplication.class, (java.lang.String[]) null);
	}

	@Override
	public void start(Stage stage) throws Exception {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
			stage.setTitle("Teknisk analys");
			stage.setScene(new Scene(root));		
			stage.getIcons().add(new Image(this.getClass().getResourceAsStream("icon_16_16.png")));
			stage.getIcons().add(new Image(this.getClass().getResourceAsStream("icon_32_32.png")));
			stage.getIcons().add(new Image(this.getClass().getResourceAsStream("icon_48_48.png")));
			stage.getIcons().add(new Image(this.getClass().getResourceAsStream("icon_256_256.png")));
			stage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
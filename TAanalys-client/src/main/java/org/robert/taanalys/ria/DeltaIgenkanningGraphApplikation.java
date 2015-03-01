package org.robert.taanalys.ria;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hittar liknande grafer. Använder en algoritm som jämför deltavärdet mellan
 * grafer.
 * 
 * @author Robert
 * 
 */
public class DeltaIgenkanningGraphApplikation extends Application {

	public static void main(String[] args) {
		Application.launch(DeltaIgenkanningGraphApplikation.class,
				(java.lang.String[]) null);
	}

	@Override
	public void start(Stage stage) throws Exception {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(
					"DeltaIgenkanningGraph.fxml"));
			stage.setTitle("Teknisk analys");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

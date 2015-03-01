package org.robert.taanalys.ria;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Analyserar grafen som ligger till grund vid USA öppning. Avser att visa
 * endera uppgångsgrafer, nedgångsgrafer och stillastående effekten. Detta för
 * att kunna göra en bedömning om det ska bli en uppgång/nedgång/inget.
 * 
 * @author Robert
 * 
 */
public class USAOpeningEffectGraphAnalysApplikation extends Application {

	public static void main(String[] args) {
		Application.launch(USAOpeningEffectGraphAnalysApplikation.class,
				(java.lang.String[]) null);
	}

	@Override
	public void start(Stage stage) throws Exception {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(
					"USAOpeningEffectGraphAnalysChart.fxml"));
			stage.setTitle("Teknisk analys");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

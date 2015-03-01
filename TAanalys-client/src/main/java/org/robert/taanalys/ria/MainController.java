package org.robert.taanalys.ria;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Hanterar Huvudmenyn.
 */
public class MainController {

	@FXML
	private ImageView previewMenuchoice;

	@FXML
	protected void mouseOverDeltaalgoritm() {
		Image image = new Image("deltaalgoritm.png");
		previewMenuchoice.setImage(image);
	}

	@FXML
	protected void mouseExitedDeltaalgoritm() {
		previewMenuchoice.setImage(null);
	}

	@FXML
	protected void visaDeltaalgoritm() throws Exception {
		DeltaIgenkanningGraphApplikation newContent = new DeltaIgenkanningGraphApplikation();
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		newContent.start(stage);
	}

	@FXML
	protected void mouseOverUSAOppningeffect() {
		Image image = new Image("trenderUSAOppning.png");
		previewMenuchoice.setImage(image);
	}

	@FXML
	protected void mouseExitedUSAOppningeffect() {
		previewMenuchoice.setImage(null);
	}

	@FXML
	protected void visaUSAOppningeffect() throws Exception {
		USAOpeningEffectApplikation newContent = new USAOpeningEffectApplikation();
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		newContent.start(stage);
	}

	@FXML
	protected void mouseOverGrafanalys() {
		Image image = new Image("grafanalys.png");
		previewMenuchoice.setImage(image);
	}

	@FXML
	protected void mouseExitedGrafanalys() {
		previewMenuchoice.setImage(null);
	}

	@FXML
	protected void visaGraphAnalys() throws Exception {
		USAOpeningEffectGraphAnalysApplikation newContent = new USAOpeningEffectGraphAnalysApplikation();
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		newContent.start(stage);
	}
}

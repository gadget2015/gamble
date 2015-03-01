package org.robert.taanalys.ria;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;

import org.robert.taanalys.ria.businessdelegate.InstrumentServiceBD;
import org.robert.taanalys.ria.businessdelegate.InstrumentServiceImpl;
import org.robert.taanalys.ria.businessdelegate.UsaOppningseffekt;

public class USAOpeningEffectController {
	@FXML
	private BarChart<String, Integer> graph;

	@FXML
	private ComboBox<String> antalDagar;

	@SuppressWarnings("unused")
	@FXML
	private void initialize() {
		ObservableList<String> options = FXCollections.observableArrayList(
				"10", "20", "40", "60", "80", "1000");
		antalDagar.setItems(options);
	}

	@SuppressWarnings("unchecked")
	@FXML
	protected void hamta20senasteDagarnasStatistik() {
		int dagar = Integer.parseInt(antalDagar.getValue().trim());

		// Anropar affärsskiktet.
		InstrumentServiceBD bd = new InstrumentServiceImpl();
		// InstrumentServiceBD bd = new InstrumentServiceMock();
		UsaOppningseffekt statistik = bd
				.hamtaUSAOppningseffectStatForDeSenasteDagarna(dagar);

		@SuppressWarnings("rawtypes")
		XYChart.Series series1 = new XYChart.Series();
		series1.setName(antalDagar.getValue().trim());
		series1.getData().add(
				new XYChart.Data<String, Integer>("Uppgångar", statistik
						.getUppgang()));
		series1.getData().add(
				new XYChart.Data<String, Integer>("Nedgångar", statistik
						.getNedgang()));
		series1.getData().add(
				new XYChart.Data<String, Integer>("Mindre än 0.25%", statistik
						.getIngenRorlese()));
		series1.getData().add(
				new XYChart.Data<String, Integer>("Totalt", statistik
						.getAntalDagar()));

		graph.getData().add(series1);
	}
}

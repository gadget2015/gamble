package org.robert.taanalys.ria;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import org.robert.taanalys.ria.businessdelegate.InstrumentServiceBD;
import org.robert.taanalys.ria.businessdelegate.InstrumentServiceImpl;
import org.robert.taanalys.ria.businessdelegate.InstrumentServiceMock;
import org.robert.taanalys.ria.businessdelegate.PercentEvent;
import org.robert.taanalys.ria.businessdelegate.ProcentIntradag;

public class USAOpeningEffectGraphAnalysController extends LineGraphController {
	@FXML
	private LineChart<Integer, Double> graph;

	@FXML
	private TextField franDatum;

	@FXML
	private TextField tillDatum;

	@SuppressWarnings("unused")
	@FXML
	private void initialize() {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		Calendar today = Calendar.getInstance();
		tillDatum.setText(sdf.format(today.getTime()));

		today.add(Calendar.DAY_OF_MONTH, -20);
		franDatum.setText(sdf.format(today.getTime()));
	}

	@FXML
	protected void hamtaUppgangarSenaste20Dagarna() {
		// Hämta data ifrån vyn.
		Calendar franTid = konvertera(franDatum.getText(), "yyyy-MM-dd");
		franTid.set(Calendar.HOUR_OF_DAY, 0);
		Calendar tillTid = konvertera(tillDatum.getText(), "yyyy-MM-dd");
		tillTid.set(Calendar.HOUR_OF_DAY, 23);

		// Anropar affärsskiktet.
		InstrumentServiceBD bd = new InstrumentServiceImpl();		
		Collection<ProcentIntradag> days = bd.hamtaUppgangarPgaUSAOppning(
				franTid.getTime(), tillTid.getTime());

		ObservableList<XYChart.Series<Integer, Double>> lineChartData = FXCollections
				.observableArrayList();

		// Skapa grafer för alla dagar i samma diagram.
		for (ProcentIntradag day : days) {
			LineChart.Series<Integer, Double> serie = new LineChart.Series<Integer, Double>();
			Collection<PercentEvent> points = day.getPercentEvents();

			// Skapar en kurva utifrån alla punkter.
			for (PercentEvent point : points) {
				XYChart.Data<Integer, Double> dataPoint = new XYChart.Data<Integer, Double>(
						point.getMinuteOfIntraday(), point.getPercent());
				serie.getData().add(dataPoint);
			}

			lineChartData.add(serie);
		}

		graph.setData(lineChartData);
		graph.setCreateSymbols(false);

		// Färglägg serierna.
		colorizeGraph(graph);
	}

	@FXML
	protected void hamtaNedgangarSenaste20Dagarna() {
		// Hämta data ifrån vyn.
		Calendar franTid = konvertera(franDatum.getText(), "yyyy-MM-dd");
		franTid.set(Calendar.HOUR_OF_DAY, 0);
		Calendar tillTid = konvertera(tillDatum.getText(), "yyyy-MM-dd");
		tillTid.set(Calendar.HOUR_OF_DAY, 23);

		// Anropar affärsskiktet.
		InstrumentServiceBD bd = new InstrumentServiceImpl();
		Collection<ProcentIntradag> days = bd.hamtaNedgangarPgaUSAOppning(
				franTid.getTime(), tillTid.getTime());

		ObservableList<XYChart.Series<Integer, Double>> lineChartData = FXCollections
				.observableArrayList();

		// Skapa grafer för alla dagar i samma diagram.
		for (ProcentIntradag day : days) {
			LineChart.Series<Integer, Double> serie = new LineChart.Series<Integer, Double>();
			Collection<PercentEvent> points = day.getPercentEvents();

			// Skapar en kurva utifrån alla punkter.
			for (PercentEvent point : points) {
				XYChart.Data<Integer, Double> dataPoint = new XYChart.Data<Integer, Double>(
						point.getMinuteOfIntraday(), point.getPercent());
				serie.getData().add(dataPoint);
			}

			lineChartData.add(serie);
		}

		graph.setData(lineChartData);
		graph.setCreateSymbols(false);

		// Färglägg serierna.
		colorizeGraph(graph);
	}

	@FXML
	protected void hamtaIngenRorelseSenasteDagarna() {
		// Hämta data ifrån vyn.
		Calendar franTid = konvertera(franDatum.getText(), "yyyy-MM-dd");
		franTid.set(Calendar.HOUR_OF_DAY, 0);
		Calendar tillTid = konvertera(tillDatum.getText(), "yyyy-MM-dd");
		tillTid.set(Calendar.HOUR_OF_DAY, 23);

		// Anropar affärsskiktet.
		InstrumentServiceBD bd = new InstrumentServiceImpl();
		Collection<ProcentIntradag> days = bd.hamtaIngenRorelsePgaUSAOppning(
				franTid.getTime(), tillTid.getTime());

		ObservableList<XYChart.Series<Integer, Double>> lineChartData = FXCollections
				.observableArrayList();

		// Skapa grafer för alla dagar i samma diagram.
		for (ProcentIntradag day : days) {
			LineChart.Series<Integer, Double> serie = new LineChart.Series<Integer, Double>();
			Collection<PercentEvent> points = day.getPercentEvents();

			// Skapar en kurva utifrån alla punkter.
			for (PercentEvent point : points) {
				XYChart.Data<Integer, Double> dataPoint = new XYChart.Data<Integer, Double>(
						point.getMinuteOfIntraday(), point.getPercent());
				serie.getData().add(dataPoint);
			}

			lineChartData.add(serie);
		}

		graph.setData(lineChartData);
		graph.setCreateSymbols(false);

		// Färglägg serierna.
		colorizeGraph(graph);
	}
}

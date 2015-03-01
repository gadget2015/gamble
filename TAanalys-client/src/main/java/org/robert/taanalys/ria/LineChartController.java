package org.robert.taanalys.ria;

import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import org.robert.taanalys.ria.businessdelegate.InstrumentServiceBD;
import org.robert.taanalys.ria.businessdelegate.InstrumentServiceImpl;
import org.robert.taanalys.ria.businessdelegate.PercentEvent;
import org.robert.taanalys.ria.businessdelegate.ProcentIntradag;

public class LineChartController extends LineGraphController{
	@FXML
	private LineChart<Integer, Double> graph;

	@FXML
	protected void hamta20senasteDagarna() {
		// Anropar affärsskiktet.
		InstrumentServiceBD bd = new InstrumentServiceImpl();
		Collection<ProcentIntradag> days = bd.fetch20LatestDays();

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

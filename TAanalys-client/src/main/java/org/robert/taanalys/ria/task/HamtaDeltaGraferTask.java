package org.robert.taanalys.ria.task;

import java.util.Collection;
import java.util.Date;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import org.robert.taanalys.ria.LineGraphController;
import org.robert.taanalys.ria.businessdelegate.InstrumentServiceBD;
import org.robert.taanalys.ria.businessdelegate.InstrumentServiceImpl;
import org.robert.taanalys.ria.businessdelegate.PercentEvent;
import org.robert.taanalys.ria.businessdelegate.ProcentIntradag;

public class HamtaDeltaGraferTask extends Task<Collection<ProcentIntradag>> {
	Date jamforTid;
	Date franTid;
	Collection<ProcentIntradag> days;
	LineChart<Integer, Double> graph;
	Collection<ProcentIntradag> liknandeDagar;

	public HamtaDeltaGraferTask(Date jamforTid, Date franTid,
			Collection<ProcentIntradag> days, LineChart<Integer, Double> graph) {
		this.franTid = franTid;
		this.jamforTid = jamforTid;
		this.days = days;
		this.graph = graph;
	}

	@Override
	protected Collection<ProcentIntradag> call() throws Exception {
		InstrumentServiceBD bd = new InstrumentServiceImpl();

		System.out.println("Hämtar liknande dagar...");
		Collection<ProcentIntradag> liknandeDagar = bd.hamtaLiknandeDagar(
				jamforTid, franTid);

		days.addAll(liknandeDagar); 
		System.out.println("Hämtat liknande dagar, ska rita upp dem nu.");
		
		// 4. Ritar upp grafen med alla dagarna.
		final ObservableList<XYChart.Series<Integer, Double>> lineChartData = FXCollections
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

		// Update UI from a Task, see
		// http://docs.oracle.com/javafx/2/api/javafx/concurrent/Task.html
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				graph.setData(lineChartData);
				graph.setCreateSymbols(false);

				// Färglägg serierna.
				LineGraphController controller = new LineGraphController();
				controller.NUMBER_OF_COLORS_POINTS_TO_INCREASE = 64;
				controller.colorizeGraph(graph);

				// Sätter grafen som man jämför med till tjockare och grön färg.
				graph.getData()
						.get(0)
						.getNode()
						.setStyle(
								"-fx-stroke: #00FF00; -fx-stroke-width: 5px; -fx-effect: null;");
			}
		});

		
		return liknandeDagar;
	}

	public Collection<ProcentIntradag> getValue2() {
		return liknandeDagar;
	}
}

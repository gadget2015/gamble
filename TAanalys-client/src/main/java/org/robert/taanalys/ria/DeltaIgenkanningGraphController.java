package org.robert.taanalys.ria;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

import org.robert.taanalys.ria.businessdelegate.InstrumentServiceBD;
import org.robert.taanalys.ria.businessdelegate.InstrumentServiceImpl;
import org.robert.taanalys.ria.businessdelegate.ProcentIntradag;
import org.robert.taanalys.ria.task.DeltaGraphProgressTask;
import org.robert.taanalys.ria.task.HamtaDeltaGraferTask;

public class DeltaIgenkanningGraphController extends LineGraphController {
	@FXML
	private LineChart<Integer, Double> graph;

	@FXML
	private TextField franDatum;

	@FXML
	private TextField jamforDatum;

	@FXML
	protected ProgressBar progressbar;

	@SuppressWarnings("unused")
	@FXML
	private void initialize() {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		Calendar today = Calendar.getInstance();
		jamforDatum.setText(sdf.format(today.getTime()));

		franDatum.setText("2013-01-20");
	}

	@FXML
	protected void sokEfterLiknandeDagar() {
		// Hämta data ifrån vyn.
		Calendar franTid = konvertera(franDatum.getText(), "yyyy-MM-dd");
		franTid.set(Calendar.HOUR_OF_DAY, 0);
		Calendar jamforTid = konvertera(jamforDatum.getText(), "yyyy-MM-dd");
		jamforTid.set(Calendar.HOUR_OF_DAY, 5);

		// Startar en progressbar.
		this.progressbar.setVisible(true);
		Task<Integer> task = new DeltaGraphProgressTask(antalManader(franTid),
				progressbar);
		progressbar.progressProperty().bind(task.progressProperty());
		new Thread(task).start();

		// Hämta 'jämför dagens graf' och rita upp den i en viss färg.
		Collection<ProcentIntradag> days = new ArrayList<ProcentIntradag>();
		InstrumentServiceBD bd = new InstrumentServiceImpl();
		ProcentIntradag jamforDagen = bd.hamtaProcentIntradag(jamforTid
				.getTime());

		days.add(jamforDagen);

		// Hämta grafer som påminner om den uppritade grafen.
		Task<Collection<ProcentIntradag>> hamtaDeltaDagarTask = new HamtaDeltaGraferTask(
				jamforTid.getTime(), franTid.getTime(), days, graph);
		Thread t = new Thread(hamtaDeltaDagarTask);
		t.start();
	}

	private int antalManader(Calendar franTid) {
		Calendar today = Calendar.getInstance();
		Date now = today.getTime();
		Date fran = franTid.getTime();
		int antalDagar = daysBetween(fran, now);

		return (int) (antalDagar / 30);
	}

	public int daysBetween(Date d1, Date d2) {
		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

}

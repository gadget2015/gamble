package org.robert.taanalys.ria.task;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;

/**
 * Det tar några sekunder att jämföra en månad. Denna task ritar upp en
 * progressbar som rör sig ett tick efter några sekunder.
 * 
 */
public class DeltaGraphProgressTask extends Task<Integer> {
	int antalManader;
	ProgressBar progressbar;

	public DeltaGraphProgressTask(int antalManader, ProgressBar progressbar) {
		this.antalManader = antalManader;
		this.progressbar = progressbar;
	}

	@Override
	public Integer call() {
		for (int i = 1; i <= antalManader; i++) {
			if (isCancelled()) {
				break;
			}

			updateProgress(i, antalManader);

			try {
				// Uppdaterar progressbaren när man beräknat en månad.
				Thread.sleep(4200);
			} catch (InterruptedException interrupted) {
				if (isCancelled()) {
					updateMessage("Cancelled");
					break;
				}
			}

		}

		// Gömmer progressbaren eftersom grafen nu är klar.
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				progressbar.setVisible(false);
			}
		});

		return antalManader;
	}

}

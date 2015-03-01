package org.robert.tips.stryktips.finder.desktopclient.dialog.searchsystem;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import org.robert.tips.stryktips.finder.desktopclient.DomainException;
import org.robert.tips.stryktips.finder.desktopclient.Main;
import org.robert.tips.stryktips.finder.desktopclient.TechnicalException;
import org.robert.tips.stryktips.finder.desktopclient.businessdelegate.Algorithm;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.AlgorithmContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.iterative.IterativeContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.random.RandomContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.algorithm.stepthrough.StepThroughContext;
import org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd.StryktipsFinderBD;

/**
 * Code that runs in a thread. Calculates a new stryktipssystem.
 * 
 * @author Robert
 * 
 */
public class FindStryktipsSystemRunnable extends SwingWorker<String, String> {
	StryktipsFinderBD businessDelegate;
	long systemId;
	SearchCallbackHandler callbackHandler;
	boolean continueSearch = true;
	Algorithm algorithm;

	public FindStryktipsSystemRunnable(StryktipsFinderBD businessDelegate,
			long systemId, SearchCallbackHandler callbackHandler,
			Algorithm algorithm) {
		this.businessDelegate = businessDelegate;
		this.systemId = systemId;
		this.callbackHandler = callbackHandler;
		this.algorithm = algorithm;
	}

	@SuppressWarnings("static-access")
	public void run2() {
		AlgorithmContext context = null;
		if (Algorithm.RANDOM == algorithm) {
			context = new RandomContext(businessDelegate, systemId,
					callbackHandler);
		} else if (Algorithm.ITERATIVE == algorithm) {
			context = new IterativeContext(businessDelegate, systemId,
					callbackHandler);
		} else if (Algorithm.STEP_THROUGH == algorithm) {
			context = new StepThroughContext(businessDelegate, systemId,
					callbackHandler);
		} else {
			JOptionPane.showMessageDialog(null, "Okänd sökalgoritm, typ = "
					+ algorithm, "Okänd sökalgoritm.",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		long iterations = 0;
		long previousNumberOfSearches = 0;
		long previousTime = System.currentTimeMillis();
		int sleepInterval = 0;

		if (Main.model.threadPriority == Thread.MAX_PRIORITY) {
			sleepInterval = 100000;
		} else if (Main.model.threadPriority == Thread.MIN_PRIORITY) {
			sleepInterval = 100;
		} else {
			sleepInterval = 1000;
		}

		while (continueSearch) {
			// Do loop infinitvily
			try {
				context.next();
			} catch (TechnicalException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),
						"Tekniskt fel", JOptionPane.ERROR_MESSAGE);
				context.callbackHandler.endSearch();
			} catch (DomainException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),
						"Domän fel/Logiskt fel", JOptionPane.WARNING_MESSAGE);
				context.callbackHandler.endSearch();
			} catch (Throwable e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, e1.getMessage(),
						"Okänt fel.", JOptionPane.ERROR_MESSAGE);
				context.callbackHandler.endSearch();
			}

			if (iterations++ % sleepInterval == 0) {
				// Update search speed.
				long workTime = System.currentTimeMillis() - previousTime;
				float numberOfSeconds = (workTime / 1000 == 0) ? 1
						: (workTime / 1000);
				float currentSearchSpeed = (iterations - previousNumberOfSearches)
						/ numberOfSeconds;
				int searchSpeed = (currentSearchSpeed == 1.0) ? 0
						: (int) currentSearchSpeed;
				callbackHandler.setSearchSpeed(searchSpeed);

				// set data used next time the chunk is called.
				previousNumberOfSearches = iterations;
				previousTime = System.currentTimeMillis();

				try {
					// Sleep for a while.
					Thread.currentThread().sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e.getMessage(),
							"Thread InterruptedException fel.",
							JOptionPane.ERROR_MESSAGE);
					context.callbackHandler.endSearch();
				}
			}
		}
	}

	public void stopSearch() {
		continueSearch = false;

	}

	@Override
	protected String doInBackground() throws Exception {
		run2();

		return "ok";
	}
}

package org.robert.taanalys.webgui.dialog;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.robert.taanalys.scheduler.StockholmExchangeSchedulerManager;
import org.robert.taanalys.scheduler.TaskExecutionInfo;

public class SchemalagdaJobbViewModelTest {
	
	@Test
	public void bordeListaMedSchemalagdaJobb() {
		// Given
		SchemalagdaJobbViewModel viewModel = new SchemalagdaJobbViewModel();
		viewModel.schedulerManager = new StockholmExchangeSchedulerManager();
		viewModel.schedulerManager.result.add(new TaskExecutionInfo("Det gick bra för OMXS30.", new Date()));
		viewModel.schedulerManager.result.add(new TaskExecutionInfo("Det gick fel för OMXS30.", new Date()));
		viewModel.schedulerManager.result.add(new TaskExecutionInfo("Det gick bra för OMXS30.", new Date()));

		// When
		viewModel.initDialog();

		// Then
		Assert.assertEquals("Borde visa en lista med tre schemalagda jobb.", 3,
				viewModel.getTaskExecutionInfos().size());
	}
}

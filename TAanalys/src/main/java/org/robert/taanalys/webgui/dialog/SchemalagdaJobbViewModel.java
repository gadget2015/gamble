package org.robert.taanalys.webgui.dialog;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

import org.robert.taanalys.scheduler.StockholmExchangeSchedulerManager;
import org.robert.taanalys.scheduler.TaskExecutionInfo;

@ManagedBean
@RequestScoped
public class SchemalagdaJobbViewModel {
	private Collection<TaskExecutionInfo> taskExecutionInfos;

	@EJB
	StockholmExchangeSchedulerManager schedulerManager = new StockholmExchangeSchedulerManager("mock");

	@PostConstruct
	public void initDialog() {
		this.taskExecutionInfos = schedulerManager.result();
	}

	public void setTaskExecutionInfos(
			Collection<TaskExecutionInfo> taskExecutionInfos) {
		this.taskExecutionInfos = taskExecutionInfos;
	}

	public Collection<TaskExecutionInfo> getTaskExecutionInfos() {
		return taskExecutionInfos;
	}

}

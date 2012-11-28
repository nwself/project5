package edu.cs5774.project5;

import javax.swing.JSplitPane;

public class DocumentPane extends JSplitPane implements TaskBugSelectionListener {

	GanttChartPane ganttPane;
	ProjectDetailsPane projectDetailsPane;
	TaskBugDetailsPane taskBugDetailsPane;
	
	public DocumentPane(Project project) {
		ganttPane = new GanttChartPane(project);
		projectDetailsPane = new ProjectDetailsPane(project);
		taskBugDetailsPane = new TaskBugDetailsPane();
		
		this.setLeftComponent(ganttPane);
		this.setRightComponent(projectDetailsPane);

		ganttPane.addTaskBugSelectionListener(this);
	}

	@Override
	public void taskBugSelected(TaskBug taskBug) {
		taskBugDetailsPane.updateDetails(taskBug);
		this.setRightComponent(taskBugDetailsPane);
	}

}

package edu.cs5774.project5;

import javax.swing.JSplitPane;

public class DocumentPane extends JSplitPane implements TaskBugSelectionListener, ProjectSelectionListener, DeletionRequestedListener {

	private Project project;

	private GanttChartPane ganttPane;
	private ProjectDetailsPane projectDetailsPane;
	private TaskBugDetailsPane taskBugDetailsPane;
	
	public DocumentPane(Project project) {
		this.project = project;
		
		ganttPane = new GanttChartPane(project);
		projectDetailsPane = new ProjectDetailsPane(project);
		taskBugDetailsPane = new TaskBugDetailsPane();
		
		this.setLeftComponent(ganttPane);
		this.setRightComponent(projectDetailsPane);

		ganttPane.addTaskBugSelectionListener(this);
		ganttPane.addProjectSelectionListener(this);
		ganttPane.addDeletionRequestedListener(this);
	}

	@Override
	public void taskBugSelected(TaskBug taskBug) {
		taskBugDetailsPane.updateDetails(taskBug);
		this.setRightComponent(taskBugDetailsPane);
	}

	@Override
	public void projectSelected(Project project) {
		this.setRightComponent(projectDetailsPane);
	}

	@Override
	public void deletionRequested() {
		TaskBug selectedTaskBug = ganttPane.getSelectedTaskBug();
		
		if (selectedTaskBug != null) {
			project.removeTaskBug(selectedTaskBug);
			ganttPane.setProject(project);
		}
	}
}

package edu.cs5774.project5;

import java.util.LinkedList;
import java.util.Stack;

import javax.swing.JSplitPane;

public class DocumentPane extends JSplitPane implements TaskBugSelectionListener, ProjectSelectionListener, DeletionRequestedListener {

	private Project project;

	private GanttChartPane ganttPane;
	private ProjectDetailsPane projectDetailsPane;
	private TaskBugDetailsPane taskBugDetailsPane;

	private Stack<Project> undoStack = new Stack<Project>();
	private Stack<Project> redoStack = new Stack<Project>();

	private LinkedList<ActionEnabledListener> listeners = new LinkedList<ActionEnabledListener>();
	
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
		if (taskBug != null) {
			taskBugDetailsPane.updateDetails(taskBug);
			this.setRightComponent(taskBugDetailsPane);
		}
		fireDeleteEnabled(taskBug != null);
	}

	@Override
	public void projectSelected(Project project) {
		this.setRightComponent(projectDetailsPane);
		fireDeleteEnabled(false);
	}

	@Override
	public void deletionRequested() {
		undoStack.push(project);
		project = new Project(project);
		
		TaskBug selectedTaskBug = ganttPane.getSelectedTaskBug();
		
		if (selectedTaskBug != null) {
			project.removeTaskBug(selectedTaskBug);
			ganttPane.setProject(project);
		}
		
		redoStack.clear();
		fireUndoRedoEnabled();
	}
	
	private void fireUndoRedoEnabled() {
		for (ActionEnabledListener listener : listeners ) {
			listener.undoRedoEnabled(!undoStack.empty(), !redoStack.empty());
		}
	}

	public void undoDeletion() {
		redoStack.push(project);
		project = undoStack.pop();
		ganttPane.setProject(project);
		
		fireUndoRedoEnabled();
	}
	
	public void redoDeletion() {
		undoStack.push(project);
		project = redoStack.pop();
		ganttPane.setProject(project);
		
		fireUndoRedoEnabled();
	}

	public void addUndoRedoEnabledListener(ActionEnabledListener listener) {
		if (listener != null) {
			listeners.add(listener);
		}
	}

	private void fireDeleteEnabled(boolean deleteEnabled) {
		for (ActionEnabledListener listener : listeners) {
			listener.deleteEnabled(deleteEnabled);
		}
	}
}

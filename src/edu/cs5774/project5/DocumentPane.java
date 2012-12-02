package edu.cs5774.project5;

import java.util.LinkedList;
import java.util.Stack;

import javax.swing.JSplitPane;

public class DocumentPane extends JSplitPane implements ProjectSelectionListener, DeletionRequestedListener {

	private static final long serialVersionUID = -924992963282055777L;

	private Project project;

	private GanttChartPane ganttPane;
	private DetailsPane detailsPane;

	private Stack<Project> undoStack = new Stack<Project>();
	private Stack<Project> redoStack = new Stack<Project>();

	private LinkedList<ActionEnabledListener> listeners = new LinkedList<ActionEnabledListener>();
	
	public DocumentPane(Project project) {
		this.project = project;
		
		ganttPane = new GanttChartPane(project);
		detailsPane = new DetailsPane(project);
		
		this.setLeftComponent(ganttPane);
		this.setRightComponent(detailsPane);

		
		ganttPane.addProjectSelectionListener(this);
		ganttPane.addDeletionRequestedListener(this);
	}


	@Override
	public void projectSelected(Project project) {
		detailsPane.showProject();
		fireDeleteEnabled(false);
	}

	@Override
	public void deletionRequested() {
		undoStack.push(project);
		project = new Project(project);
		
		TaskBug selectedTaskBug = ganttPane.getSelectedTaskBug();
		
		if (selectedTaskBug != null) {
			project.removeTask(selectedTaskBug);
			detailsPane.setProject(project);
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
		detailsPane.setProject(project);
		ganttPane.setProject(project);
		
		fireUndoRedoEnabled();
	}
	
	public void redoDeletion() {
		undoStack.push(project);
		project = redoStack.pop();
		detailsPane.setProject(project);
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

	public Project getProject() {
		return project;
	}
}

package edu.cs5774.project5;

import java.util.HashMap;

import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;

public class TaskBugSeriesCollection extends TaskSeriesCollection implements TaskBugSelectionListener, ProjectSelectionListener {

	private HashMap<String, TaskBug> taskBugs = new HashMap<String, TaskBug>();
	private GanttSelectionRenderer ganttSelectionRenderer;

	public TaskBugSeriesCollection(Project project, GanttSelectionRenderer ganttSelectionRenderer) {
		this.ganttSelectionRenderer = ganttSelectionRenderer;
		
		// Make sure selection is clear
		ganttSelectionRenderer.setSelectedColumn(-1);

		// Create task series for tasks and bugs
		TaskSeries taskSeries = new TaskSeries("Tasks");
		TaskSeries bugSeries = new TaskSeries("Bugs");
		
		for (TaskBug taskBug : project.getTask()) {
			Task chartTask = new Task(taskBug.getTitle(), 
					new SimpleTimePeriod(taskBug.getCreatedAt().getTime(), taskBug.getDueDate().getTime()));
			chartTask.setPercentComplete(taskBug.getPercentageCompleted() / 100.0);
		    
			// Add to appropriate task series
			if (taskBug.isTask()) {
		    	taskSeries.add(chartTask);
		    } else {
		    	bugSeries.add(chartTask);
		    }
			
			taskBugs.put(taskBug.getTitle(), taskBug);
		}
		
		// Add them to `this` (NOTE: I am a TaskSeriesCollection)
        this.add(taskSeries);
        this.add(bugSeries);
	}

	public TaskBug getTaskBugByTitle(String taskBugTitle) {
		TaskBug selectedTaskBug = null;
		
		if (taskBugs.containsKey(taskBugTitle)) {
			selectedTaskBug = taskBugs.get(taskBugTitle);
		}
		
		return selectedTaskBug;
	}
	
	public TaskBug getSelectedTaskBug() {
		TaskBug selectedTaskBug = null;
		
		int selectedColumn = ganttSelectionRenderer.getSelectedColumn();
		if (selectedColumn != -1) {
			String taskBugTitle = (String) this.getColumnKey(selectedColumn);
			selectedTaskBug = this.getTaskBugByTitle(taskBugTitle);
		}
		
		return selectedTaskBug;
	}

	@Override
	public void taskBugSelected(TaskBug taskBug) {
		int columnIndex = -1;
		
		if (taskBug != null) {
			columnIndex = this.getColumnIndex(taskBug.getTitle());
		}
		
		ganttSelectionRenderer.setSelectedColumn(columnIndex);
	}

	@Override
	public void projectSelected(Project project) {
		// No tasks/bugs are selected when the project is selected
		ganttSelectionRenderer.setSelectedColumn(-1);
	}
}

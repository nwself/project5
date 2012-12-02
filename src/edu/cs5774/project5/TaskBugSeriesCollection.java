package edu.cs5774.project5;

import java.util.HashMap;
import java.util.List;

import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;

public class TaskBugSeriesCollection extends TaskSeriesCollection implements TaskBugSelectionListener, ProjectSelectionListener {

	private static final long serialVersionUID = 4675461723005553511L;
	private HashMap<String, TaskBug> taskBugs = new HashMap<String, TaskBug>();
	private GanttSelectionRenderer ganttSelectionRenderer;
	
	public TaskBugSeriesCollection(Project project, GanttSelectionRenderer ganttSelectionRenderer) {
		this.ganttSelectionRenderer = ganttSelectionRenderer;
		
		// Make sure selection is clear
		ganttSelectionRenderer.setSelectedColumn(-1);

		// Create task series for tasks and bugs
		TaskSeries taskSeries = new TaskSeries("Tasks");
		TaskSeries bugSeries = new TaskSeries("Bugs");
		
		addTaskBugsToSeries(project.getTask(), taskSeries);
		addTaskBugsToSeries(project.getBug(), bugSeries);

		// Add them to `this` (NOTE: I am a TaskSeriesCollection)
        this.add(taskSeries);
        this.add(bugSeries);
	}

	private void addTaskBugsToSeries(List<TaskBug> taskBugList, TaskSeries series) {
		for (TaskBug taskBug : taskBugList) {
			Task chartTask = new Task(taskBug.getTitle(), 
					new SimpleTimePeriod(taskBug.getCreatedAt().getTime(), taskBug.getDueDate().getTime()));
			chartTask.setPercentComplete(taskBug.getPercentageCompleted() / 100.0);
		    
			// Add to appropriate task series
			series.add(chartTask);
			
			taskBugs.put(taskBug.getTitle(), taskBug);
		}
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

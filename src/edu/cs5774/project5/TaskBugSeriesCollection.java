package edu.cs5774.project5;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;

/**
 * This class extends the functionality of JFreeChart's TaskSeriesCollection to create
 * a Gantt chart for a Project, i.e. displaying Tasks and Bugs on the chart.  It also
 * uses a GanttSelectionRenderer on the data to allow for task/bug selection.
 *
 */
public class TaskBugSeriesCollection extends TaskSeriesCollection implements TaskBugSelectionListener, ProjectSelectionListener {

	private static final long serialVersionUID = 4675461723005553511L;
	private HashMap<String, TaskBug> taskBugs = new HashMap<String, TaskBug>();
	private GanttSelectionRenderer ganttSelectionRenderer;
	
	private TaskSeries taskSeries;
	
	public TaskBugSeriesCollection(Project project, GanttSelectionRenderer ganttSelectionRenderer) {
		this.ganttSelectionRenderer = ganttSelectionRenderer;
		
		project.addTaskBugSelectionListener(this);
		
		// Make sure selection is clear
		ganttSelectionRenderer.setSelectedColumn(-1);

		// Create task series for tasks and bugs
		taskSeries = new TaskSeries("Tasks");
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

	@Override
	public void taskBugSelected(TaskBug taskBug) {
		int columnIndex = -1;
		
		if (taskBug != null) {
			columnIndex = this.getColumnIndex(taskBug.getTitle());
		}
		
		ganttSelectionRenderer.setSelectedColumn(columnIndex);
		
		// This is a hack required because JFreeChart will not redraw
		// despite calls to repaint/revalidate unless the data has changed.
		Task emptyTask = new Task("", new Date(), new Date());
		taskSeries.add(emptyTask);
		taskSeries.remove(emptyTask);
	}

	@Override
	public void projectSelected(Project project) {
		// No tasks/bugs are selected when the project is selected
		ganttSelectionRenderer.setSelectedColumn(-1);
	}
}

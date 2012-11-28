package edu.cs5774.project5;

import java.util.HashMap;

import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;

public class TaskBugSeriesCollection extends TaskSeriesCollection {

	private HashMap<String, TaskBug> taskBugs = new HashMap<>();

	public TaskBugSeriesCollection(Project project) {
		TaskSeries taskSeries = new TaskSeries("Tasks");
		TaskSeries bugSeries = new TaskSeries("Bugs");
		
		for (TaskBug taskBug : project.getTaskBugs()) {
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
		
        this.add(taskSeries);
        this.add(bugSeries);
	}

	public TaskBug getTaskBugByTitle(String uniqueId) {
		TaskBug selectedTaskBug = null;
		
		if (taskBugs.containsKey(uniqueId)) {
			selectedTaskBug = taskBugs.get(uniqueId);
		}
		
		return selectedTaskBug;
	}
}

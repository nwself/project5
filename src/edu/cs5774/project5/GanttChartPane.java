package edu.cs5774.project5;

import java.awt.Cursor;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.CategoryLabelEntity;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.TitleEntity;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;

public class GanttChartPane extends JPanel implements ChartMouseListener {

	private static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
	
	private Project project;
	
	private IntervalCategoryDataset dataset;
	
	private HashMap<String, TaskBug> taskBugs = new HashMap<>();
	private LinkedList<TaskBugSelectionListener> taskBugListeners = new LinkedList<>();
	private LinkedList<ProjectSelectionListener> projectListeners = new LinkedList<>();

	private GanttSelectionRenderer ganttSelectionRenderer;

	public GanttChartPane(Project project) {
		super(new GridLayout(0, 1));
		this.project = project;
		
			dataset = createDataset(project);
        JFreeChart chart = ChartFactory.createGanttChart(project.getName(), "Task/Bug", "Date", dataset, true, true, false);
        
        ganttSelectionRenderer = new GanttSelectionRenderer();
        
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setRenderer(ganttSelectionRenderer);
        
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.addChartMouseListener(this);
		this.add(chartPanel);
	}

	private IntervalCategoryDataset createDataset(Project project) {
		TaskSeries scheduled = new TaskSeries("Scheduled");
		
		for (TaskBug taskBug : project.getTaskBugs()) {
			Task chartTask = new Task(taskBug.getTitle(), 
					new SimpleTimePeriod(taskBug.getCreatedAt().getTime(), taskBug.getDueDate().getTime()));
			chartTask.setPercentComplete(taskBug.getPercentageCompleted() / 100.0);
		    scheduled.add(chartTask);
			
			taskBugs.put(taskBug.getTitle(), taskBug);
		}
		
        TaskSeriesCollection collection = new TaskSeriesCollection();
        collection.add(scheduled);

        return collection;
	}

	public void addTaskBugSelectionListener(TaskBugSelectionListener listener) {
		taskBugListeners.add(listener);
	}

	private void fireTaskBugSelected(String uniqueId) {
		if (taskBugs.containsKey(uniqueId)) {
			TaskBug selectedTaskBug = taskBugs.get(uniqueId);

			for (TaskBugSelectionListener listener : taskBugListeners) {
				listener.taskBugSelected(selectedTaskBug);
			}
		}
	}

	public void addProjectSelectionListener(ProjectSelectionListener listener) {
		projectListeners.add(listener);
	}

	private void fireProjectSelected(Project project) {
		for (ProjectSelectionListener listener : projectListeners ) {
			listener.projectSelected(project);
		}
	}
	
	@Override
	public void chartMouseClicked(ChartMouseEvent chartEvent) {
		ChartEntity chartEntity = chartEvent.getEntity();
		if (chartEntity instanceof CategoryItemEntity) {
			CategoryItemEntity entity = (CategoryItemEntity) chartEntity;
			fireTaskBugSelected((String) entity.getColumnKey());
			ganttSelectionRenderer.setSelectedColumn(dataset.getColumnIndex(entity.getColumnKey()));
		} else if (chartEntity instanceof CategoryLabelEntity) {
			CategoryLabelEntity entity = (CategoryLabelEntity) chartEntity;
			fireTaskBugSelected((String) entity.getKey());
			ganttSelectionRenderer.setSelectedColumn(dataset.getColumnIndex(entity.getKey()));
		} else if (chartEntity instanceof TitleEntity) {
			// Selecting the title always selects the project, no need to inspect the entity since
			// there is only one project per chart.
			fireProjectSelected(project);
			ganttSelectionRenderer.setSelectedColumn(-1);
		} else {
			ganttSelectionRenderer.setSelectedColumn(-1);
		}
	}

	@Override
	public void chartMouseMoved(ChartMouseEvent chartEvent) {
		ChartEntity chartEntity = chartEvent.getEntity();
		if ((chartEntity instanceof CategoryItemEntity) || 
				(chartEntity instanceof CategoryLabelEntity) ||
				(chartEntity instanceof TitleEntity)) {
			this.setCursor(HAND_CURSOR);
		} else {
			this.setCursor(null);
		}
	}

}

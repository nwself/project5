package edu.cs5774.project5;

import java.awt.GridLayout;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.CategoryLabelEntity;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;

public class GanttChartPane extends JPanel implements ChartMouseListener {

	private HashMap<String, TaskBug> taskBugs = new HashMap<>();
	private LinkedList<TaskBugSelectionListener> listeners = new LinkedList<>();

	public GanttChartPane(Project project) {
		super(new GridLayout(0, 1));
		
		IntervalCategoryDataset dataset = createDataset(project);
        JFreeChart chart = ChartFactory.createGanttChart(project.getName(), "Task/Bug", "Date", dataset, true, true, false);
        
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.addChartMouseListener(this);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		this.add(chartPanel);
	}

	private IntervalCategoryDataset createDataset(Project project) {
		TaskSeries scheduled = new TaskSeries("Scheduled");
		
		for (TaskBug taskBug : project.getTaskBugs()) {
			scheduled.add(new Task(taskBug.getTitle(), 
					      new SimpleTimePeriod(taskBug.getCreatedAt().getTime(), taskBug.getDueDate().getTime())));
			
			taskBugs.put(taskBug.getTitle(), taskBug);
		}
		
        TaskSeriesCollection collection = new TaskSeriesCollection();
        collection.add(scheduled);

        return collection;
	}

	public void addTaskBugSelectionListener(TaskBugSelectionListener listener) {
		listeners.add(listener);
	}
	
	private void fireTaskBugSelected(String uniqueId) {
		if (taskBugs.containsKey(uniqueId)) {
			for (TaskBugSelectionListener listener : listeners) {
				listener.taskBugSelected(taskBugs.get(uniqueId));
			}
		}
	}

	@Override
	public void chartMouseClicked(ChartMouseEvent chartEvent) {
		ChartEntity chartEntity = chartEvent.getEntity();
		if (chartEntity instanceof CategoryItemEntity) {
			CategoryItemEntity entity = (CategoryItemEntity) chartEntity;
			fireTaskBugSelected((String) entity.getColumnKey());
		} else if (chartEntity instanceof CategoryLabelEntity) {
			CategoryLabelEntity entity = (CategoryLabelEntity) chartEntity;
			fireTaskBugSelected((String) entity.getKey());
		}
	}


	@Override
	public void chartMouseMoved(ChartMouseEvent arg0) {
		// Do nothing
	}

}

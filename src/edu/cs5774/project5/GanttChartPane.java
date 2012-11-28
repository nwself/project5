package edu.cs5774.project5;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

public class GanttChartPane extends JPanel implements ChartMouseListener, KeyListener {

	private static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
	
	private Project project;
	
	private TaskBugSeriesCollection dataset;
	
	private LinkedList<TaskBugSelectionListener> taskBugListeners = new LinkedList<>();
	private LinkedList<ProjectSelectionListener> projectListeners = new LinkedList<>();

	private GanttSelectionRenderer ganttSelectionRenderer = new GanttSelectionRenderer();
	private ChartPanel chartPanel;
	
	public GanttChartPane(Project project) {
		super(new GridLayout(0, 1));
		this.project = project;

		this.setProject(project);
		
		this.addKeyListener(this);
	}

	public void setProject(Project project) {
		this.project = project;
		
		// Clear selection
		ganttSelectionRenderer.setSelectedColumn(-1);
		
		// Rebuild dataset and chart
		dataset = new TaskBugSeriesCollection(project);
		JFreeChart chart = createChart(project.getName(), dataset);
		
		// Set up the new chart's panel
		if (chartPanel == null) {
			// This case is for the call from the constructor
			chartPanel = new ChartPanel(chart);
			chartPanel.addChartMouseListener(this);
			this.add(chartPanel);
		} else {
			// Show the new chart
			chartPanel.setChart(chart);
		}
		
		// By default the new project is selected
		fireProjectSelected(project);
	}
	
	private JFreeChart createChart(String chartTitle, IntervalCategoryDataset dataset) {
		JFreeChart chart = ChartFactory.createGanttChart(project.getName(), "Task/Bug", "Date", dataset, true, true, false);
		
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setRenderer(ganttSelectionRenderer);

		return chart;
	}

	public void addTaskBugSelectionListener(TaskBugSelectionListener listener) {
		taskBugListeners.add(listener);
	}

	private void fireTaskBugSelected(String uniqueId) {
		TaskBug selectedTaskBug = dataset.getTaskBugByTitle(uniqueId);
		if (selectedTaskBug != null) {
			for (TaskBugSelectionListener listener : taskBugListeners) {
				listener.taskBugSelected(selectedTaskBug);
			}
		}
		
//		if (taskBugs.containsKey(uniqueId)) {
//			TaskBug selectedTaskBug = taskBugs.get(uniqueId);
//
//			for (TaskBugSelectionListener listener : taskBugListeners) {
//				listener.taskBugSelected(selectedTaskBug);
//			}
//		}
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
        // Ask for keyboard input
        this.requestFocusInWindow();
		
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
	
	public void deleteSelectedElement() {
		int selectedColumn = ganttSelectionRenderer.getSelectedColumn();
		if (selectedColumn != -1) {
			String uniqueId = (String) dataset.getColumnKey(selectedColumn);
			
			TaskBug selectedTaskBug = dataset.getTaskBugByTitle(uniqueId);
			if (selectedTaskBug != null) {
				project.removeTaskBug(selectedTaskBug);
				setProject(project);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Do nothing
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DELETE ||
				e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			deleteSelectedElement();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Do nothing
	}
}
 
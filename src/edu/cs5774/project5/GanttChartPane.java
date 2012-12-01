package edu.cs5774.project5;

import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

public class GanttChartPane extends JPanel implements ChartMouseListener, KeyListener {

	private static final long serialVersionUID = -1201780568482094473L;

	private static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
	
	private Project project;
	
	private TaskBugSeriesCollection dataset;
	
	private LinkedList<TaskBugSelectionListener> taskBugListeners = new LinkedList<TaskBugSelectionListener>();
	private LinkedList<ProjectSelectionListener> projectListeners = new LinkedList<ProjectSelectionListener>();
	private LinkedList<DeletionRequestedListener> deletionListeners =  new LinkedList<DeletionRequestedListener>();
	
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
		
		// Rebuild dataset
		dataset = new TaskBugSeriesCollection(project, ganttSelectionRenderer);
		this.addTaskBugSelectionListener(dataset);
		this.addProjectSelectionListener(dataset);
		
		// Rebuild chart
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
		if (listener != null) {
			taskBugListeners.add(listener);
		}
	}

	private void fireTaskBugSelected(TaskBug selectedTaskBug) {
		for (TaskBugSelectionListener listener : taskBugListeners) {
			listener.taskBugSelected(selectedTaskBug);
		}
	}

	public void addProjectSelectionListener(ProjectSelectionListener listener) {
		if (listener != null) {
			projectListeners.add(listener);
		}
	}

	private void fireProjectSelected(Project project) {
		for (ProjectSelectionListener listener : projectListeners ) {
			listener.projectSelected(project);
		}
	}
	
	public void addDeletionRequestedListener(DeletionRequestedListener listener) {
		if (listener != null) {
			deletionListeners.add(listener);
		}
	}
	
	@Override
	public void chartMouseClicked(ChartMouseEvent chartEvent) {
        // Ask for keyboard input so that we get delete key presses
        this.requestFocusInWindow();
		
        // Get the title string out of the entity and get TaskBug from TaskBugSeriesCollection
		ChartEntity chartEntity = chartEvent.getEntity();
		if (chartEntity instanceof CategoryItemEntity) {
			CategoryItemEntity entity = (CategoryItemEntity) chartEntity;
			String entityTitle = (String) entity.getColumnKey();
			
			fireTaskBugSelected(dataset.getTaskBugByTitle(entityTitle));
		} else if (chartEntity instanceof CategoryLabelEntity) {
			CategoryLabelEntity entity = (CategoryLabelEntity) chartEntity;
			String entityTitle = (String) entity.getKey();
			
			fireTaskBugSelected(dataset.getTaskBugByTitle(entityTitle));
		} else if (chartEntity instanceof TitleEntity) {
			// Selecting the title always selects the project, no need to inspect the entity since
			// there is only one project per chart.
			fireProjectSelected(project);
		} else {
			// Send null to clear out selection
			fireTaskBugSelected(null);
		}
	}

	@Override
	public void chartMouseMoved(ChartMouseEvent chartEvent) {
		ChartEntity chartEntity = chartEvent.getEntity();
		
		// Change to hand cursor over every selectable item
		if ((chartEntity instanceof CategoryItemEntity) || 
				(chartEntity instanceof CategoryLabelEntity) ||
				(chartEntity instanceof TitleEntity)) {
			this.setCursor(HAND_CURSOR);
		} else {
			this.setCursor(null);
		}
	}
	
	public void deleteSelectedElement() {
		TaskBug selectedTaskBug = dataset.getSelectedTaskBug();
		if (selectedTaskBug != null) {
			fireDeletionRequested();
		}
	}

	private void fireDeletionRequested() {
		for (DeletionRequestedListener listener : deletionListeners) {
			listener.deletionRequested();
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

	public TaskBug getSelectedTaskBug() {
		return dataset.getSelectedTaskBug();
	}
}
 
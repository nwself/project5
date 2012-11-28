package edu.cs5774.project5;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TaskBugDetailsPane extends JPanel {
	
	TaskBug taskBug;
	JLabel stubLabel;
	
	public TaskBugDetailsPane() {
		super(new GridLayout(0, 1));
		
		stubLabel = new JLabel("Empty task bug pane (should never see this)");
		this.add(stubLabel);
	}
	
	public TaskBugDetailsPane(TaskBug taskBug) {
			super(new GridLayout(0, 1));
		this.taskBug = taskBug;
		
		stubLabel = new JLabel("Details for " + taskBug.getTitle());
		this.add(stubLabel);
	}
	
	public void updateDetails(TaskBug taskBug) {
		this.taskBug = taskBug;
		stubLabel.setText("Details for " + taskBug.getTitle());
	}
}

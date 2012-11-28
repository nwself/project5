package edu.cs5774.project5;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TaskBugDetailsPane extends JPanel {
	
	TaskBug taskBug;
	
	public TaskBugDetailsPane(TaskBug taskBug) {
		super(new GridLayout(0, 1));
		this.taskBug = taskBug;
		
		this.add(new JLabel("Details for " + taskBug.getTitle()));
	}
}

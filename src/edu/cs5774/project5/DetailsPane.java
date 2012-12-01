package edu.cs5774.project5;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class DetailsPane extends JPanel {
	
	private JLabel stubLabel;
	private Project project;
	
	public DetailsPane(Project project) {
		super(new GridLayout(0, 1));
		this.project = project;
		
		stubLabel = new JLabel("Empty task bug pane (should never see this)");
		this.add(stubLabel);
	}
	
	public void showTaskBug(TaskBug taskBug) {
		stubLabel.setText("Details for " + taskBug.getTitle());
	}

	public void showProject() {
		stubLabel.setText("Details for " + project.getName());
	}
}

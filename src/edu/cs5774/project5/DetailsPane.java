package edu.cs5774.project5;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class DetailsPane extends JPanel {
	
	private JPanel projectPanel;
	private JPanel taskBugPanel;
	
	public DetailsPane(Project project) {
		super(new GridLayout(0, 1));

		// Create taskBugPanel, it will be filled in later
		taskBugPanel = new JPanel(new FlowLayout());
		taskBugPanel.setVisible(false);
		this.add(taskBugPanel);
		
		projectPanel = new JPanel(new BorderLayout());
		fillInProjectPanel(project);
		this.add(projectPanel);
	}
	
	private void fillInProjectPanel(Project project) {
		JLabel nameLabel = new JLabel("Details for " + project.getName());
		JLabel descriptionLabel = new JLabel("Details for " + project.getDescription());
		JLabel createdLabel = new JLabel("Details for " + project.getCreatedAt());
		projectPanel.add(nameLabel);
		projectPanel.add(descriptionLabel);
		projectPanel.add(createdLabel);
		
	}

	private void fillInTaskBugPanel(TaskBug taskBug) {
		taskBugPanel.removeAll();
		
		JLabel titleLabel = new JLabel("Details for " + taskBug.getTitle());
		taskBugPanel.add(titleLabel);
	}

	public void showTaskBug(TaskBug taskBug) {
		projectPanel.setVisible(false);

		fillInTaskBugPanel(taskBug);
		taskBugPanel.setVisible(true);
	}

	public void showProject() {
		projectPanel.setVisible(true);
		taskBugPanel.setVisible(false);
	}
}

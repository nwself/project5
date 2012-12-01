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
	
	private Project project;
	
	public DetailsPane(Project project) {
		super(new GridLayout(0, 1));
		this.project = project;
		
		// Create taskBugPanel, it will be filled in later
//		taskBugPanel = new JPanel(new GridLayout(0,1));
//		taskBugPanel.setVisible(false);
//		this.add(taskBugPanel);
//		
//		projectPanel = new JPanel(new GridLayout(0,1));
		fillInProjectPanel(project);
//		this.add(projectPanel);
	}
	
	private void fillInProjectPanel(Project project) {
		this.removeAll();
		this.repaint();
		
		JLabel nameLabel = new JLabel();
		nameLabel.setText("Details for " + project.getName());
		Font f=nameLabel.getFont();
		nameLabel.setFont(f.deriveFont(f.getStyle()^Font.BOLD));
		this.add(nameLabel);
		
		JLabel descriptionLabel = new JLabel();
		descriptionLabel.setText("Description: "+ project.getDescription());
		this.add(descriptionLabel);
		
		JLabel numofusersLabel = new JLabel("Number of Users: " + project.getUsers().size());
		this.add(numofusersLabel);
		
		JLabel createdLabel = new JLabel("Date of Creation: " + project.getCreatedAt().getTime());
		this.add(createdLabel);
		
		this.revalidate();
	}

	private void fillInTaskBugPanel(TaskBug taskBug) {
		this.removeAll();
		this.repaint();
		
		JLabel titleLabel = new JLabel("Details for " + taskBug.getTitle());
		this.add(titleLabel);
		
		JLabel statusLabel = new JLabel("The status is : " + taskBug.getStatus());
		this.add(statusLabel);
		
		JLabel priorityLabel = new JLabel("The priority is : " + taskBug.getPriority());
		this.add(priorityLabel);
		
		JLabel dueDateLabel = new JLabel("The due date is : " + taskBug.getDueDate());
		this.add(dueDateLabel);
		
		JLabel estDateLabel = new JLabel("The estimated date is : " + taskBug.getEstimatedDate());
		this.add(estDateLabel);
		
		JLabel percentCompLabel = new JLabel("Percentage completed is : " + taskBug.getPercentageCompleted());
		this.add(percentCompLabel);
		
		JLabel createdAtLabel = new JLabel("Created at : " + taskBug.getCreatedAt());
		this.add(createdAtLabel);
		
		JLabel updatedAtLabel = new JLabel("Updated at : " + taskBug.getUpdatedAt());
		this.add(updatedAtLabel);
		
		this.revalidate();
	}

	public void showTaskBug(TaskBug taskBug) {
		//projectPanel.setVisible(false);

		fillInTaskBugPanel(taskBug);
		//taskBugPanel.setVisible(true);
	}

	public void showProject() {
		//projectPanel.setVisible(true);
		//taskBugPanel.setVisible(false);
		fillInProjectPanel(project);
	}
}

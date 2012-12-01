package edu.cs5774.project5;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.text.*;

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
		Font newLabelFont=new Font(descriptionLabel.getFont().getName(),Font.BOLD,descriptionLabel.getFont().getSize());
		JLabel word = new JLabel("Description: ");
		word.setFont(newLabelFont);
		descriptionLabel.setText(project.getDescription());
		this.add(word);
		this.add(descriptionLabel);
		
		JLabel numofusersLabel = new JLabel();
		numofusersLabel.setText(Integer.toString(project.getUsers().size()));
		word.setText("Number of Users: ");
		word.setFont(newLabelFont);
		this.add(word);
		this.add(numofusersLabel);
		
		JLabel createdLabel = new JLabel(project.getCreatedAt().getTime().toString());	
		word.setText("Date of Creation: ");
		word.setFont(newLabelFont);
		this.add(word);
		this.add(createdLabel);
		
		this.revalidate();
	}

	private void fillInTaskBugPanel(TaskBug taskBug) {
		this.removeAll();
		this.repaint();
		
		JLabel titleLabel = new JLabel("Details for " + taskBug.getTitle());
		Font f1=titleLabel.getFont();
		titleLabel.setFont(f1.deriveFont(f1.getStyle()^Font.BOLD));
		this.add(titleLabel);
		
		JLabel statusLabel = new JLabel(taskBug.getStatus().toString());
		Font newLabelFont1=new Font(statusLabel.getFont().getName(),Font.BOLD,statusLabel.getFont().getSize());
		JLabel word = new JLabel("The status is : ");
		word.setFont(newLabelFont1);
		this.add(word);
		this.add(statusLabel);
		
		JLabel priorityLabel = new JLabel(taskBug.getPriority().toString());
		word = new JLabel("The priority is : ");
		word.setFont(newLabelFont1);
		this.add(word);
		this.add(priorityLabel);
		
		JLabel dueDateLabel = new JLabel(taskBug.getDueDate().toString());
		word = new JLabel("The due date is : ");
		word.setFont(newLabelFont1);
		this.add(word);
		this.add(dueDateLabel);
		
		JLabel estDateLabel = new JLabel(taskBug.getEstimatedDate().getTime().toString());
		word = new JLabel("The estimated date is : ");
		word.setFont(newLabelFont1);
		this.add(word);
		this.add(estDateLabel);
		
		JLabel percentCompLabel = new JLabel(Double.toString(taskBug.getPercentageCompleted()));
		word = new JLabel("Percentage completed is : ");
		word.setFont(newLabelFont1);
		this.add(word);
		this.add(percentCompLabel);
		
		JLabel createdAtLabel = new JLabel(taskBug.getCreatedAt().getTime().toString());
		word = new JLabel("Created at : ");
		word.setFont(newLabelFont1);
		this.add(word);
		this.add(createdAtLabel);
		
		JLabel updatedAtLabel = new JLabel(taskBug.getUpdatedAt().getTime().toString());
		word = new JLabel("Updated at : ");
		word.setFont(newLabelFont1);
		this.add(word);
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

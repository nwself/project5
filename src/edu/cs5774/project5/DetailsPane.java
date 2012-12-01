package edu.cs5774.project5;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class DetailsPane extends JPanel {
	
	private static final long serialVersionUID = 3727883159676314045L;

	private Project project;
	
	public DetailsPane(Project project) {
		super(new GridLayout(0, 1));
		this.project = project;
		
		fillInProjectPanel(project);
	}
	
	private void fillInProjectPanel(Project project) {
		this.removeAll();
		this.repaint();
		
		JLabel nameLabel = new JLabel();
		nameLabel.setText("Details for " + project.getName());
		Font f=nameLabel.getFont();
		nameLabel.setFont(f.deriveFont(f.getStyle()^Font.BOLD, 18.0f));
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(nameLabel);
		
		
		
		JLabel descriptionLabel = new JLabel(project.getDescription());
		Font newLabelFont=new Font(descriptionLabel.getFont().getName(),Font.BOLD,descriptionLabel.getFont().getSize());
		JLabel word = new JLabel();
		JPanel both=new JPanel();
		word.setText("Description: ");
		word.setFont(newLabelFont);
		both.add(word);
		both.add(descriptionLabel);
		this.add(both);
		
		JLabel numofusersLabel = new JLabel();
		numofusersLabel.setText(Integer.toString(project.getUsers().size()));
		JLabel word1 = new JLabel();
		word1.setText("Number of Users: ");
		word1.setFont(newLabelFont);
		JPanel both1=new JPanel();
		both1.add(word1);
		both1.add(numofusersLabel);
		this.add(both1);
		
		JLabel createdLabel = new JLabel(project.getCreatedAt().getTime().toString());	
		JLabel word3 = new JLabel();
		word3.setText("Date of Creation: ");
		word3.setFont(newLabelFont);
		JPanel both2=new JPanel();
		both2.add(word3);
		both2.add(createdLabel);
		this.add(both2);
		
		this.revalidate();
	}

	private void fillInTaskBugPanel(TaskBug taskBug) {
		this.removeAll();
		this.repaint();
		
		JLabel titleLabel = new JLabel("Details for " + taskBug.getTitle());
		Font f1=titleLabel.getFont();
		titleLabel.setFont(f1.deriveFont(f1.getStyle()^Font.BOLD, 18.0f));
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(titleLabel);
		
		JLabel statusLabel = new JLabel("Complete");//taskBug.getStatus().toString());
		Font newLabelFont1=new Font(statusLabel.getFont().getName(),Font.BOLD,statusLabel.getFont().getSize());
		JLabel word = new JLabel("The status is : ");
		word.setFont(newLabelFont1);
		this.add(word);
		this.add(statusLabel);
		
		JLabel priorityLabel = new JLabel("High");//taskBug.getPriority().toString());
		word = new JLabel("The priority is : ");
		word.setFont(newLabelFont1);
		this.add(word);
		this.add(priorityLabel);
		
		JLabel dueDateLabel = new JLabel(taskBug.getDueDate().getTime().toString());
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

package edu.cs5774.project5;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DetailsPane extends JPanel implements TaskBugSelectionListener {
	
	private static final long serialVersionUID = 3727883159676314045L;

	private Project project;

	public DetailsPane(Project project) {
		super(new GridLayout(0, 1));
		this.project = project;
		
		project.addTaskBugSelectionListener(this);
		fillInProjectPanel(project);
	}
	
	private void fillInProjectPanel(final Project project) {
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
		
		
		List<TaskBug> taskList = new ArrayList<TaskBug>();
		taskList = project.getTask();
		Iterator<TaskBug> taskListItr = taskList.iterator();
		while(taskListItr.hasNext())
		{
			JLabel taskLabel = new JLabel();	
			JLabel word4 = new JLabel();
			JPanel both3=new JPanel();
			JButton detailsButton = new JButton("View Details");

			final TaskBug element = taskListItr.next();
			taskLabel.setText(element.getTitle());
			word4.setText("Task: ");
			detailsButton.setActionCommand(element.getTitle());
			detailsButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					project.setSelectedTaskBug(element);
				}
			});
			word4.setFont(newLabelFont);
			both3.add(word4);
			both3.add(taskLabel);
			both3.add(detailsButton);
			this.add(both3);
		}
		
		
		List<TaskBug> bugList = new ArrayList<TaskBug>();
		bugList = project.getBug();
		Iterator<TaskBug> bugListItr = bugList.iterator();
		while(bugListItr.hasNext())
		{
			JLabel bugLabel = new JLabel();	
			JLabel word5 = new JLabel();
			JPanel both4=new JPanel();
			JButton detailsButton1 = new JButton("View Details");

			final TaskBug element1 = bugListItr.next();
			bugLabel.setText(element1.getTitle());
			word5.setText("Bug: ");
			detailsButton1.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					project.setSelectedTaskBug(element1);
				}
			});
			word5.setFont(newLabelFont);
			both4.add(word5);
			both4.add(bugLabel);
			both4.add(detailsButton1);
			this.add(both4);
		}
		
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


	public void showProject() {
		fillInProjectPanel(project);
	}

	public void setProject(Project project) {
		this.project = project;
		fillInProjectPanel(project);
	}

	@Override
	public void taskBugSelected(TaskBug taskBug) {
		if (taskBug != null) {
			fillInTaskBugPanel(taskBug);
		}
	}
}

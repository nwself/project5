package edu.cs5774.project5;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel that lists details of a Project or TaskBug when it receives a TaskBug selected event or a
 * call to display its Project.  When the Project is displayed, buttons are available that fire a
 * TaskBug selected event.
 *
 */
public class DetailsPane extends JPanel implements TaskBugSelectionListener {
	
	private static final long serialVersionUID = 3727883159676314045L;

	private Project project;
	
	private Font descriptionFont;

	public DetailsPane(Project project) {
		super(new GridLayout(0, 1));
		this.project = project;

		JLabel defaultLabel = new JLabel();
		descriptionFont = new Font(defaultLabel.getFont().getName(), Font.BOLD, defaultLabel.getFont().getSize());

		project.addTaskBugSelectionListener(this);
		fillInProjectPanel(project);
	}
	
	private JPanel createLabelPanel(String label, String value) {
		JPanel panel = new JPanel();
		
		JLabel descriptiveLabel = new JLabel(label);
		descriptiveLabel.setFont(descriptionFont);
		panel.add(descriptiveLabel);
		
		panel.add(new JLabel(value));
		
		return panel;
	}

	private JPanel createLabelButtonPanel(String label, String value, String buttonLabel, ActionListener listener) {
		JPanel labelPanel = createLabelPanel(label, value);
		
		JButton button = new JButton(buttonLabel);
		button.addActionListener(listener);
		labelPanel.add(button);
		
		return labelPanel;
	}

	private void fillInProjectPanel(final Project project) {
		this.removeAll();
		this.repaint();
		
		JLabel nameLabel = new JLabel("Details for " + project.getName());
		Font nameLabelFont = nameLabel.getFont();
		nameLabel.setFont(nameLabelFont.deriveFont(nameLabelFont.getStyle()^Font.BOLD, 18.0f));
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(nameLabel);
		

		this.add(createLabelPanel("Description: ", project.getDescription()));
		this.add(createLabelPanel("Number of Users: ", Integer.toString(project.getUsersCount())));
		this.add(createLabelPanel("Date of Creation: ", project.getCreatedAt().getTime().toString()));

		List<TaskBug> taskBugs = new LinkedList<TaskBug>();
		taskBugs.addAll(project.getTask());
		taskBugs.addAll(project.getBug());
		for (final TaskBug taskBug : taskBugs) {
			String label = taskBug.isTask() ? "Task: " : "Bug: ";
			this.add(createLabelButtonPanel(label, taskBug.getTitle(), "View Details", new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					project.setSelectedTaskBug(taskBug);
				}
			}));
		}
		
		this.revalidate();
	}

	private void fillInTaskBugPanel(TaskBug taskBug) {
		this.removeAll();
		this.repaint();
		
		JLabel titleLabel = new JLabel("Details for " + taskBug.getTitle());
		
		Font titleFont = titleLabel.getFont();
		titleLabel.setFont(titleFont.deriveFont(titleFont.getStyle()^Font.BOLD, 18.0f));
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(titleLabel);
		
		this.add(createLabelPanel("The status is : ", taskBug.getStatus().toString()));
		this.add(createLabelPanel("The priority is : ", taskBug.getPriority().toString()));
		this.add(createLabelPanel("The due date is : ", taskBug.getDueDate().getTime().toString()));
		this.add(createLabelPanel("The estimated date is : ", taskBug.getEstimatedDate().getTime().toString()));
		this.add(createLabelPanel("Percentage completed is : ", Double.toString(taskBug.getPercentageCompleted())));
		this.add(createLabelPanel("Created at : ", taskBug.getCreatedAt().getTime().toString()));
		this.add(createLabelPanel("Updated at : ", taskBug.getUpdatedAt().getTime().toString()));
		
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

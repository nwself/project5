package edu.cs5774.project5;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProjectDetailsPane extends JPanel {

	Project project;
	
	public ProjectDetailsPane(Project project) {
		super(new GridLayout(0, 1));
		this.project = project;
		
		this.add(new JLabel("Details for " + project.getName()));
	}

}

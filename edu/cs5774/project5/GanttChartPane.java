package edu.cs5774.project5;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GanttChartPane extends JPanel {

	public GanttChartPane(Project project) {
		super(new GridLayout(0, 1));
		
		this.add(new JLabel("Gantt Chart for " + project.getName()));
	}

}

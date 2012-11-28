package edu.cs5774.project5;

import javax.swing.JSplitPane;

public class DocumentPane extends JSplitPane {

	GanttChartPane ganttPane;
	ProjectDetailsPane detailsPane;
	
	public DocumentPane(Project project) {
		ganttPane = new GanttChartPane(project);
		detailsPane = new ProjectDetailsPane(project);
		
		this.setLeftComponent(ganttPane);
		this.setRightComponent(detailsPane);
		
	}

}

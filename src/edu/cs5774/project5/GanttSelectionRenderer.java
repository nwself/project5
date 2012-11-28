package edu.cs5774.project5;

import java.awt.Color;
import java.awt.Paint;

import org.jfree.chart.renderer.category.GanttRenderer;

public class GanttSelectionRenderer extends GanttRenderer {
	
	int selectedColumn = -1;
	
	public GanttSelectionRenderer() {
	}
	
	@Override
	public Paint getItemPaint(int row, int column) {
		if (column == selectedColumn) {
			return Color.blue;
		} else {
			return super.getItemPaint(row, column);
		}
	}

	public void setSelectedColumn(int columnIndex) {
		selectedColumn = columnIndex;
	}
}

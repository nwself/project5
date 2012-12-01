package edu.cs5774.project5;

import java.awt.Color;
import java.awt.Paint;

import org.jfree.chart.renderer.category.GanttRenderer;

public class GanttSelectionRenderer extends GanttRenderer {
	
	private static final long serialVersionUID = -2472039507871250135L;
	private int selectedColumn = -1;
	private static final Paint SELECTED_PAINT = Color.yellow;
	
	public GanttSelectionRenderer() {
	}
	
	@Override
	public Paint getItemPaint(int row, int column) {
		if (column == selectedColumn) {
			return SELECTED_PAINT;
		} else {
			return super.getItemPaint(row, column);
		}
	}

	public void setSelectedColumn(int columnIndex) {
		selectedColumn = columnIndex;
	}

	public int getSelectedColumn() {
		return selectedColumn;
	}
}

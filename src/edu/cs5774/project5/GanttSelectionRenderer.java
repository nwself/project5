package edu.cs5774.project5;

import java.awt.Color;
import java.awt.Paint;

import org.jfree.chart.renderer.category.GanttRenderer;

public class GanttSelectionRenderer extends GanttRenderer {
	
	private int selectedColumn = -1;
	private static final Paint SELECTED_PAINT = Color.blue;
	
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

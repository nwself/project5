package edu.cs5774.project5;

/**
 * Interface for listeners of deletion requested events. This is used
 * to propagate Delete or Backspace key presses up from GanttChartPane to
 * its owning DocumentPane. 
 *
 */
public interface DeletionRequestedListener {

	void deletionRequested();

}

package edu.cs5774.project5;

/**
 * Interface for passing selected TaskBug events. This is fired by
 * the Project class when setSelectedTaskBug is called.
 *
 */
public interface TaskBugSelectionListener {

	void taskBugSelected(TaskBug taskBug);

}

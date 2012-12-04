package edu.cs5774.project5;

/**
 * Interface describing listeners for whether certain actions should be enabled in
 * the JFrame's menu bar. Actions such as delete and undo/redo are owned by DocumentPane
 * but the menu bar is owned by JVizinator which contains a JTabbedPane of DocumentPanes
 * so this interface allows DocumentPane to alert JVizinator of action availability.
 *
 */
public interface ActionEnabledListener {

	void undoRedoEnabled(boolean undoEnabled, boolean redoEnabled);
	void deleteEnabled(boolean deleteEnabled);

}

package edu.cs5774.project5;

public interface ActionEnabledListener {

	void undoRedoEnabled(boolean undoEnabled, boolean redoEnabled);
	void deleteEnabled(boolean deleteEnabled);

}

package edu.cs5774.project5;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

public class JVizinator extends JFrame implements ActionEnabledListener {
	
	private JTabbedPane tabbedPane;
	
	private JMenuItem undoItem;
	private JMenuItem redoItem;
	private JMenuItem deleteItem;
	
	public JVizinator() {
		super("JVizinator");
		
        this.setJMenuBar(createJMenuBar());
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel jviz = new JPanel(new GridLayout(0, 1));
        tabbedPane = new JTabbedPane();
        jviz.add(tabbedPane);
        this.setContentPane(jviz);
 
        //Display the window.
        this.pack();
        this.setVisible(true);
	}

	private void updateFromRSS() {
		// TODO: Krunal get RSS and parse it
		// in the meantime, build a test Project and display it
		
		Project project = new Project("Test Project", 
				"A stub project while we wait for parsing to be implemented", 
				Calendar.getInstance(),
				Calendar.getInstance());
		
		Calendar dueDate = Calendar.getInstance();
		dueDate.set(Calendar.DAY_OF_YEAR, dueDate.get(Calendar.DAY_OF_YEAR) + 5);

		Calendar secondDue = Calendar.getInstance();
		secondDue.set(Calendar.DAY_OF_YEAR, secondDue.get(Calendar.DAY_OF_YEAR) + 10);
		
		TaskBug tb1 = new TaskBug("Task 1", TaskBug.Status.COMPLETE, TaskBug.Priority.HIGH, 
				dueDate, Calendar.getInstance(), 100.0, Calendar.getInstance(),
				Calendar.getInstance(), true);
		project.addTaskBug(tb1);
		
		project.addTaskBug(new TaskBug("Task 2", TaskBug.Status.IN_PROGRESS, TaskBug.Priority.LOW, 
				secondDue, Calendar.getInstance(), 50.0, dueDate,
				Calendar.getInstance(), true));
		
		project.addTaskBug(new TaskBug("Bug 1", TaskBug.Status.OPEN, TaskBug.Priority.MEDIUM,
				secondDue, Calendar.getInstance(), 33.3, dueDate,
				Calendar.getInstance(), false));
		
		project.addUser(new User("userfoo", "userfoo@bar.baz", Calendar.getInstance()));
		
		DocumentPane docPane = new DocumentPane(project);
		docPane.addUndoRedoEnabledListener(this);
		tabbedPane.addTab(project.getName(), docPane);
	}
	
	private JMenuBar createJMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem rssItem = new JMenuItem("Update from RSS", KeyEvent.VK_U);
		rssItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateFromRSS();
			}
		});
		fileMenu.add(rssItem);
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        
        JMenu editMenu = new JMenu("Edit");
		editMenu.setMnemonic(KeyEvent.VK_E);
        
		undoItem = new JMenuItem("Undo");
        undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		undoItem.setMnemonic(KeyEvent.VK_U);
		undoItem.setEnabled(false);
		undoItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				undoDeletion();
			}
		});
        
        redoItem = new JMenuItem("Redo");
        redoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        redoItem.setMnemonic(KeyEvent.VK_R);
        redoItem.setEnabled(false);
        redoItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				redoDeletion();
			}
		});
        
        editMenu.add(undoItem);
        editMenu.add(redoItem);
		
		JMenu elementMenu = new JMenu("Element");
		elementMenu.setMnemonic(KeyEvent.VK_L);
		
		deleteItem = new JMenuItem("Delete Element", KeyEvent.VK_D);
		deleteItem.setEnabled(false);
		deleteItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteSelectedItem();
			}
		});
		elementMenu.add(deleteItem);
		
		menuBar.add(fileMenu);
        menuBar.add(editMenu);
		menuBar.add(elementMenu);
		
		return menuBar;
	}
	
	protected void redoDeletion() {
		Component selectedComponent = tabbedPane.getSelectedComponent();
		if (selectedComponent instanceof DocumentPane) {
			DocumentPane docPane = (DocumentPane) selectedComponent;
			docPane.redoDeletion();
		}
	}

	protected void undoDeletion() {
		Component selectedComponent = tabbedPane.getSelectedComponent();
		if (selectedComponent instanceof DocumentPane) {
			DocumentPane docPane = (DocumentPane) selectedComponent;
			docPane.undoDeletion();
		}
	}

	protected void deleteSelectedItem() {
		Component selectedComponent = tabbedPane.getSelectedComponent();
		if (selectedComponent instanceof DocumentPane) {
			DocumentPane docPane = (DocumentPane) selectedComponent;
			docPane.deletionRequested();
		}
	}

	protected static void createAndShowGUI() {
		new JVizinator();
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}

	@Override
	public void undoRedoEnabled(boolean undoEnabled, boolean redoEnabled) {
		undoItem.setEnabled(undoEnabled);
		redoItem.setEnabled(redoEnabled);
	}

	@Override
	public void deleteEnabled(boolean deleteEnabled) {
		deleteItem.setEnabled(deleteEnabled);
	}
}

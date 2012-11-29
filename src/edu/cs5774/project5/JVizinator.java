package edu.cs5774.project5;

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

public class JVizinator extends JFrame {
	
	private JTabbedPane tabbedPane;
	
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
		tabbedPane.addTab(project.getName(), docPane);
	}
	
	private JMenuBar createJMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		// TODO: Noha fill out the rest of the menu items
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
        JMenuItem undoItem = new JMenuItem("Undo");
        JMenuItem redoItem = new JMenuItem("Redo");
        editMenu.add(undoItem);
        editMenu.add(redoItem);
		
		JMenu elementMenu = new JMenu("Element");
		elementMenu.setMnemonic(KeyEvent.VK_E);
		
		JMenuItem deleteItem = new JMenuItem("Delete", KeyEvent.VK_D);
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
	
	protected void deleteSelectedItem() {
		DocumentPane docPane = null;
		
		// TODO Manpreet figure out how to find the currently visible tab
		// docPane = currently visible tab
		docPane.deletionRequested();
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
}

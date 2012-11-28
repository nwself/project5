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
		
		DocumentPane docPane = new DocumentPane(project);
		tabbedPane.addTab(project.getName(), docPane);
	}
	
	private JMenuBar createJMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		// TODO: Noha fill out the rest of the menu items
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);
		
		JMenuItem rssItem = new JMenuItem("Update from RSS", KeyEvent.VK_U);
		rssItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateFromRSS();
			}
		});
		fileMenu.add(rssItem);
		
		menuBar.add(fileMenu);
		
		return menuBar;
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

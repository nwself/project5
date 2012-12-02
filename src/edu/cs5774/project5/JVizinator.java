package edu.cs5774.project5;

import java.awt.AWTKeyStroke;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.filechooser.FileFilter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.protocol.HttpContext;


public class JVizinator extends JFrame implements ActionEnabledListener {
	
	private static final long serialVersionUID = -6822773346882930866L;

	private JTabbedPane tabbedPane;
	
	private JMenuItem saveItem;
	private JMenuItem closeItem;
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
        setupTabTraversalKeys(tabbedPane);
        jviz.add(tabbedPane);
        this.setContentPane(jviz);
        
        // Start the program with the RSS feed loaded
        updateFromRSS();
 
        //Display the window.
        this.pack();
        this.setVisible(true);
	}

	private HttpResponse fetchFeed() {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.setRedirectStrategy(new DefaultRedirectStrategy() {                
	        public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context)  {
	            boolean isRedirect=false;
	            try {
	                isRedirect = super.isRedirected(request, response, context);
	            } catch (ProtocolException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            if (!isRedirect) {
	                int responseCode = response.getStatusLine().getStatusCode();
	                if (responseCode == 301 || responseCode == 302) {
	                    return true;
	                }
	            }
	            return isRedirect;
	        }
	    });

		HttpResponse response = null;
		HttpGet httpGet = new HttpGet("http://localhost:3000/feed");
		try {
			response = httpClient.execute(httpGet);
		} catch (ClientProtocolException e) {
			response = null;
			e.printStackTrace();
		} catch (IOException e) {
			response = null;
			e.printStackTrace();
		}
		return response;
	}
	
	private void openFilesFromStream(InputStream inputStream) throws JAXBException {
		JAXBContext jaxbContext;
        jaxbContext = JAXBContext.newInstance(Rss.class);
        
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Rss rssObject = (Rss) jaxbUnmarshaller.unmarshal(inputStream);
        
        for (Project project : rssObject.getChannel().getItem()) {
        	createNewTab(project);
        }
	}
	
	private void updateFromRSS() {
		HttpResponse response = fetchFeed();
		
		if (response != null) {
			try {
				openFilesFromStream(response.getEntity().getContent());
			} catch (IllegalStateException e) {
				e.printStackTrace();
				showErrorMessage("IllegalStateException thrown");
			} catch (JAXBException e) {
				e.printStackTrace();
				showErrorMessage("Could not parse RSS feed");
			} catch (IOException e) {
				e.printStackTrace();
				showErrorMessage("Problem reading from RSS feed");
			}
		} else {
			showErrorMessage("RSS feed could not be fetched");
		}
	}
	
	private void createNewTab(Project project) {
		String tabTitle = project.getName();
		int tabIndex = tabbedPane.indexOfTab(tabTitle); 
		
		if (tabIndex == -1) {
			DocumentPane docPane = new DocumentPane(project);
			docPane.addUndoRedoEnabledListener(this);
			
			tabbedPane.addTab(tabTitle, docPane);
			
			int newTabIndex = tabbedPane.indexOfTab(tabTitle);
		    tabbedPane.setTabComponentAt(newTabIndex, new ButtonTabComponent(tabbedPane));
		} else {
			tabbedPane.setSelectedIndex(tabIndex);
		}
		
		updateMenuItemsEnabled();
    }
	
	private JMenuBar createJMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		/* FILE MENU */
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		// Update from RSS
		JMenuItem rssItem = new JMenuItem("Update from RSS", KeyEvent.VK_U);
		rssItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateFromRSS();
			}
		});
		fileMenu.add(rssItem);
		
		// Open file
        JMenuItem openItem = new JMenuItem("Open", KeyEvent.VK_O);
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        openItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				openFile();
			}
		});
        fileMenu.add(openItem);
        
        // Save file
        saveItem = new JMenuItem("Save", KeyEvent.VK_S);
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveItem.setEnabled(false);
        saveItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveFile();
			}
		});
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
         
        //Save as 
        JMenuItem saveasItem = new JMenuItem("Save As", KeyEvent.VK_A);
        
        // Close current tab
        closeItem = new JMenuItem("Close Tab", KeyEvent.VK_C);
        closeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        closeItem.setEnabled(false);
        closeItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				closeFile();
			}
		});
        fileMenu.add(closeItem);
        fileMenu.addSeparator();
        
        // Exit program
        JMenuItem exitItem = new JMenuItem("Exit", KeyEvent.VK_E);
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        exitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
        fileMenu.add(exitItem);
		menuBar.add(fileMenu);
		
        /* EDIT MENU */
        JMenu editMenu = new JMenu("Edit");
		editMenu.setMnemonic(KeyEvent.VK_E);

		// Undo
		undoItem = new JMenuItem("Undo", KeyEvent.VK_U);
        undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		undoItem.setEnabled(false);
		undoItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				undoDeletion();
			}
		});
        editMenu.add(undoItem);
        
		// Redo
        redoItem = new JMenuItem("Redo", KeyEvent.VK_R);
        redoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        redoItem.setEnabled(false);
        redoItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				redoDeletion();
			}
		});
        editMenu.add(redoItem);
        menuBar.add(editMenu);

        /* ELEMENT MENU */
		JMenu elementMenu = new JMenu("Element");
		elementMenu.setMnemonic(KeyEvent.VK_L);
		
		// Delete element
		deleteItem = new JMenuItem("Delete Element", KeyEvent.VK_D);
		deleteItem.setEnabled(false);
		deleteItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteSelectedItem();
			}
		});
		elementMenu.add(deleteItem);
		menuBar.add(elementMenu);
		
		return menuBar;
	}
	public class XMLFileFilter extends FileFilter
	{
	     public boolean accept(File f)
	    {
	    	 System.out.println(f.getName());
	    	  if (f.getName().endsWith(".xml")||f.isDirectory())
	    	 {
	    		 System.out.println("xml");
	    		 return true;
	    	 }
	    	 else return false;
	    }
	 
	    public String getDescription()
	    {
	        return "XML files (*.xml)";
	    }
	}
	protected void saveFile() {
		JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setFileFilter(new XMLFileFilter());
		String fileName="";
		int returnVal = fileChooser.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if(fileChooser.getSelectedFile()!=null)
			{		fileName = fileChooser.getSelectedFile().getName();
	System.out.println(fileName);
		
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Rss.class);
		
			Rss rssObject = new Rss();
			Channel channel = new Channel();
			
			LinkedList<Project> projectList = new LinkedList<Project>();
			Component selectedComponent = tabbedPane.getSelectedComponent();
			DocumentPane docPane = (DocumentPane) selectedComponent;
			projectList.add(docPane.getProject());

			channel.setItem(projectList);
			
			rssObject.setChannel(channel);
			
			File outputFile = new File(fileName);
			
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.marshal(rssObject, outputFile);

		} catch (JAXBException e) {
			e.printStackTrace();
			showErrorMessage("Could not save file to " + fileName + " JAXB marshalling failed");
		}
			}
		}
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
	protected void openFile() {
		JFileChooser fileChooser = new JFileChooser(".");
		
		int returnVal = fileChooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String fileName = fileChooser.getSelectedFile().getName();
			try {
				openFilesFromStream(new FileInputStream(new File(fileName)));
			} catch (FileNotFoundException noFileException) {
				noFileException.printStackTrace();
				showErrorMessage("No such file: " + fileName);
			} catch (JAXBException e) {
				e.printStackTrace();
				showErrorMessage("Could not parse file: " + fileName);
			}
		}
	}
	
	private void showErrorMessage(String errorMessage) {
		// TODO show an error dialog with errorMessage in it
		
	}

	protected void closeFile() {
		Component tabComponent = tabbedPane.getTabComponentAt(tabbedPane.getSelectedIndex());
		
		if (tabComponent instanceof ButtonTabComponent) {
			ButtonTabComponent buttonTab = (ButtonTabComponent) tabComponent;
			buttonTab.closeTab();
		}
		
		updateMenuItemsEnabled();
	}

	private void updateMenuItemsEnabled() {
		int tabCount = tabbedPane.getTabCount();
		
		saveItem.setEnabled(tabCount != 0);
		closeItem.setEnabled(tabCount != 0);
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
	
	private static void setupTabTraversalKeys(JTabbedPane tabbedPane)
	{
		KeyStroke ctrlTab = KeyStroke.getKeyStroke("ctrl TAB");
		KeyStroke ctrlShiftTab = KeyStroke.getKeyStroke("ctrl shift TAB");
		
		// Remove ctrl-tab from normal focus traversal
		Set<AWTKeyStroke> forwardKeys = new HashSet<AWTKeyStroke>(tabbedPane.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
		forwardKeys.remove(ctrlTab);
		tabbedPane.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardKeys);
		 
		// Remove ctrl-shift-tab from normal focus traversal
		Set<AWTKeyStroke> backwardKeys = new HashSet<AWTKeyStroke>(tabbedPane.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
		backwardKeys.remove(ctrlShiftTab);
		tabbedPane.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backwardKeys);
		 
		// Add keys to the tab's input map
		InputMap inputMap = tabbedPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		inputMap.put(ctrlTab, "navigateNext");
		inputMap.put(ctrlShiftTab, "navigatePrevious");
	}
}

package edu.cs5774.project5;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
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
	
	private void updateFromRSS() throws ClientProtocolException, IOException {
		HttpResponse response = fetchFeed();
		
		if (response != null) {
			try {
				openFilesFromStream(response.getEntity().getContent());
			} catch (Exception e) {
				e.printStackTrace();
				showErrorMessage("Parsing of RSS feed failed");
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
    }
	
	private JMenuBar createJMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem rssItem = new JMenuItem("Update from RSS", KeyEvent.VK_U);
		rssItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				updateFromRSS();
				} catch (ClientProtocolException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		fileMenu.add(rssItem);
        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				openFile();
			}
		});
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveFile();
			}
		});
        
        JMenuItem closeItem = new JMenuItem("Close current");
        closeItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				closeFile();
			}
		});
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(closeItem);
        fileMenu.add(exitItem);
        
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
	
	protected void saveFile() {
		String fileName = "text.out"; // TODO: use JFileChooser to get filename
		
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Rss.class);
		
			Rss rss = new Rss();
			Channel channel = new Channel();
			
			LinkedList<Project> projectList = new LinkedList<Project>();
			Component selectedComponent = tabbedPane.getSelectedComponent();
			DocumentPane docPane = (DocumentPane) selectedComponent;
			projectList.add(docPane.getProject());

			channel.setItem(projectList);
			
			rss.setChannel(channel);
			
			File outputFile = new File(fileName);
			
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.marshal(rss, outputFile);

		} catch (JAXBException e) {
			e.printStackTrace();
			showErrorMessage("Could not save file to " + fileName + " JAXB marshalling failed");
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

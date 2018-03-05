package gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


import javax.swing.JFrame;

import javax.swing.JPanel;

import model.UrbanParksSystem;
import model.User;


public final class UrbanParksGUI extends JFrame implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	
	/** Used in order to access user's screen size. */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();
    
    /** User's computer screensize to be used for setting GUI's location. */
    private static final Dimension SCREENSIZE = KIT.getScreenSize();
    
    /** User's computer screen width. */
    private static final int DEFAULT_SCREEN_WIDTH = SCREENSIZE.width;
    
    /** User's computer screen height. */
    private static final int DEFAULT_SCREEN_HEIGHT = SCREENSIZE.height;
    
    public static final String LOG_IN_PANEL = "LogInPanel";
    public static final String MAIN_MENU_PANEL = "MainMenuPanel";
    
    private UrbanParksSystem mySystem;
	private JPanel myMasterPanel;
	private User myCurrentUserType;
	private String myCurrentUser;
	
	
	public UrbanParksGUI() {
		mySystem = new UrbanParksSystem();
		myMasterPanel = new JPanel(new CardLayout());
		myCurrentUserType = null;
		myCurrentUser = null;
		
	}
	
	/**
	 * Method that is called by the driver class starts up the whole program.
	 */
	public void start() {
		
		setupInitialPanels();
		maximizeFrame();
		setMinimumWindowSize();
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	
	private void setupInitialPanels() {
		LogInPanel logIn = new LogInPanel(mySystem); 
		logIn.addPropertyChangeListener(this);
		myMasterPanel.add(logIn, LOG_IN_PANEL);
		
		
		
		add(myMasterPanel);
		
	}
	
	
	private void maximizeFrame() {
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		pack();
	}
	
	
	private void setMinimumWindowSize() {
		setSize(getPreferredSize());
		setMinimumSize(getPreferredSize());
	}
	
	
	/**
	 * This method allows switching between panels in the master panel.
	 * @param theNewPanelName The name of the panel to switch to.
	 */
	private void switchPanels(String theNewPanelName) {
		
		CardLayout cardLayout = (CardLayout) myMasterPanel.getLayout();				
		cardLayout.show(myMasterPanel, theNewPanelName);
		
	}
	
	public int createMainMenu() {
		
		if (myCurrentUserType == null) {
			return 1;
		}
		
		MainMenu mainMenu = new MainMenu(mySystem, myCurrentUserType, myCurrentUser);
		mainMenu.addPropertyChangeListener(this);
		myMasterPanel.add(mainMenu, MAIN_MENU_PANEL);
		
		switchPanels(MAIN_MENU_PANEL);
		
		return 0;
	}
	
	public void clearGUI() {
		myMasterPanel.removeAll();
		myCurrentUserType = null;
		myCurrentUser = null;
		start();

	}
	
	
	
	
	public void setUser(User theUserType, String theUserName) {
		myCurrentUserType = theUserType;
		myCurrentUser = theUserName;
	
	}
	

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		String propertyName = (String) evt.getPropertyName();
		if (propertyName.equals("clearGUI")) {
			clearGUI();
		} else if (propertyName.equals("logIn")) {
			User theUserType = (User) evt.getNewValue();
			String theUserName = theUserType.getUsername();
			setUser(theUserType, theUserName);
			createMainMenu();
		}
		
	}
	
	/* For future implementations if needed
	private void setScreenToCenter() {
        pack();
        setLocation(DEFAULT_SCREEN_WIDTH / 2 - getWidth() / 2, 
                            DEFAULT_SCREEN_HEIGHT / 2 - getHeight() / 2);
		
	}
	*/
	
	
	
	

}

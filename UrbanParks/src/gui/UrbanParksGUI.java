package gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.ParkManager;
import model.UrbanParksSystem;
import model.User;
import model.Volunteer;

public final class UrbanParksGUI extends JFrame {

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
	private Stack<String> myStackOfPanels;
	
	public UrbanParksGUI() {
		mySystem = new UrbanParksSystem();
		myMasterPanel = new JPanel(new CardLayout());
		myCurrentUserType = null;
		myCurrentUser = null;
		myStackOfPanels = new Stack<>();
		myStackOfPanels.add(LOG_IN_PANEL);
		
	}
	
	public void start() {
		
		setupInitialPanels();
		setScreenToCenter();
		setMinimumWindowSize();
		
		setVisible(true);
		
		
		// need to be recoded to listen to windowClosing method instead of this code
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void setupInitialPanels() {
		
		myMasterPanel.add(new LogInPanel(this, mySystem), LOG_IN_PANEL);
		
		
		
		add(myMasterPanel);
		setVisible(true);
		
	}
	
	public void setScreenToCenter() {
        pack();
        setLocation(DEFAULT_SCREEN_WIDTH / 2 - getWidth() / 2, 
                            DEFAULT_SCREEN_HEIGHT / 2 - getHeight() / 2);
		
	}
	
	public void setMinimumWindowSize() {
		setSize(getPreferredSize());
		setMinimumSize(getPreferredSize());
	}
	
	

	/* This method may not be needed in this class. */
	public void switchPanels(String theNewPanelName) {
		
		CardLayout cardLayout = (CardLayout) myMasterPanel.getLayout();				
		cardLayout.show(myMasterPanel, theNewPanelName);
		
	}
	
	public int createMainMenu() {
		
		if (myCurrentUserType == null) {
			return 1;
		}
		
		
		
		
		
		if (myCurrentUserType instanceof Volunteer) {
			
			// add Volunteer MainMenu Here
			

		} else if (myCurrentUserType instanceof ParkManager) {
			
			// add ParkManager Menu Here
			myMasterPanel.add(new ParkManagerMainMenu(this, mySystem,
					myCurrentUserType, myCurrentUser),
					MAIN_MENU_PANEL);
		}
		
		
		switchPanels(MAIN_MENU_PANEL);
		
		return 0;
	}
	
	
	
	
	// setters for the fields
	public void setUser(User theUserType, String theUserName) {
		myCurrentUserType = theUserType;
		myCurrentUser = theUserName;
	
	}
	
	
	/**
	 * Pops off the current panel from the stack and
	 * sets the previous panel as the new Current Panel
	 * @return 0 if action is valid; 1 if action attempts to remove LogIn Panel and this is not allowed
	 *
	 */
	public int returnToPreviousPage() {
		if (myStackOfPanels.size() > 1) {
			myStackOfPanels.pop();
			switchPanels(myStackOfPanels.peek());
			return 1;
		}
		
		return 0;
		
	}
	
	
	public void terminateProgram() {

		int terminatingValue = JOptionPane.showConfirmDialog(null,
		"Are you sure you want to exit?",
		"Exiting the Program",
		JOptionPane.YES_NO_OPTION,
		JOptionPane.QUESTION_MESSAGE);
		
		if (terminatingValue == JOptionPane.YES_OPTION) {
			myStackOfPanels.clear();
		}
	}
	
	
	
	

}

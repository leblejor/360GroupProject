package gui;

import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.User;

public final class UrbanParksGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel myCurrentPanel;
	private User myCurrentUserType;
	private String myCurrentUser;
	private Stack<JPanel> myStackOfPanels;
	
	public UrbanParksGUI() {
		myCurrentPanel = new LogInPanel();
		myCurrentUserType = null;
		myCurrentUser = null;
		myStackOfPanels = new Stack<>();
		myStackOfPanels.add(myCurrentPanel);
	}
	
	public void start() {
		
		
		add(myCurrentPanel);
		myCurrentPanel.setVisible(true);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void terminateProgram() {

		int terminatingValue = JOptionPane.showConfirmDialog(null,
		"Are you sure you want to exit?",
		"Exiting the Program",
		JOptionPane.YES_NO_OPTION,
		JOptionPane.QUESTION_MESSAGE);
		
		if (terminatingValue == JOptionPane.YES_OPTION) {
			
		}
	}
	
	public void setCurrentPanel(JPanel theNewPanel) {
		myCurrentPanel = theNewPanel;
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
			myCurrentPanel = myStackOfPanels.peek();
			return 1;
		}
		
		return 0;
		
	}
	
	
	
	
	

}

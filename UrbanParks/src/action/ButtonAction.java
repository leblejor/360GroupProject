package action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JPanel;

import gui.MainMenu;

public class ButtonAction extends AbstractAction {
	
	private final MainMenu myMainMenu;
	private final String myPanelName;
	
	public ButtonAction(MainMenu theMainMenu, String thePanelName) {
		myMainMenu = theMainMenu;
		myPanelName = thePanelName;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		myMainMenu.switchPanels(myPanelName);

	}

}

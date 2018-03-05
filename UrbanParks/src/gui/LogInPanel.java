package gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.UrbanParksSystem;
import model.User;


/**
 * This is the JPanel class that allows the user to signIn or logIn with a userName
 * 
 * @author Bryan Santos
 *
 */
public class LogInPanel extends JPanel {
	

	private static final long serialVersionUID = 1L;
	private JLabel myLogInSuccessMessage;
	
	
	private JPanel myCenterMasterPanel;
	
	/** This JPanel contains swing components such as the JLabel and JTextField;
	 * This goes inside myCentrerMasterPanel
	 */
	private JPanel myCenterComponentsPanel;
	private UrbanParksSystem mySystem;
	
	
	
	
	public LogInPanel(UrbanParksSystem theSystem) {
		
		mySystem = theSystem;
		myCenterMasterPanel = new JPanel(new GridBagLayout());
		myCenterComponentsPanel = new JPanel(new GridLayout(2,1));
		myLogInSuccessMessage = new JLabel("");
		setName(UrbanParksGUI.LOG_IN_PANEL);
		setupPanel();
	}
	
	public void setupPanel() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    setSize(screenSize.width, screenSize.height);
	    setPreferredSize(screenSize);
		setLayout(new BorderLayout());
		setupWelcomeLabel();
		setupCenterComponents();
		myCenterMasterPanel.add(myCenterComponentsPanel);
		add(myCenterMasterPanel);
		
		
	}
	
	public void setupCenterComponents() {
		
		JPanel userNameFieldPanel = new JPanel(new FlowLayout());
		JTextField logInTextField = createLogInTextField();
		JLabel userNameLabel = createUserNameLabel();
		userNameFieldPanel.add(userNameLabel);
		userNameFieldPanel.add(logInTextField);
		
		myCenterComponentsPanel.add(userNameFieldPanel);
		myCenterComponentsPanel.add(myLogInSuccessMessage);
		
	}
	
	public void setupWelcomeLabel() {
		JLabel welcomeLabel = new JLabel();
		
		welcomeLabel.setText("Welcome to Urban Parks");
		welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
		welcomeLabel.setBackground(Color.BLACK);
		welcomeLabel.setForeground(Color.WHITE);
		welcomeLabel.setOpaque(true);
		welcomeLabel.setVisible(true);
		add(welcomeLabel, BorderLayout.NORTH);
	}
	
	public JLabel createUserNameLabel() {
		JLabel userNameLabel = new JLabel("User Name: ");
		userNameLabel.setVisible(true);
		return userNameLabel;
		
	}
	
	public JTextField createLogInTextField() {
		
		
		JTextField textField = new JTextField(null, 20);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setToolTipText("<html><b><font color=blue>"
                + "Please enter your username" + "</font></b></html>");
		
		textField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent theEvent) {

				String userName = textField.getText();
				User theUser = mySystem.signIn(userName);
			
				if (theUser != null) {					
					myLogInSuccessMessage.setText("");
					firePropertyChange("logIn", null, theUser);	
				
				} else {
					myLogInSuccessMessage.setText("<html><b><font color=red>"
			                + "User not found" + "</font></b></html>");
					myLogInSuccessMessage.setVisible(true);
				}
			}
		});
		
		return textField;

		
	}
	

}

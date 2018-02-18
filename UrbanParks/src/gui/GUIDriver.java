package gui;

import java.awt.EventQueue;

public final class GUIDriver {
	
	/**
     * Prevents this Driver Utility class from being instantiated.
     */
    private GUIDriver() {
        // Do Nothing
    }
    
    
    /**
     * Starts the GUI program.
     * @param theArgs command-line parameters ignored for this project
     */
    public static void main(final String... theArgs) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	
            	new UrbanParksGUI().start();
                
            }
        });
    }

}

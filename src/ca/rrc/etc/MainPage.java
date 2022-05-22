package ca.rrc.etc;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public abstract class MainPage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ImageIcon logo = new ImageIcon("icons/logo_chicken.png");
	
	public MainPage() {
		
		this.setSize(950, 700);
		this.setIconImage(logo.getImage());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);

	}// end of constructor
	
}// end of class

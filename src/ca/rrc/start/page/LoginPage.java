package ca.rrc.start.page;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

import ca.rrc.etc.FrameInterface;

/**
 * @author Moohyun Kang
 * @version 1.0
 */

public class LoginPage extends JFrame implements FrameInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Connection conn;
	public static final String URL = "jdbc:mysql://localhost/ChickenFarm";
	public static String USER = "moohka";
	public static String PASSWORD = "123";

	private JTextField userField;
	private JPasswordField passField;

	LoginPage() {
		this.setFrame();
	}

	@Override
	public void setFrame() {
		// image
		ImageIcon logo = new ImageIcon("icons/logo_chicken.png");

		ImageIcon userI = new ImageIcon(
				new ImageIcon("login_user.png").getImage().getScaledInstance(20, 20, Image.SCALE_FAST));
		ImageIcon passI = new ImageIcon(
				new ImageIcon("login_password.png").getImage().getScaledInstance(20, 20, Image.SCALE_FAST));

		// frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new GridBagLayout());
		this.setSize(700, 450);
		this.setTitle("Chicken Farm");
		this.setResizable(false);
		this.getContentPane().setBackground(Color.white);
		this.setIconImage(logo.getImage());
		this.setLocationRelativeTo(null);

		// panel
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(500, 275));
		panel.setBackground(Color.decode("#D9C9BA"));
		panel.setLayout(null);
		this.getContentPane().add(panel);

		// title
		JLabel titleLabel = new JLabel("Chicken Farm Login");
		titleLabel.setFont(new Font("SansSerif", Font.BOLD, 35));
		titleLabel.setForeground(Color.decode("#4a390f"));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(51, 42, 393, 45);
		panel.add(titleLabel);

		// label
		JLabel userLabel = new JLabel("User ID: ");
		userLabel.setForeground(Color.black);
		userLabel.setBounds(143, 136, 97, 20);
		userLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
		userLabel.setIcon(userI);
		panel.add(userLabel);

		JLabel passLabel = new JLabel("Password: ");
		passLabel.setForeground(Color.black);
		passLabel.setBounds(126, 172, 114, 20);
		passLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
		passLabel.setIcon(passI);
		panel.add(passLabel);

		// text field
		userField = new JTextField(15);
		userField.setBounds(225, 133, 126, 25);
		userField.setFont(new Font("SansSerif", Font.PLAIN, 15));
		userField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// USER = userField.getText();
			}
		});
		panel.add(userField);

		passField = new JPasswordField(15);
		passField.setBounds(225, 169, 126, 25);
		passField.setFont(new Font("SansSerif", Font.PLAIN, 15));
		passField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// PASSWORD = String.valueOf(passField.getPassword());
			}
		});
		panel.add(passField);

		// button
		JButton loginButton = new JButton();
		loginButton.setText("Login");
		loginButton.setForeground(Color.white);
		loginButton.setBackground(Color.decode("#8C6E52"));
		loginButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		loginButton.setFocusable(false);
		loginButton.setBounds(252, 221, 60, 30);
		loginButton.setFont(new Font("SansSerif", Font.PLAIN, 17));
		loginButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				loginButton.setBackground(Color.decode("#6E4F32"));
			}

			public void mouseExited(MouseEvent e) {
				loginButton.setBackground(Color.decode("#8C6E52"));
			}
		});
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == loginButton) {
					verify();
				}
			}
		});
		panel.add(loginButton);

		this.setVisible(true);
	}// end of setFrame()

	public void verify() {
		try {
			String userInput = userField.getText();
			String passInput = String.valueOf(passField.getPassword());
			assert (userInput.equals("")) : "wrong userid";
			assert (passInput.equals("")) : "wrong password";

			conn = DriverManager.getConnection(URL, USER, PASSWORD);

		} catch (AssertionError | SQLException e) {
			JOptionPane.showMessageDialog(null, "You have entered either wrong User ID or Password.", "Login Fail",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		new HomePage();
		this.dispose();
	}// end of verify()

	public static void main(String[] args) {
		new LoginPage();
	}// end of main
}// end of class
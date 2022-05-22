package ca.rrc.start.page;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

import ca.rrc.etc.FrameInterface;
import ca.rrc.main.page.*;

public class HomePage extends JFrame implements FrameInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static HomePage hp;

	// pages
	private Chicken chick;
	private Customer custo;
	// private Expense expen;

	private JLabel logoutLabel;

	HomePage() {
		hp = this;
		hp.setFrame();
	}

	@Override
	public void setFrame() {

		// image
		ImageIcon logo = new ImageIcon("icons/logo_chicken.png");
		ImageIcon chickenIcon = new ImageIcon(
				new ImageIcon("icons/main_chicken.png").getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));
		ImageIcon customerIcon = new ImageIcon(
				new ImageIcon("icons/main_customer.png").getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));
		ImageIcon expenseIcon = new ImageIcon(
				new ImageIcon("icons/main_expense.png").getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));

		// frame
		this.setBounds(180, 180, 776, 550);
		this.getContentPane().setBackground(Color.white);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Chicken Farm");
		this.setIconImage(logo.getImage());
		this.getContentPane().setLayout(new GridBagLayout());
		this.setResizable(false);

		// panel
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(650, 350));
		panel.setBackground(Color.decode("#D9C9BA"));
		panel.setLayout(null);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		this.getContentPane().add(panel, gbc_panel);

		// label
		JLabel chickLabel = new JLabel("Chicken");
		chickLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
		chickLabel.setForeground(Color.darkGray);
		chickLabel.setBounds(114, 215, 64, 21);
		panel.add(chickLabel);

		JLabel custoLabel = new JLabel("Customer");
		custoLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
		custoLabel.setBounds(296, 215, 76, 21);
		panel.add(custoLabel);

		JLabel expenLabel = new JLabel("Expense");
		expenLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
		expenLabel.setBounds(482, 215, 64, 21);
		panel.add(expenLabel);

		logoutLabel = new JLabel("Logout");
		logoutLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		logoutLabel.setForeground(Color.darkGray);
		logoutLabel.setBounds(4, 334, 53, 15);
		logoutLabel.addMouseListener(new HomePage.LogoutInnerClass());
		panel.add(logoutLabel);

		// button
		JButton chickButton = new JButton();
		chickButton.setIcon(chickenIcon);
		chickButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		chickButton.setHorizontalTextPosition(SwingConstants.CENTER);
		chickButton.setFocusable(false);
		chickButton.setBounds(90, 115, 100, 100);
		chickButton.setBackground(Color.white);
		chickButton.setBorder(new LineBorder(Color.black, 1));
		chickButton.setRolloverEnabled(true);

		chickButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				chickButton.setBorder(new LineBorder(Color.black, 2));
				chickLabel.setForeground(Color.black);
			}

			public void mouseExited(MouseEvent e) {
				chickButton.setBorder(new LineBorder(Color.black, 1));
				chickLabel.setForeground(Color.darkGray);
			}
		});
		chickButton.addActionListener((e) -> {
			if (chick != null) {
				chick.dispose();
				chick.setVisible(true);
			}
			chick = Chicken.getInstance();
		});
		panel.add(chickButton);

		JButton custoButton = new JButton();
		custoButton.setFocusable(false);
		custoButton.setIcon(customerIcon);
		custoButton.setBounds(279, 115, 100, 100);
		custoButton.setBackground(Color.white);
		custoButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				custoButton.setBorder(new LineBorder(Color.black, 2));
				custoLabel.setForeground(Color.black);
			}

			public void mouseExited(MouseEvent e) {
				custoButton.setBorder(new LineBorder(Color.black, 1));
				custoLabel.setForeground(Color.darkGray);
			}
		});
		custoButton.addActionListener((e) -> {
			if (custo != null) {
				custo.dispose();
				custo.setVisible(true);
			}
			custo = Customer.getInstance();
		});
		panel.add(custoButton);

		JButton expenButton = new JButton();
		expenButton.setFocusable(false);
		expenButton.setIcon(expenseIcon);
		expenButton.setBounds(462, 115, 100, 100);
		expenButton.setBackground(Color.white);
		expenButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				expenButton.setBorder(new LineBorder(Color.black, 2));
				expenLabel.setForeground(Color.black);
			}

			public void mouseExited(MouseEvent e) {
				expenButton.setBorder(new LineBorder(Color.black, 1));
				expenLabel.setForeground(Color.darkGray);
			}
		});
		expenButton.addActionListener((e) -> {
			new Thread(() -> new Expense()).start();
		});
		panel.add(expenButton);

		this.setVisible(true);
	}// ene of setFrame

	class LogoutInnerClass implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			int yesNo = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout",
					JOptionPane.YES_NO_OPTION);
			if (yesNo == 0) {
				try {
					LoginPage.conn = null;
				} catch (Exception ignore) {

				}
				hp.dispose();
				new LoginPage();
			}
			if (yesNo == 1) {
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			logoutLabel.setForeground(Color.decode("#F10000"));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			logoutLabel.setForeground(Color.darkGray);

		}
	}// end of inner class
}// end of class

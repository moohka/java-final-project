package ca.rrc.main.page;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

import ca.rrc.etc.MainPage;
import ca.rrc.start.page.LoginPage;

public class Chicken extends MainPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Chicken chicken = null;

	private JTextField numField;
	private JLabel chicken3Label;
	private JLabel chick3Label;
	private JLabel egg3Label;

	int chickenNum;
	int chickNum;
	int trayNum;

	public static Chicken getInstance() {
		if (chicken == null)
			chicken = new Chicken();
		return chicken;
	}

	public Chicken() {

		getData();
		// frame
		this.setTitle("Chicken");
		this.setLocation(800, 50);
		this.getContentPane().setBackground(Color.white);
		this.getContentPane().setLayout(null);

		// panel
		JPanel panel = new JPanel();
		panel.setBackground(Color.decode("#D9C9BA"));
		panel.setBounds(10, 11, 914, 639);
		panel.setLayout(null);
		this.getContentPane().add(panel);

		// sub-panel
		JPanel displayPanel = new JPanel();
		displayPanel.setBackground(Color.decode("#D9C9BA"));
		displayPanel.setBounds(46, 75, 842, 283);
		displayPanel.setLayout(null);
		panel.add(displayPanel);

		JPanel editPanel = new JPanel();
		editPanel.setLayout(null);
		editPanel.setBackground(Color.decode("#D9C9BA"));
		editPanel.setBounds(202, 444, 538, 90);
		panel.add(editPanel);

		// label
		JLabel chicken1Label = new JLabel("Chicken");
		chicken1Label.setForeground(Color.decode("#4a390f"));
		chicken1Label.setBounds(71, 51, 120, 40);
		chicken1Label.setFont(new Font("SansSerif", Font.PLAIN, 30));
		displayPanel.add(chicken1Label);

		JLabel chicken2Label = new JLabel("number of chickens");
		chicken2Label.setForeground(Color.decode("#4a390f"));
		chicken2Label.setFont(new Font("SansSerif", Font.PLAIN, 20));
		chicken2Label.setBounds(34, 170, 187, 33);
		displayPanel.add(chicken2Label);

		chicken3Label = new JLabel();
		chicken3Label.setHorizontalAlignment(SwingConstants.CENTER);
		chicken3Label.setForeground(Color.decode("#4a390f"));
		chicken3Label.setFont(new Font("SansSerif", Font.PLAIN, 20));
		chicken3Label.setBounds(34, 214, 187, 33);
		displayPanel.add(chicken3Label);

		JLabel chick1Lable = new JLabel("Chick");
		chick1Lable.setBounds(378, 51, 88, 40);
		chick1Lable.setForeground(Color.decode("#4a390f"));
		chick1Lable.setFont(new Font("SansSerif", Font.PLAIN, 30));
		displayPanel.add(chick1Lable);

		JLabel chick2Label = new JLabel("number of chicks");
		chick2Label.setBounds(339, 170, 162, 33);
		chick2Label.setForeground(Color.decode("#4a390f"));
		chick2Label.setFont(new Font("SansSerif", Font.PLAIN, 20));
		displayPanel.add(chick2Label);

		chick3Label = new JLabel("0");
		chick3Label.setBounds(318, 214, 198, 33);
		chick3Label.setHorizontalAlignment(SwingConstants.CENTER);
		chick3Label.setForeground(Color.decode("#4a390f"));
		chick3Label.setFont(new Font("SansSerif", Font.PLAIN, 20));
		displayPanel.add(chick3Label);

		JLabel egg1Label = new JLabel("Egg");
		egg1Label.setBounds(674, 51, 70, 40);
		egg1Label.setForeground(Color.decode("#4a390f"));
		egg1Label.setFont(new Font("SansSerif", Font.PLAIN, 30));
		displayPanel.add(egg1Label);

		JLabel egg2Label = new JLabel("number of trays(30pc)");
		egg2Label.setBounds(607, 170, 211, 33);
		egg2Label.setForeground(Color.decode("#4a390f"));
		egg2Label.setFont(new Font("SansSerif", Font.PLAIN, 20));
		displayPanel.add(egg2Label);

		egg3Label = new JLabel("0");
		egg3Label.setBounds(617, 214, 176, 33);
		egg3Label.setHorizontalAlignment(SwingConstants.CENTER);
		egg3Label.setForeground(Color.decode("#4a390f"));
		egg3Label.setFont(new Font("SansSerif", Font.PLAIN, 20));
		displayPanel.add(egg3Label);

		setData();

		// combobox
		JComboBox<String> combobox = new JComboBox<>();
		combobox.setBounds(48, 32, 119, 23);
		combobox.setFont(new Font("SansSerif", Font.PLAIN, 13));
		combobox.addItem("Chicken");
		combobox.addItem("Chick");
		combobox.addItem("Egg");
		editPanel.add(combobox);

		numField = new JTextField();
		numField.setBounds(208, 33, 144, 23);
		numField.setColumns(10);
		editPanel.add(numField);

		// button
		JButton addButton = new JButton("Add");
		addButton.setBounds(419, 11, 80, 30);
		addButton.setForeground(Color.white);
		addButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		addButton.setFocusable(false);
		addButton.setBorder(null);
		addButton.setBackground(Color.decode("#8C6E52"));
		addButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				addButton.setBackground(Color.decode("#6E4F32"));
			}

			public void mouseExited(MouseEvent e) {
				addButton.setBackground(Color.decode("#8C6E52"));
			}
		});
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String choice = (String) combobox.getSelectedItem();
				String numberStr = numField.getText();
				try {
					int number = Integer.parseInt(numberStr);
					int yesNo = JOptionPane.showConfirmDialog(null,
							"Are you sure you want to add " + number + " to " + choice + " ?", "Confirm",
							JOptionPane.YES_NO_OPTION);
					if (yesNo == 0) {
						// click yes
						try {
							String element = null;
							Connection conn = LoginPage.conn;
							if (choice == "Chicken") {
								element = "CNA";
							} else if (choice == "Chick") {
								element = "CCA";
							} else if (choice == "Egg") {
								element = "EGA";
							}
							PreparedStatement pstmt = conn.prepareStatement(
									"update Inventory set Quantity = Quantity + ? where ProductID = ?");
							pstmt.setInt(1, number);
							pstmt.setString(2, element);
							pstmt.executeUpdate();

						} catch (SQLException se) {
							JOptionPane.showMessageDialog(null, "SQL Exception", "Message", JOptionPane.ERROR_MESSAGE);
						}
					}
					if (yesNo == 1) {
						// click no
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Input invalid", "Message", JOptionPane.ERROR_MESSAGE);
				}
				getData();
				setData();
			}
		});
		editPanel.add(addButton);

		JButton removeButton = new JButton("Remove");
		removeButton.setBounds(419, 49, 80, 30);
		removeButton.setForeground(Color.white);
		removeButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		removeButton.setFocusable(false);
		removeButton.setBorder(null);
		removeButton.setBackground(Color.decode("#8C6E52"));
		removeButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				removeButton.setBackground(Color.decode("#6E4F32"));
			}

			public void mouseExited(MouseEvent e) {
				removeButton.setBackground(Color.decode("#8C6E52"));
			}
		});
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String choice = (String) combobox.getSelectedItem();
				String numberStr = numField.getText();
				try {
					int number = Integer.parseInt(numberStr);
					int yesNo = JOptionPane.showConfirmDialog(null,
							"Are you sure you want to subtract " + number + " to " + choice + " ?", "Confirm",
							JOptionPane.YES_NO_OPTION);
					if (yesNo == 0) {
						// click yes
						try {
							String element = null;
							Connection conn = LoginPage.conn;
							if (choice == "Chicken") {
								element = "CNA";
							} else if (choice == "Chick") {
								element = "CCA";
							} else if (choice == "Egg") {
								element = "EGA";
							}
							PreparedStatement pstmt = conn.prepareStatement(
									"update Inventory set Quantity = Quantity - ? where ProductID = ?");
							pstmt.setInt(1, number);
							pstmt.setString(2, element);
							pstmt.executeUpdate();

						} catch (SQLException se) {
							JOptionPane.showMessageDialog(null, "SQL Exception", "Message", JOptionPane.ERROR_MESSAGE);
						}

					}
					if (yesNo == 1) {
						// click no
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Input invalid", "Message", JOptionPane.ERROR_MESSAGE);
				}
				getData();
				setData();
			}
		});
		editPanel.add(removeButton);

		this.setVisible(true);
	}// end of constructor

	public void getData() {
		try {
			Connection conn = LoginPage.conn;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select Quantity from Inventory where ProductID = 'CNA'");
			while (rs.next()) {
				chickenNum = rs.getInt(1);
			}
			rs = stmt.executeQuery("select Quantity from Inventory where ProductID = 'CCA'");
			while (rs.next()) {
				chickNum = rs.getInt(1);
			}
			rs = stmt.executeQuery("select Quantity from Inventory where ProductID = 'EGA'");
			while (rs.next()) {
				trayNum = rs.getInt(1);
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "SQL Exception", "Message", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void setData() {
		chicken3Label.setText("" + chickenNum);
		chick3Label.setText("" + chickNum);
		egg3Label.setText("" + trayNum);
	}

}// end of class

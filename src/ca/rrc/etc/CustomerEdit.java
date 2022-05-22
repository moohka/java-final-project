package ca.rrc.etc;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;

import ca.rrc.main.page.Customer;
import ca.rrc.start.page.LoginPage;

/**
 * 
 */
public class CustomerEdit extends JFrame implements FrameInterface {

	private static final long serialVersionUID = 1L;

	private JComboBox<String> typeBox;
	private JTextField nameField;
	private JFormattedTextField phoneField;
	private JTextField addressField;
	private JFormattedTextField zipcodeField;
	private String id, type, name, phone, address, zipcode;

	public CustomerEdit(String id, String type, String name, String phone, String address, String zipcode) {
		Thread t = new Thread(() -> {
			this.id = id;
			this.type = type;
			this.name = name;
			this.phone = phone;
			this.address = address;
			this.zipcode = zipcode;
			this.setFrame();
		});
		t.start();
	}// end of constructor

	@Override
	public void setFrame() {

		// frame
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(750, 350, 957, 167);
		this.setResizable(false);
		this.getContentPane().setBackground(Color.white);
		this.getContentPane().setLayout(null);

		// panel
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 921, 106);
		panel.setBackground(Color.decode("#D9C9BA"));
		panel.setLayout(null);
		this.getContentPane().add(panel);

		// label
		JLabel typeLabel = new JLabel("Customer Type");
		typeLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
		typeLabel.setBounds(10, 11, 99, 23);
		panel.add(typeLabel);

		JLabel nameLabel = new JLabel("Customer Name");
		nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
		nameLabel.setBounds(113, 10, 119, 25);
		panel.add(nameLabel);

		JLabel phoneLabel = new JLabel("Phone Number");
		phoneLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
		phoneLabel.setBounds(272, 11, 93, 23);
		panel.add(phoneLabel);

		JLabel addressLabel = new JLabel("Delivery Address");
		addressLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
		addressLabel.setBounds(375, 11, 119, 23);
		panel.add(addressLabel);

		JLabel zipcodeLabel = new JLabel("Zip Code");
		zipcodeLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
		zipcodeLabel.setBounds(626, 11, 59, 23);
		panel.add(zipcodeLabel);

		// field
		typeBox = new JComboBox<>();
		typeBox.addItem("Individual");
		typeBox.addItem("Corporate");
		typeBox.setBounds(10, 37, 93, 23);
		typeBox.setFont(new Font("SansSerif", Font.PLAIN, 13));
		if (type.startsWith("I")) {
			typeBox.setSelectedIndex(0);
		} else {
			typeBox.setSelectedIndex(1);
		}
		panel.add(typeBox);

		nameField = new JTextField();
		nameField.setBounds(113, 36, 142, 23);
		nameField.setFont(new Font("SansSerif", Font.PLAIN, 13));
		nameField.setText(name);
		panel.add(nameField);

		try {
			MaskFormatter phoneMask = new MaskFormatter("(###)-###-####");
			phoneMask.setPlaceholderCharacter('_');
			phoneField = new JFormattedTextField(phoneMask);
		} catch (ParseException e1) {
		}
		phoneField.setBounds(272, 36, 93, 23);
		phoneField.setFont(new Font("SansSerif", Font.PLAIN, 13));
		phoneField.setText(phone);
		panel.add(phoneField);

		addressField = new JTextField();
		addressField.setBounds(375, 36, 241, 23);
		addressField.setFont(new Font("SansSerif", Font.PLAIN, 13));
		addressField.setText(address);
		panel.add(addressField);

		try {
			MaskFormatter zipcodeMask = new MaskFormatter("U#U #U#");
			zipcodeMask.setPlaceholderCharacter('_');
			zipcodeField = new JFormattedTextField(zipcodeMask);
		} catch (ParseException e1) {
		}
		zipcodeField.setBounds(626, 36, 66, 23);
		zipcodeField.setFont(new Font("SansSerif", Font.PLAIN, 13));
		zipcodeField.setText(zipcode);
		panel.add(zipcodeField);

		// button
		JButton saveButton = new JButton("SaveEdit");
		saveButton.setBounds(722, 32, 99, 30);
		saveButton.setForeground(Color.white);
		saveButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		saveButton.setFocusable(false);
		saveButton.setBorder(null);
		saveButton.setBackground(Color.decode("#8C6E52"));
		saveButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				saveButton.setBackground(Color.decode("#6E4F32"));
			}

			public void mouseExited(MouseEvent e) {
				saveButton.setBackground(Color.decode("#8C6E52"));
			}
		});
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// click
				synchronized (Customer.getDataThr) {
					try {
						String nameCheck = nameField.getText();
						assert (!nameCheck.equals("")) : "name missing";

						Connection conn = DriverManager.getConnection(LoginPage.URL, LoginPage.USER,
								LoginPage.PASSWORD);
						PreparedStatement pstmt = conn.prepareStatement(
								"update Customer set CustomerType = ?, CustomerName = ?, PhoneNumber = ?, Address = ?, ZipCode = ? where CustomerID = ?;");

						pstmt.setString(1, typeBox.getSelectedItem().toString());
						pstmt.setString(2, nameField.getText());
						pstmt.setString(3, phoneField.getText());
						pstmt.setString(4, addressField.getText());
						pstmt.setString(5, zipcodeField.getText());
						pstmt.setString(6, id);

						pstmt.executeUpdate();

						conn.close();
						pstmt.close();
						Customer.getDataThr.notifyAll();
						dispose();
					} catch (AssertionError | SQLException ae) {
						JOptionPane.showMessageDialog(null, "Something went wrong", "Error", JOptionPane.ERROR_MESSAGE);
						dispose();
					}
				}
			}// end of sync
		});
		panel.add(saveButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(831, 32, 80, 30);
		cancelButton.setForeground(Color.white);
		cancelButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		cancelButton.setFocusable(false);
		cancelButton.setBorder(null);
		cancelButton.setBackground(Color.decode("#8C6E52"));
		cancelButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				cancelButton.setBackground(Color.decode("#6E4F32"));
			}

			public void mouseExited(MouseEvent e) {
				cancelButton.setBackground(Color.decode("#8C6E52"));
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}// click button
		});
		panel.add(cancelButton);

		this.setVisible(true);
	}// end of constructor
}// end of class

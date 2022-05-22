package ca.rrc.main.page;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.*;
import java.awt.event.*;

import ca.rrc.etc.CustomerEdit;
import ca.rrc.etc.CustomerInfo;
import ca.rrc.etc.FrameInterface;
import ca.rrc.etc.MainPage;
import ca.rrc.start.page.LoginPage;

/**
 * search feature is done by sql command. And for sort feature, I used defaut
 * sorting feature that comes with Jtable.
 * 
 * getData() method is run on separate thread. It wait for notify() one it
 * completes one loop. earchFlag is added to prevent the program from notifying
 * not-waiting thread.
 */
public class Customer extends MainPage implements FrameInterface {

	private static final long serialVersionUID = 1L;
	private static Customer custo = null;
	public static Thread getDataThr;
	public boolean searchFlag;

	private DefaultTableModel model;
	private JTable table;

	private JComboBox<String> typeBox;
	private JTextField nameField;
	private JFormattedTextField phoneField;
	private JTextField addressField;
	private JFormattedTextField zipcodeField;

	private JTextField searchField;

	public static Customer getInstance() {
		if (custo == null)
			custo = new Customer();
		return custo;
	}

	public Customer() {
		this.setFrame();
		getDataThr = new Thread(() -> {
			this.getData();
		});
		getDataThr.setDaemon(true);
		getDataThr.start();
	}

	public void setFrame() {

		// frame
		this.setTitle("Customer");
		this.setLocation(820, 80);
		this.getContentPane().setBackground(Color.white);
		this.getContentPane().setLayout(null);

		// panel
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 914, 639);
		panel.setBackground(Color.decode("#D9C9BA"));
		panel.setLayout(null);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		this.getContentPane().add(panel, gbc_panel);

		JPanel savePanel = new JPanel();
		savePanel.setBounds(24, 149, 894, 75);
		savePanel.setBackground(Color.decode("#D9C9BA"));
		savePanel.setLayout(null);
		panel.add(savePanel);

		JPanel tablePanel = new JPanel();
		tablePanel.setBounds(57, 261, 836, 355);
		tablePanel.setBackground(Color.decode("#D9C9BA"));
		tablePanel.setLayout(null);
		panel.add(tablePanel);

		// table & data
		table = new JTable() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		table.setSurrendersFocusOnKeystroke(true);
		table.setShowVerticalLines(false);
		table.setFont(new Font("SansSerif", Font.PLAIN, 13));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 39, 697, 291);
		scrollPane.setViewportView(table);
		tablePanel.add(scrollPane);

		// label
		JLabel titleLabel = new JLabel("Customer Information");
		titleLabel.setForeground(Color.decode("#4a390f"));
		titleLabel.setFont(new Font("SansSerif", Font.BOLD, 50));
		titleLabel.setBounds(24, 11, 552, 97);
		panel.add(titleLabel);

		JLabel typeLabel = new JLabel("Customer Type");
		typeLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
		typeLabel.setBounds(10, 11, 99, 23);
		savePanel.add(typeLabel);

		JLabel nameLabel = new JLabel("Customer Name");
		nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
		nameLabel.setBounds(113, 10, 119, 25);
		savePanel.add(nameLabel);

		JLabel phoneLabel = new JLabel("Phone Number");
		phoneLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
		phoneLabel.setBounds(272, 11, 93, 23);
		savePanel.add(phoneLabel);

		JLabel addressLabel = new JLabel("Delivery Address");
		addressLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
		addressLabel.setBounds(375, 11, 119, 23);
		savePanel.add(addressLabel);

		JLabel zipcodeLabel = new JLabel("Zip Code");
		zipcodeLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
		zipcodeLabel.setBounds(626, 11, 59, 23);
		savePanel.add(zipcodeLabel);

		JLabel searchLabel = new JLabel("Search:");
		searchLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
		searchLabel.setBounds(10, 11, 55, 17);
		tablePanel.add(searchLabel);

		// text field
		typeBox = new JComboBox<>();
		typeBox.setBounds(10, 36, 93, 23);
		typeBox.setBackground(Color.white);
		typeBox.setFont(new Font("SansSerif", Font.PLAIN, 13));
		typeBox.addItem("Individual");
		typeBox.addItem("Corporate");
		savePanel.add(typeBox);

		nameField = new JTextField();
		nameField.setBounds(113, 36, 142, 23);
		nameField.setFont(new Font("SansSerif", Font.PLAIN, 13));
		nameField.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				JTextField source = (JTextField) e.getComponent();
				source.setText("");
				source.removeFocusListener(this);
			}
		});
		savePanel.add(nameField);

		try {
			MaskFormatter phoneMask = new MaskFormatter("(###)-###-####");
			phoneMask.setPlaceholderCharacter('_');
			phoneField = new JFormattedTextField(phoneMask);
		} catch (ParseException e1) {
		}
		phoneField.setBounds(272, 36, 93, 23);
		phoneField.setFont(new Font("SansSerif", Font.PLAIN, 13));
		phoneField.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				JTextField source = (JTextField) e.getComponent();
				source.setText("");
				source.removeFocusListener(this);
			}
		});
		savePanel.add(phoneField);

		addressField = new JTextField();
		addressField.setBounds(375, 36, 241, 23);
		addressField.setFont(new Font("SansSerif", Font.PLAIN, 13));
		savePanel.add(addressField);

		try {
			MaskFormatter zipcodeMask = new MaskFormatter("U#U #U#");
			zipcodeMask.setPlaceholderCharacter('_');
			zipcodeField = new JFormattedTextField(zipcodeMask);
		} catch (ParseException e1) {
		}
		zipcodeField.setBounds(626, 36, 66, 23);
		zipcodeField.setFont(new Font("SansSerif", Font.PLAIN, 13));
		savePanel.add(zipcodeField);

		searchField = new JTextField();
		searchField.setBounds(62, 7, 160, 23);
		searchField.setFont(new Font("SansSerif", Font.PLAIN, 13));
		searchField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				synchronized (getDataThr) {
					if (searchFlag) {
						getDataThr.notifyAll();
					}
				}
			}
		});
		tablePanel.add(searchField);

		// button
		JButton saveButton = new JButton("Save");
		saveButton.setBounds(702, 31, 80, 30);
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
				int yesNo = JOptionPane.showConfirmDialog(null, "Confirm Save", "Confirm", JOptionPane.YES_NO_OPTION);
				synchronized (getDataThr) {
					if (yesNo == 0) {
						try {
							String nameCheck = nameField.getText();
							assert (!nameCheck.equals("")) : "name missing";

							Connection conn = LoginPage.conn;
							PreparedStatement pstmt = conn.prepareStatement(
									"insert into Customer(CustomerType, CustomerName, PhoneNumber, Address, ZipCode) values(?,?,?,?,?);");

							pstmt.setString(1, typeBox.getSelectedItem().toString());
							pstmt.setString(2, nameField.getText());
							pstmt.setString(3, phoneField.getText());
							pstmt.setString(4, addressField.getText());
							pstmt.setString(5, zipcodeField.getText());
							pstmt.executeUpdate();

							pstmt.close();
							getDataThr.notifyAll();
						} catch (AssertionError | SQLException se) {
							JOptionPane.showMessageDialog(null, "input invalid", "Message", JOptionPane.ERROR_MESSAGE);
						}
					} // click yes
				}
			}// click button
		});
		savePanel.add(saveButton);

		JButton clearButton = new JButton("Clear");
		clearButton.setBounds(792, 31, 80, 30);
		clearButton.setForeground(Color.white);
		clearButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		clearButton.setFocusable(false);
		clearButton.setBorder(null);
		clearButton.setBackground(Color.decode("#8C6E52"));
		clearButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				clearButton.setBackground(Color.decode("#6E4F32"));
			}

			public void mouseExited(MouseEvent e) {
				clearButton.setBackground(Color.decode("#8C6E52"));
			}
		});
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nameField.setText("");
				phoneField.setText("");
				addressField.setText("");
				zipcodeField.setText("");
			}// click button
		});
		savePanel.add(clearButton);

		JButton editButton = new JButton("Edit");
		editButton.setBounds(733, 56, 80, 30);
		editButton.setForeground(Color.white);
		editButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		editButton.setFocusable(false);
		editButton.setBorder(null);
		editButton.setBackground(Color.decode("#8C6E52"));
		editButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				editButton.setBackground(Color.decode("#6E4F32"));
			}

			public void mouseExited(MouseEvent e) {
				editButton.setBackground(Color.decode("#8C6E52"));
			}
		});
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(() -> {
					String id = null;
					String type = null;
					String name = null;
					String phone = null;
					String address = null;
					String zipcode = null;

					try {
						for (int column = 0; column < 6; column++) {

							int viewRow = table.getSelectedRow();
							if (viewRow == -1) {
								throw new ArrayIndexOutOfBoundsException();
							}
							int modelRow = table.convertRowIndexToModel(viewRow);
							String value = table.getModel().getValueAt(modelRow, column).toString();

							if (value != null) {
								switch (column) {
								case 0:
									id = value;
								case 1:
									if (value.startsWith("I")) {
										type = "I";
									} else {
										type = "C";
									}
									break;
								case 2:
									name = value;
									break;
								case 3:
									phone = value;
									break;
								case 4:
									address = value;
									break;
								case 5:
									zipcode = value;
									break;
								}
							} else {
								switch (column) {
								case 2:
									name = "";
									break;
								case 3:
									phone = "";
									break;
								case 4:
									address = "";
									break;
								case 5:
									zipcode = "";
									break;
								}
							}
						}
						new CustomerEdit(id, type, name, phone, address, zipcode);
					} catch (ArrayIndexOutOfBoundsException ne) {
						JOptionPane.showMessageDialog(null, "Select a row that you wish to edit.", "Message",
								JOptionPane.ERROR_MESSAGE);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Something went wrong.", "Message",
								JOptionPane.ERROR_MESSAGE);
					}
				}).start();
			}// click button
		});
		tablePanel.add(editButton);

		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(733, 103, 80, 30);
		deleteButton.setForeground(Color.white);
		deleteButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		deleteButton.setFocusable(false);
		deleteButton.setBorder(null);
		deleteButton.setBackground(Color.decode("#8C6E52"));
		deleteButton.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				deleteButton.setBackground(Color.decode("#6E4F32"));
			}

			public void mouseExited(MouseEvent e) {
				deleteButton.setBackground(Color.decode("#8C6E52"));
			}
		});
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int yesNo = JOptionPane.showConfirmDialog(null, "Confirm Delete", "Confirm",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				synchronized (getDataThr) {
					if (yesNo == 0) {
						try {
							Connection conn = LoginPage.conn;
							Statement stmt = conn.createStatement();

							int column = 0;
							int row = table.getSelectedRow();
							String value = table.getModel().getValueAt(row, column).toString();
							stmt.execute("delete from Customer where CustomerID = '" + value + "';");

							stmt.close();
							getDataThr.notifyAll();
						} catch (SQLException se) {
							JOptionPane.showMessageDialog(null, "SQL Exception", "Message", JOptionPane.ERROR_MESSAGE);
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "Pick a row that you wish to delete", "Message",
									JOptionPane.ERROR_MESSAGE);
						}
					} // click yes
				} // end of delete
			}// click button
		});
		tablePanel.add(deleteButton);

		this.setVisible(true);
	}// end of setFrame

	private void getData() {
		synchronized (getDataThr) {
			try {
				do {
					Connection conn = LoginPage.conn;
					Statement stmt = conn.createStatement();
					model = new DefaultTableModel();
					String keyword = searchField.getText();

					if (keyword == null || keyword == "") {
						ResultSet rs = stmt.executeQuery("select * from Customer;");
						ResultSetMetaData meta = rs.getMetaData();
						int columnCount = meta.getColumnCount();

						for (int i = 1; i <= columnCount; i++) {
							String name = meta.getColumnName(i);
							model.addColumn(name);
						}
						while (rs.next()) {
							Vector<CustomerInfo<String>> dataRow = new Vector<>();
							for (int i = 1; i <= columnCount; i++) {
								dataRow.add(new CustomerInfo<String>(rs.getString(i)));
							}
							model.addRow(dataRow);
						}
						table.setModel(model);
						table.setAutoCreateRowSorter(true);

						stmt.close();
						rs.close();
					} else {
						ResultSet rs = stmt.executeQuery("select * from Customer where CustomerType like '%" + keyword
								+ "%' or CustomerName like '%" + keyword + "%' or " + "PhoneNumber like '%" + keyword
								+ "%' or Address like '%" + keyword + "%' or ZipCode like '%" + keyword + "%';");
						ResultSetMetaData meta = rs.getMetaData();
						int columnCount = meta.getColumnCount();
						for (int i = 1; i <= columnCount; i++) {
							String name = meta.getColumnName(i);
							model.addColumn(name);
						}
						while (rs.next()) {
							Vector<CustomerInfo<String>> dataRow = new Vector<>();
							for (int i = 1; i <= columnCount; i++) {
								dataRow.add(new CustomerInfo<String>(rs.getString(i)));
							}
							model.addRow(dataRow);
						}
						table.setModel(model);
						table.setAutoCreateRowSorter(true);

						stmt.close();
						rs.close();
					}
					searchFlag = true; 
					getDataThr.wait();
					searchFlag = false;
				} while (true);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, "SQL Exception", "Message", JOptionPane.ERROR_MESSAGE);
			} catch (InterruptedException ie) {
				JOptionPane.showMessageDialog(null, "wait() interrupted", "Message", JOptionPane.ERROR_MESSAGE);
			}
		}
	}// end of getDataThr(search)

}// end of class

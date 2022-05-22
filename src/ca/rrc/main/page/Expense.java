package ca.rrc.main.page;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.locks.*;
import java.util.stream.*;
import javax.swing.*;
import java.time.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import ca.rrc.etc.MainPage;

/**
 * ReadWriteLock has been added to Expense class to 1.allow mutiple reader when there is no writer 2.allow only one writer.
 */
public class Expense extends MainPage {

	
	private static final long serialVersionUID = 1L;

	private ReadWriteLock rwl = new ReentrantReadWriteLock();
	private Lock rl = rwl.readLock();
	private Lock wl = rwl.writeLock();

	static NumberFormat toCAD = NumberFormat.getCurrencyInstance(Locale.CANADA);

	Date date = new Date();
	LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	int year = localDate.getYear();

	public Expense() {

		// frame
		this.setTitle("Expense");
		this.setLocation(840, 110);
		this.getContentPane().setBackground(Color.white);
		this.getContentPane().setLayout(null);

		// panel
		JPanel panel = new JPanel();
		panel.setBackground(Color.decode("#D9C9BA"));
		panel.setBounds(10, 11, 914, 639);
		panel.setLayout(null);
		this.getContentPane().add(panel);

		JPanel subPanel = new JPanel();
		subPanel.setBackground(Color.decode("#D9C9BA"));
		subPanel.setBounds(52, 41, 837, 587);
		subPanel.setLayout(null);
		panel.add(subPanel);

		JPanel addPanel = new JPanel();
		addPanel.setBackground(Color.decode("#D9C9BA"));
		addPanel.setBounds(10, 11, 787, 107);
		addPanel.setLayout(null);
		subPanel.add(addPanel);

		JPanel readPanel = new JPanel();
		readPanel.setBackground(Color.decode("#D9C9BA"));
		readPanel.setBounds(10, 129, 787, 447);
		readPanel.setLayout(null);
		subPanel.add(readPanel);

		// label
		JLabel yearLabel = new JLabel("Year");
		yearLabel.setBounds(10, 11, 35, 25);
		yearLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
		addPanel.add(yearLabel);

		JLabel monthLabel = new JLabel("Month");
		monthLabel.setBounds(106, 11, 38, 25);
		monthLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
		addPanel.add(monthLabel);

		JLabel dayLabel = new JLabel("Day");
		dayLabel.setBounds(174, 11, 38, 25);
		dayLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
		addPanel.add(dayLabel);

		JLabel cateLabel = new JLabel("Category");
		cateLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
		cateLabel.setBounds(222, 11, 58, 25);
		addPanel.add(cateLabel);

		JLabel detailLabel = new JLabel("Detail: ");
		detailLabel.setBounds(10, 72, 87, 25);
		detailLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
		addPanel.add(detailLabel);

		JLabel amountLabel = new JLabel("Amount Spent: ");
		amountLabel.setBounds(462, 72, 93, 25);
		amountLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
		addPanel.add(amountLabel);

		JLabel year2Label = new JLabel("Year");
		year2Label.setFont(new Font("SansSerif", Font.PLAIN, 13));
		year2Label.setBounds(10, 12, 38, 25);
		readPanel.add(year2Label);

		JLabel month2Label = new JLabel("Month");
		month2Label.setBounds(92, 12, 38, 25);
		month2Label.setFont(new Font("SansSerif", Font.PLAIN, 13));
		readPanel.add(month2Label);

		// text field
		JTextField descriptionField = new JTextField();
		descriptionField.setBounds(50, 72, 388, 23);
		descriptionField.setFont(new Font("SansSerif", Font.PLAIN, 13));
		addPanel.add(descriptionField);

		JTextField priceField = new JTextField();
		priceField.setBounds(553, 72, 93, 23);
		priceField.setFont(new Font("SansSerif", Font.PLAIN, 13));
		addPanel.add(priceField);

		// comboBox
		JComboBox<Integer> yearBox = new JComboBox<>();
		yearBox.setBounds(10, 38, 87, 23);
		getYears().forEach((y) -> yearBox.addItem(y));
		addPanel.add(yearBox);

		JComboBox<Integer> monthBox = new JComboBox<>();
		monthBox.setBounds(107, 38, 51, 23);
		getMonths().forEach((m) -> monthBox.addItem(m));
		addPanel.add(monthBox);

		JComboBox<Integer> dayBox = new JComboBox<>();
		dayBox.setBounds(168, 38, 44, 23);
		getDays().forEach((d) -> dayBox.addItem(d));
		addPanel.add(dayBox);

		JComboBox<String> cateBox = new JComboBox<>();
		cateBox.setBounds(222, 38, 87, 23);
		cateBox.addItem("Feed");
		cateBox.addItem("Fuel");
		cateBox.addItem("Payroll");
		cateBox.addItem("Others");
		addPanel.add(cateBox);

		JComboBox<Integer> year2Box = new JComboBox<>();
		year2Box.setBounds(10, 40, 72, 23);
		getYears().forEach((y) -> year2Box.addItem(y));
		readPanel.add(year2Box);

		JComboBox<Integer> month2Box = new JComboBox<>();
		month2Box.setBounds(92, 40, 48, 23);
		getMonths().forEach((m) -> month2Box.addItem(m));
		readPanel.add(month2Box);

		// text
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(false);
		textArea.setFont(new Font("SansSerif", Font.PLAIN, 15));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 74, 605, 362);
		scrollPane.setViewportView(textArea);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		readPanel.add(scrollPane);

		// button
		JButton addButton = new JButton("Add");
		addButton.setBounds(685, 68, 80, 30);
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
		addButton.addActionListener(e -> {
			// click
			new Thread(() -> {
				try {
					int yesNo = JOptionPane.showConfirmDialog(null, "Are you sure you want to add a line?", "Confirm",
							JOptionPane.YES_NO_OPTION);
					wl.lock();
					// yes
					if (yesNo == 0) {
						String spentYear = yearBox.getSelectedItem().toString();
						String spentMonth = monthBox.getSelectedItem().toString();
						int spentMonthInt = Integer.parseInt(spentMonth);
						int spentDay = Integer.parseInt(dayBox.getSelectedItem().toString());
						String spentCate = cateBox.getSelectedItem().toString();
						String spentdescription;
						spentdescription = descriptionField.getText();

						String spentPriceStr;
						spentPriceStr = priceField.getText();
						double spentPrice = Double.parseDouble(spentPriceStr);

						File file = new File("expenses/" + spentYear + "-" + spentMonth + ".txt");
						Path path = file.toPath();

						if (file.exists()) {
						} else {
							Files.createFile(path);
						}

						FileWriter fw = new FileWriter(file, true);
						BufferedWriter bw = new BufferedWriter(fw);
						bw.write(spentYear + "/" + String.format("%02d", spentMonthInt) + "/"
								+ String.format("%02d", spentDay) + "\n");
						bw.write("       " + spentCate + " - " + spentdescription + ", " + toCAD.format(spentPrice)
								+ "\n");

						// finish
						bw.flush();
						bw.close();
					}
				} catch (Exception re) {
					JOptionPane.showMessageDialog(null, "Input is either missing or invalid.", "Invalid",
							JOptionPane.ERROR_MESSAGE);
				} finally {
					wl.unlock();
				}
			}).start();
		});
		addPanel.add(addButton);

		JButton readButton = new JButton("Read");
		readButton.setBounds(165, 34, 80, 30);
		readButton.setForeground(Color.WHITE);
		readButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		readButton.setFocusable(false);
		readButton.setBorder(null);
		readButton.setBackground(Color.decode("#8C6E52"));
		readButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				readButton.setBackground(Color.decode("#6E4F32"));
			}

			public void mouseExited(MouseEvent e) {
				readButton.setBackground(Color.decode("#8C6E52"));
			}
		});
		readButton.addActionListener(e -> {
			// click
			new Thread(() -> {
				try {
					String searchYear = year2Box.getSelectedItem().toString();
					String searchMonth = month2Box.getSelectedItem().toString();
					rl.lock();
					File file = new File("expenses/" + searchYear + "-" + searchMonth + ".txt");
					if (file.exists()) {
						FileReader fr = new FileReader(file);
						BufferedReader br = new BufferedReader(fr);
						textArea.read(br, file);
					} else {
						textArea.setText("");
					}
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(null, "IO Exception", "Message", JOptionPane.ERROR_MESSAGE);
				} finally {
					rl.unlock();
				}
			}).start();
		});
		readPanel.add(readButton);

		JButton editButton = new JButton("SaveEdit");
		editButton.setForeground(Color.WHITE);
		editButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		editButton.setFocusable(false);
		editButton.setBorder(null);
		editButton.setBackground(new Color(140, 110, 82));
		editButton.setBounds(656, 256, 80, 30);
		editButton.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				editButton.setBackground(Color.decode("#6E4F32"));
			}

			public void mouseExited(MouseEvent e) {
				editButton.setBackground(Color.decode("#8C6E52"));
			}
		});
		editButton.addActionListener(e -> {
			new Thread(() -> {
				// click
				try {
					int yesNo = JOptionPane.showConfirmDialog(null,
							"It will override existing text file. Do you want to continue?", "Warning",
							JOptionPane.WARNING_MESSAGE);
					wl.lock();
					if (yesNo == 0) {
						String searchYear = year2Box.getSelectedItem().toString();
						String searchMonth = month2Box.getSelectedItem().toString();

						File file = new File("expenses/" + searchYear + "-" + searchMonth + ".txt");
						FileWriter fw = new FileWriter(file);
						BufferedWriter bw = new BufferedWriter(fw);
						if (file.exists()) {

						} else {
							file.createNewFile();
						}
						bw.write(textArea.getText());
						
						// finish
						bw.flush();
						bw.close();

					}
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(null, "IO Exception", "Message", JOptionPane.ERROR_MESSAGE);
				} finally {
					wl.unlock();
				}
			}).start();
		});
		readPanel.add(editButton);

		this.setVisible(true);
	}// end of constructor

	public IntStream getYears() {
		IntStream yearStream = IntStream.iterate(year, (y) -> y >= 2010, (y) -> y - 1);
		return yearStream;
	}

	public IntStream getMonths() {
		IntStream monthStream = IntStream.iterate(1, (m) -> m <= 12, (m) -> m + 1);
		return monthStream;
	}

	public IntStream getDays() {
		IntStream dayStream = IntStream.iterate(1, (d) -> d <= 31, (d) -> d + 1);
		return dayStream;
	}

}// end of class

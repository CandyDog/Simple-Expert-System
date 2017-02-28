package ui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import kernel.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;

/**
 * testCase1 = "covering feathers AND swims t AND wings swimming AND feet webbed AND flies f"; 
 * testCase2 = "covering hair AND eats cud AND legs long AND color tawny AND spots dark"; 
 * testCase3 = "covering hair AND eats meat AND color tawny AND teeth pointed AND claws t AND eyes forward"; 
 * testCase4 = "eats cud AND milk t AND toes even AND antlers hornlike"; 
 * testCase5 = "covering hair AND eats meat AND color tawny AND spots dark";
 * 
 * @author CaptainXI
 *
 */

public class MainUI
{

	private JFrame frmAnimalExpertSystem;
	private JTextField inputField;

	public static void main(String[] args) {		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI window = new MainUI();
					window.frmAnimalExpertSystem.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainUI() {
		initialize();
	}

	/**
	  *  Initialize the contents of the frame.
	  */
	private void initialize() {
		frmAnimalExpertSystem = new JFrame();
		frmAnimalExpertSystem.setTitle("Animal Expert System  --Powered by CaptainXI");
		frmAnimalExpertSystem.setBounds(100, 100, 1000, 600);
		frmAnimalExpertSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAnimalExpertSystem.setResizable(false);
		frmAnimalExpertSystem.getContentPane().setLayout(null);
		
		/**
		 *  The text tells user some information.
		 */
		Container cp = frmAnimalExpertSystem.getContentPane();
		JLabel welcome = new JLabel("Hello, there! This is a simple animal identification expert system.");
		welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
		welcome.setHorizontalAlignment(SwingConstants.CENTER);
		welcome.setFont(new Font("Georgia", Font.PLAIN, 18));
		welcome.setBounds(100, 15, 800, 40);
		cp.add(welcome);
		JLabel intro = new JLabel("The following table shows our knowledge base.");
		intro.setHorizontalAlignment(SwingConstants.CENTER);
		intro.setHorizontalTextPosition(SwingConstants.CENTER);
		intro.setFont(new Font("Georgia", Font.PLAIN, 18));
		intro.setBounds(100, 50, 800, 40);
		cp.add(intro);
		
		/**
		 *  Show the knowledge base.
		 */
		JScrollPane tablePane = new JScrollPane();
		tablePane.setBounds(100, 100, 800, 275);
		cp.add(tablePane);
		
		
		// JTable Settings
		String[] columnNames = {"Rules", "Conditions", "Conclusions", "Order"};
		Object[][] data = new Object[20][4];
		for (int i = 0; i < 20; i++) {
			data[i][0] = i+1;
			data[i][1] = RuleSystem.rules[i+1].conditions;
			data[i][2] = RuleSystem.rules[i+1].conclusion;
		}
		@SuppressWarnings("serial")
		final DefaultTableModel myModel = new DefaultTableModel(data, columnNames) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		final JTable ruleSystem = new JTable(myModel);
		// Set the height of each row
		ruleSystem.setRowHeight(20);
		TableColumn firstColumn = ruleSystem.getColumnModel().getColumn(0);
		firstColumn.setPreferredWidth(40);
		TableColumn secondColumn = ruleSystem.getColumnModel().getColumn(1);
		secondColumn.setPreferredWidth(500);
		TableColumn thirdColumn = ruleSystem.getColumnModel().getColumn(2);
		thirdColumn.setPreferredWidth(150);
		
		ruleSystem.setEnabled(true);
		ruleSystem.getTableHeader().setReorderingAllowed(false);
		ruleSystem.getTableHeader().setResizingAllowed(false);
		ruleSystem.setFont(new Font("Courier New", Font.PLAIN, 14));
		tablePane.setViewportView(ruleSystem);
		
		/**
		 * Show the input example.
		 */
		JLabel input = new JLabel("Please input your conditions below. (i.e. eats cud AND milk t AND toes even AND antlers hornlike)");
		input.setForeground(new Color(0, 100, 0));
		input.setHorizontalTextPosition(SwingConstants.CENTER);
		input.setHorizontalAlignment(SwingConstants.LEFT);
		input.setFont(new Font("Courier New", Font.PLAIN, 14));
		input.setBounds(100, 400, 800, 40);
		cp.add(input);
		
		/**
		 * Show the final result.
		 */
		JLabel finalResult = new JLabel("The final result we can get from your input was: ");
		finalResult.setFont(new Font("Georgia", Font.PLAIN, 16));
		finalResult.setBounds(100, 530, 360, 30);
		cp.add(finalResult);
		
		final JLabel generation = new JLabel("");
		generation.setFont(new Font("Georgia", Font.BOLD, 16));
		generation.setBounds(450, 530, 360, 30);
		cp.add(generation);
		
		/**
		 * Create button and add some events on it.
		 */
		final JButton button = new JButton("Confirm");
		button.setForeground(Color.RED);
		button.setFont(new Font("Times New Roman", Font.BOLD, 18));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < RuleSystem.rules.length - 1; i++) {
					ruleSystem.setValueAt("", i, 3);
				}
				if (inputField.getText().trim().equals(""))
					generation.setText("ERROR! PLEASE INPUT SOMETHING!");
				else {
					List<String> inConditions = RuleSystem.Dealing(inputField.getText());
					List<String> generations = new ArrayList<>();
					int ruleCount = -1, order = 0, initialSize = 0;
					for (Rule r:RuleSystem.rules) {
						ruleCount ++;
						if (findAll(r.conditions, inConditions)){
							order++;
							ruleSystem.setValueAt(order, ruleCount-1, 3);
							removeCond(inConditions, r.conditions);
							inConditions.add(r.conclusion);
							generations.add(r.conclusion);
						}
					}
					if (generations.isEmpty())
						generation.setText("NOTHING");
					else {
						int latestSize = generations.size();
						while (initialSize != latestSize) {
							initialSize = latestSize;
							ruleCount = -1; 
							for (Rule r:RuleSystem.rules) {
								ruleCount ++;
								if (findAll(r.conditions, inConditions)){
									order++;
									if ((ruleSystem.getValueAt(ruleCount-1, 3)) == "")
										ruleSystem.setValueAt(order, ruleCount-1, 3);
									inConditions.add(r.conclusion);
									if (!generations.contains(r.conclusion))
										generations.add(r.conclusion);
								}
							}
							latestSize = generations.size();
						}
						generation.setText(generations.get(generations.size()-1));
					}
				}	
			}
		});
		button.setBounds(400, 480, 150, 50);
		cp.add(button);
		
		
		/**
		 * Create inputField and let user type in his conditions.
		 */
		inputField = new JTextField();
		inputField.setForeground(new Color(0, 100, 0));
		inputField.setFont(new Font("Courier New", Font.PLAIN, 15));
		inputField.setBounds(100, 435, 800, 40);
		inputField.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar()==KeyEvent.VK_ENTER){
					button.doClick();
				}
			}
		});
		cp.add(inputField);
		
		final JDialog newRule = new JDialog(frmAnimalExpertSystem, "Insert A New Rule", true);
		Container diacp = newRule.getContentPane();
		JPanel pa1 = new JPanel();
		pa1.setLayout(null);
		JLabel lb1 = new JLabel("Please input new rule. (ie. IF covering hair THEN subclass mammal)");
		lb1.setFont(new Font("Arial", Font.PLAIN, 16));
		lb1.setBounds(0, 0, 500, 80);
		lb1.setHorizontalAlignment(SwingConstants.CENTER);
		final JTextField tf1 = new JTextField();
		tf1.setFont(new Font("Arial", Font.PLAIN, 16));
		tf1.setBounds(0, 80, 500, 50);
		tf1.setHorizontalAlignment(SwingConstants.CENTER);
		JButton bt1 = new JButton("Confirm");
		bt1.setFont(new Font("Arial", Font.PLAIN, 16));
		bt1.setBounds(200, 150, 100, 50);
		bt1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = tf1.getText();
				if (text.trim().equals(""))
					return;
				Rule newRule = new Rule(text);
				RuleSystem.add(newRule);
				Object[] rowData = new Object[4];
				int len = RuleSystem.rules.length-1;
				rowData[0] = len;
				rowData[1] = RuleSystem.rules[len].conditions;
				rowData[2] = RuleSystem.rules[len].conclusion;
				myModel.addRow(rowData);
			}
		});
		pa1.add(lb1);
		pa1.add(tf1);
		pa1.add(bt1);
		diacp.add(pa1);
		newRule.setBounds(500, 500, 500, 250);
		newRule.setResizable(false);
		newRule.setVisible(false);
		
		JMenuItem mntmInsert = new JMenuItem("Insert New Rule");
		mntmInsert.setFont(new Font("Georgia", Font.PLAIN, 16));
		mntmInsert.setHorizontalAlignment(SwingConstants.CENTER);
		mntmInsert.setBounds(350, 375, 150, 30);
		mntmInsert.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				newRule.setVisible(true);
			}
		});
		frmAnimalExpertSystem.getContentPane().add(mntmInsert);
		
		JMenuItem mntmDelete = new JMenuItem("Delete Rules");
		mntmDelete.setFont(new Font("Georgia", Font.PLAIN, 16));
		mntmDelete.setHorizontalAlignment(SwingConstants.CENTER);
		mntmDelete.setBounds(500, 375, 150, 30);
		mntmDelete.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int index = ruleSystem.getSelectedRow();
				
				if (index == -1) 
					return;

				RuleSystem.remove(index);
				myModel.removeRow(index);
				for (int i = index; i < ruleSystem.getRowCount(); i++)
					myModel.setValueAt(i+1, i, 0);
			}
		});
		frmAnimalExpertSystem.getContentPane().add(mntmDelete);
		
	}

	/**
	 * Find each element of one list in another list. If all elements are found,
	 * return true; otherwise false.
	 * 
	 * @param smallList
	 * @param bigList
	 * @return
	 */
	private boolean findAll(List<String> smallList, List<String> bigList)
	{
		// TODO Auto-generated method stub
		if (smallList == null)
			return false;
		for (String i : smallList) {
			if (!bigList.contains(i))
				return false;
		}
		return true;
	}
	
	
	private void removeCond(List<String> bigList, List<String> smallList) {
		for (String i:smallList) {
			bigList.remove(i);
		}
	}
}

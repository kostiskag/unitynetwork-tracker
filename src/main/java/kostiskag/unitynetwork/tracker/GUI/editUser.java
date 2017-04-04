package kostiskag.unitynetwork.tracker.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import kostiskag.unitynetwork.tracker.App;
import kostiskag.unitynetwork.tracker.database.Queries;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class editUser {

	private JFrame frmEditUserEntry;
	private JTextField textField;
	private JLabel lblUsername;
	private JTextField textField_1;
	private JLabel lblPassword;
	private JLabel lblType;
	private JTextField textField_3;
	private int type;
	private int userId;
	private JPasswordField passwordField;
	private JButton btnNewButton;
	private JLabel label;
	private JComboBox<String> comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					editUser window = new editUser(0,0);
					window.frmEditUserEntry.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public editUser(int type, int userId) {
		//type is 
		//0 for new entry
		//1 for update
		this.type = type;
		this.userId = userId;
		initialize();
		if (type == 0) {
			btnNewButton.setText("Add new entry");
		} else {
			btnNewButton.setText("Update entry");
		}
		frmEditUserEntry.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmEditUserEntry = new JFrame();
		frmEditUserEntry.setResizable(false);
		frmEditUserEntry.setTitle("Edit user entry");
		frmEditUserEntry.setBounds(100, 100, 450, 300);
		frmEditUserEntry.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmEditUserEntry.getContentPane().setLayout(null);
		
		JLabel lblId = new JLabel("id");
		lblId.setBounds(10, 14, 17, 14);
		frmEditUserEntry.getContentPane().add(lblId);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(76, 11, 75, 20);
		frmEditUserEntry.getContentPane().add(textField);
		textField.setColumns(10);
		
		lblUsername = new JLabel("username");
		lblUsername.setBounds(10, 58, 56, 14);
		frmEditUserEntry.getContentPane().add(lblUsername);
		
		textField_1 = new JTextField();
		textField_1.setBounds(76, 55, 128, 20);
		frmEditUserEntry.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		lblPassword = new JLabel("password");
		lblPassword.setBounds(10, 99, 56, 14);
		frmEditUserEntry.getContentPane().add(lblPassword);
		
		lblType = new JLabel("type");
		lblType.setBounds(10, 141, 46, 14);
		frmEditUserEntry.getContentPane().add(lblType);
		
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"system", "user", "robot", "goverment service/organisation/company "}));
		comboBox.setBounds(76, 138, 257, 20);
		frmEditUserEntry.getContentPane().add(comboBox);
		
		JLabel lblFullName = new JLabel("full name");
		lblFullName.setBounds(10, 183, 56, 14);
		frmEditUserEntry.getContentPane().add(lblFullName);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(76, 180, 257, 20);
		frmEditUserEntry.getContentPane().add(textField_3);
		
		btnNewButton = new JButton("Add new entry");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password = new String(passwordField.getPassword());
				if (!textField_1.getText().isEmpty() && !password.isEmpty() && !textField_3.getText().isEmpty()){
					if (textField_1.getText().length() <= App.max_str_len_small_size && password.length() <= App.max_str_len_large_size && textField_3.getText().length() <= App.max_str_len_large_size) {
						Queries q;
						try {
							q = new Queries();
							if (type == 0) {			
								q.insertEntryUsers(textField_1.getText(), password, comboBox.getSelectedIndex(), textField_3.getText());
							} else {
								q.updateEntryUsersWithId(userId, textField_1.getText(), password, comboBox.getSelectedIndex(), textField_3.getText());
							}
							q.closeQueries();
						} catch (SQLException ex) {
							if (ex.getErrorCode() == 19) { 
								label.setText("The given username is already taken.");
								return;
						    } else { 
						    	ex.printStackTrace();
						    }	
						}										
						
						App.window.updateDatabaseGUI();
						frmEditUserEntry.dispose();
					
					} else {
						label.setText("Please provide a Hostname up to "+App.max_str_len_small_size+" characters and a number up to "+App.max_int_str_len+" digits.");
					}
				} else {
					label.setText("Please fill in all the fields.");
				}			
			}
		});
		btnNewButton.setBounds(296, 233, 128, 23);
		frmEditUserEntry.getContentPane().add(btnNewButton);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(76, 96, 257, 20);
		frmEditUserEntry.getContentPane().add(passwordField);
		
		label = new JLabel("");
		label.setForeground(new Color(204, 0, 0));
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		label.setBounds(10, 208, 414, 14);
		frmEditUserEntry.getContentPane().add(label);
	}
}
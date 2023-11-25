package tictactoe;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class Register {

	JFrame frame;
	private JTextField fieldName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register window = new Register();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Register() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	    int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
	    int frameWidth = frame.getWidth();
	    int frameHeight = frame.getHeight();
	    int x = (screenWidth - frameWidth) / 2;
	    int y = (screenHeight - frameHeight) / 2;
	    
	    frame.setLocation(x, y);
	    
	    JPanel panel = new JPanel();
	    panel.setBackground(new Color(5, 182, 147));
	    frame.getContentPane().add(panel, BorderLayout.CENTER);
	    panel.setLayout(null);
	    
	    JLabel labelName = new JLabel("Name");
	    labelName.setForeground(new Color(255, 255, 255));
	    labelName.setFont(new Font("Rockwell", Font.BOLD, 15));
	    labelName.setBounds(76, 76, 77, 14);
	    panel.add(labelName);
	    
	    fieldName = new JTextField();
	    fieldName.setBounds(144, 72, 153, 27);
	    panel.add(fieldName);
	    fieldName.setColumns(10);
	    
	    JPanel panel_1 = new JPanel();
	    panel_1.setBackground(new Color(5, 182, 147));
	    panel_1.setBounds(10, 11, 364, 38);
	    panel.add(panel_1);
	    
	    JLabel title = new JLabel("Register Name");
	    panel_1.add(title);
	    title.setForeground(new Color(255, 255, 255));
	    title.setFont(new Font("Rockwell", Font.BOLD, 20));
	    
	    JButton addButton = new JButton("Add Name");
	    addButton.setFont(new Font("Rockwell", Font.BOLD, 12));
	    addButton.setBounds(144, 122, 115, 27);
	    panel.add(addButton);
	    addButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		String name = fieldName.getText();
                if (!name.isEmpty()) {
                    try {
                    	Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projecttictactoe", "root", "");

                        String checkSql = "SELECT * FROM username WHERE Name=?";
                        
                        PreparedStatement checkPst = con.prepareStatement(checkSql);
                        checkPst.setString(1, name);
                        ResultSet rs = checkPst.executeQuery();
                        boolean nameFound = rs.next();

                        if (nameFound) {
                            JOptionPane.showMessageDialog(frame, name + " is already registered", "Error", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            String insertSql = "INSERT INTO username (Name) VALUES (?)";
                            
                            PreparedStatement pst = con.prepareStatement(insertSql);
                            pst.setString(1, name);
                            int newName = pst.executeUpdate();

                            if (newName > 0) {
                                JOptionPane.showMessageDialog(frame, name + " registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                fieldName.setText("");
                            } else {
                                JOptionPane.showMessageDialog(frame, "Failed to register name!", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }

                        con.close();
                    } catch (ClassNotFoundException | SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Database error!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a name!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
	    });
	    addButton.setBackground(new Color(255, 255, 255));
	    
	    JButton backButton = new JButton("Go Back");
	    backButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		InitialPage login = new InitialPage();
	    		login.frame.setVisible(true);
	    		frame.dispose();
	    	}
	    });
	    backButton.setFont(new Font("Rockwell", Font.BOLD, 12));
	    backButton.setBackground(Color.WHITE);
	    backButton.setBounds(144, 160, 115, 27);
	    panel.add(backButton);
	}
}

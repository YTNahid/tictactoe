package tictactoe;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
//import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
//import javax.swing.SwingConstants;

public class InitialPage {

	JFrame frame;
	private JTextField playerOneName;
	private JTextField playerTwoName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InitialPage window = new InitialPage();
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
	public InitialPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setSize(600, 400);
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
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(5, 182, 147));
		panel_1.setBounds(0, 28, 584, 44);
		panel.add(panel_1);
		
		JLabel lblNewLabel_1 = new JLabel("WELCOME TO");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Rockwell", Font.BOLD, 28));
		panel_1.add(lblNewLabel_1);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBackground(new Color(5, 182, 147));
		panel_1_1.setBounds(0, 66, 584, 44);
		panel.add(panel_1_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("TicTacToe");
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Rockwell", Font.BOLD, 28));
		panel_1_1.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel = new JLabel("Player 1 Name:");
		lblNewLabel.setFont(new Font("Rockwell", Font.BOLD, 16));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(137, 134, 158, 43);
		panel.add(lblNewLabel);
		
		playerOneName = new JTextField();
		playerOneName.setBounds(271, 144, 174, 26);
		playerOneName.setText("");
		panel.add(playerOneName);
		playerOneName.setColumns(10);
		
		JLabel lblPlayerName = new JLabel("Player 2 Name:");
		lblPlayerName.setForeground(Color.WHITE);
		lblPlayerName.setFont(new Font("Rockwell", Font.BOLD, 16));
		lblPlayerName.setBounds(137, 178, 158, 43);
		panel.add(lblPlayerName);
		
		playerTwoName = new JTextField();
		playerTwoName.setColumns(10);
		playerTwoName.setBounds(271, 188, 174, 26);
		playerTwoName.setText("");
		panel.add(playerTwoName);
		
		JCheckBox aiMode = new JCheckBox("Easy Mode");
		aiMode.setForeground(Color.WHITE);
		aiMode.setFont(new Font("Rockwell", Font.PLAIN, 11));
		aiMode.setBackground(new Color(5, 182, 147));
		aiMode.setBounds(169, 327, 106, 23);
		aiMode.setVisible(false);
		panel.add(aiMode);
		
		JCheckBox enableAI = new JCheckBox("Enable AI");
		enableAI.setFont(new Font("Rockwell", Font.PLAIN, 14));
		enableAI.setForeground(new Color(255, 255, 255));
		enableAI.setBackground(new Color(5, 182, 147));
		enableAI.setBounds(159, 301, 106, 23);
		enableAI.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        boolean isSelected = enableAI.isSelected();
		        aiMode.setVisible(isSelected); // Show aiButton if enableAI is selected
		    }
		});
		panel.add(enableAI);
		
		JButton playBtn = new JButton("Play");
		playBtn.setBounds(271, 294, 135, 33);
		panel.add(playBtn);
		playBtn.setFont(new Font("Rockwell", Font.BOLD, 18));
		playBtn.setBackground(new Color(255, 255, 255));
		playBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(playerOneName.getText().isEmpty() || playerTwoName.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Please enter player names!", "Warning", JOptionPane.INFORMATION_MESSAGE);
				} else {
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projecttictactoe", "root", "");
						
						String sql = "SELECT * FROM username WHERE BINARY Name=?";
						
						PreparedStatement pst = con.prepareStatement(sql);
						pst.setString(1, playerOneName.getText());
						ResultSet rs = pst.executeQuery();
						boolean player1Found = rs.next();
						
						pst = con.prepareStatement(sql);
						pst.setString(1, playerTwoName.getText());
						rs = pst.executeQuery();
						boolean player2Found = rs.next();
						
						if(player1Found && player2Found) {
							TicTacToe game = new TicTacToe();
							if(enableAI.isSelected()) game.AI = true;
							if(aiMode.isSelected()) game.easyMode = true;
							
							game.playerOne.setText(playerOneName.getText());
							game.playerTwo.setText(playerTwoName.getText());
							game.playerOneName = (playerOneName.getText());
							game.playerTwoName = (playerTwoName.getText());
							game.gameStatus.setText(playerOneName.getText() + "'s Turn... [ X ]");
							game.frame.setVisible(true);
							frame.dispose();
						} else {
							if(!player1Found && !player2Found) {
								JOptionPane.showMessageDialog(frame, playerOneName.getText() + " and " + playerTwoName.getText() + " are not registered" , "Access Denied", JOptionPane.ERROR_MESSAGE);
							} else if(!player1Found) {
								JOptionPane.showMessageDialog(frame, playerOneName.getText() + " is not registered" , "Access Denied", JOptionPane.ERROR_MESSAGE);
							} else if(!player2Found) {
								JOptionPane.showMessageDialog(frame, playerTwoName.getText() + " is not registered" , "Access Denied", JOptionPane.ERROR_MESSAGE);
							}
						}
					} catch(Exception e1) {
						JOptionPane.showMessageDialog(null, e1);
					}
				}
			}
		});
		
		JButton autoPlay = new JButton("Auto Play");
		autoPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TicTacToe game = new TicTacToe();
				
				game.autoPlay = true;
				game.playerOne.setText("Computer 1");
				game.playerTwo.setText("Computer 2");
				game.playerOneName = ("Computer 1");
				game.playerTwoName = ("Computer 2");
				game.gameStatus.setText("Computer 1" + "'s Turn... [ X ]");
				game.setAITimerDelay(800);
				game.aiTimer.start();
				game.frame.setVisible(true);
				frame.dispose();
			}
		});
		autoPlay.setFont(new Font("Rockwell", Font.BOLD, 18));
		autoPlay.setBackground(Color.WHITE);
		autoPlay.setBounds(416, 294, 135, 33);
		panel.add(autoPlay);
		
		JButton registerBtn = new JButton("Register");
		registerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Register reg = new Register();
				reg.frame.setVisible(true);
				frame.dispose();
			}
		});
		registerBtn.setFont(new Font("Rockwell", Font.PLAIN, 11));
		registerBtn.setBackground(new Color(255, 255, 255));
		registerBtn.setBounds(271, 260, 83, 23);
		panel.add(registerBtn);
	}
}



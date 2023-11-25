package tictactoe;

//import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//import javax.swing.JComboBox;
//import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Toolkit;

public class TicTacToe {

	JFrame frame;
	JLabel gameStatus;
	JTextField playerOne;
	JTextField playerTwo;
	String playerOneName;
	String playerTwoName;
	Timer aiTimer;
	boolean AI = false;
	boolean easyMode = false;
	boolean autoPlay = false;
	private JButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
	private int b1 = 0, b2 = 0, b3 = 0, b4 = 0, b5 = 0, b6 = 0, b7 = 0, b8 = 0, b9 = 0;
	private JTextField score1;
	private JTextField score2;
	private String initialTurn = "X";
	private int cnt, xCnt, oCnt;
	private boolean checkForGameOver;	
	private int gameCounts; 
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TicTacToe window = new TicTacToe();
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
	public TicTacToe() {
		initialize();
	}
	
	// Updates Score in the Score box when a game finishes
	private void updateScore() { 
		score1.setText(String.valueOf(xCnt));
		score2.setText(String.valueOf(oCnt));
	}
	
	// Checks if the game has ended
	private boolean WarningToResetGame() { 
		if(checkForGameOver) {
			JOptionPane.showMessageDialog(frame, "Please reset the game first", "Warning", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		
		return false;
	}
	
	// Sets 'X' or 'O' for each player turn, this function is used when any button is pressed
	private void changePlayerTurn() { 
		if(initialTurn.equals("X")) {
			initialTurn = "O";
		} else {
			initialTurn = "X";
		}
	}
	
	// Changes turns for player in each game, this function is used in Reset Board
	private void changeTurnForNewGame() { 
		if(gameCounts%2 == 0) initialTurn = "X";
		else initialTurn = "O";
		
	}
	
	// Check if 'X' or 'O' is playing, return to gameStatus that shows it below player name
	private String checkTurn() {
		if(checkForGameOver) {
			return "Game Over...";
		} else if(initialTurn.equals("X")) {
			return playerOneName + "'s turn... [ X ]";
		} else {
			return playerTwoName + "'s turn... [ O ]";
		}
	}
	
	// Checking if button is already pressed, this is used in buttons to execute the button's functions or not
	private boolean checkInvalidSelection(JButton c) { 
		if(c.getText() != "") return false;
		return true;
	}
	
	// Shows warning if a button is already pressed, this is for showing warning
	private void warnInvalidSelection(JButton c) { 
		if(c.getText() != "") JOptionPane.showMessageDialog(frame, "Invalid Selection!", "Warning", JOptionPane.INFORMATION_MESSAGE);
	}
	
	// Show dialogue if player 1 wins
	private void playerOneWinDialogue() { 
		String p1Name = playerOne.getText();
		JOptionPane.showMessageDialog(frame, p1Name + " Wins!", "Game Over!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	// Show dialogue if player 2 wins
	private void playerTwoWinDialogue() { 
		String p2Name = playerTwo.getText();
		JOptionPane.showMessageDialog(frame, p2Name + " Wins!", "Game Over!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	// Change BG color after winning
	private void changeBG(JButton b1, JButton b2, JButton b3) { 
		b1.setBackground(new Color(166, 243, 228));
		b2.setBackground(new Color(166, 243, 228));
		b3.setBackground(new Color(166, 243, 228));
	}
	
	// What happens when a Player wins
	private void winAction(JButton bt1, JButton bt2, JButton bt3, int winner) {
		changeBG(bt1, bt2, bt3);
		if(winner == 1) {
			playerOneWinDialogue();
			xCnt++;
		}
		else if(winner == 2) {
			playerTwoWinDialogue();
			oCnt++;
		}
		updateScore();
		checkForGameOver = true;
	}
	
	// Make 2D array of winning patterns
	private int[][] winPatterns = {
			{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, 	// Horizontal
		    {1, 4, 7}, {2, 5, 8}, {3, 6, 9}, 	// Vertical
		    {1, 5, 9}, {3, 5, 7}			 	// Diagonal
	};
	
	private void checkForWin() {
	    for (int[] i : winPatterns) {
	        if (getStatus(i[0]) == 1 && getStatus(i[1]) == 1 && getStatus(i[2]) == 1) {
	            winAction(getButton(i[0]), getButton(i[1]), getButton(i[2]), 1);
	            return;
	        } else if (getStatus(i[0]) == 2 && getStatus(i[1]) == 2 && getStatus(i[2]) == 2) {
	            winAction(getButton(i[0]), getButton(i[1]), getButton(i[2]), 2);
	            return;
	        }
	    }

	    if (cnt == 9) {
	        JOptionPane.showMessageDialog(frame, "Draw!", "Game Over!", JOptionPane.INFORMATION_MESSAGE);
	        checkForGameOver = true;
	        return;
	    }
	}
	
	// What happens when a button is clicked
	private void btnAction(JButton btn, int b) {
		WarningToResetGame();
		if(!checkForGameOver) warnInvalidSelection(btn);
		if(!checkForGameOver && checkInvalidSelection(btn)) {
			btn.setText(initialTurn);
			if(initialTurn.equals("X")) {
				btn.setForeground(new Color(5, 182, 147));
				cnt++;
				for(int i = 1; i <= 9; i++) {
					if(b == i) setStatus(i, 1);
				}
			} else {
				btn.setForeground(new Color(28, 47, 67));
				cnt++;
				for(int i = 1; i <= 9; i++) {
					if(b == i) setStatus(i, 2);
				}
			}
			
			changePlayerTurn();					
			checkForWin();
			gameStatus.setText(checkTurn());
			
			if(autoPlay) aiTimer.start();
			else if (AI && !checkForGameOver && initialTurn.equals("O")) aiTimer.start();
		}
	}
	
	// AI code	
	private void randomMove() {
	    int[] emptyCells = new int[9];
	    int count = 0;

	    // Find empty cells
	    for (int i = 1; i < 10; i++) {
	        if (checkInvalidSelection(getButton(i))) {
	            emptyCells[count] = i;
	            count++;
	        }
	    }

	    if (count > 0) {
	        JButton aiButton = getButton(emptyCells[(int)(Math.random() * count)]);
	        aiButton.doClick();
	    }
	}
	
	private int checkGameResult() {
	    for (int[] i : winPatterns) {
	        if (getStatus(i[0]) == 1 && getStatus(i[1]) == 1 && getStatus(i[2]) == 1) {
	            return 1;
	        } else if (getStatus(i[0]) == 2 && getStatus(i[1]) == 2 && getStatus(i[2]) == 2) {
	            return 2;
	        }
	    }

	    if (cnt == 9) {
	        return 3;
	    }
	    
	    return -1;
	}
	
	private void aiMoveX() {
		int bestScore = Integer.MIN_VALUE;
		int bestMove = 0;
		
		if(cnt == 0) {
			bestMove = (int)(Math.random()*9 + 1);
		} else {
			for(int i = 1; i <= 9; i++) {
				if(getStatus(i) == 0) {
					setStatus(i, 1);
					cnt++;
					int score = minimaxX(true);
					setStatus(i, 0);
					cnt--;
					if(score > bestScore) {
						bestScore = score;
						bestMove = i;
					}
				}
			}
		}
		
		JButton aiButton = getButton(bestMove);
		aiButton.doClick();
	}
	
	private void aiMoveO() {
		int bestScore = Integer.MAX_VALUE;
		int bestMove = 0;
		
		if(cnt == 0) {
			bestMove = (int)(Math.random()*9 + 1);
		} else {
			for(int i = 1; i <= 9; i++) {
				if(getStatus(i) == 0) {
					setStatus(i, 2);
					cnt++;
					int score = minimaxO(true);
					setStatus(i, 0);
					cnt--;
					if(score < bestScore) {
						bestScore = score;
						bestMove = i;
					}
				}
			}
		}
		
		JButton aiButton = getButton(bestMove);
		aiButton.doClick();
	}
	
	private int minimaxX(boolean isMaximizing) {
	    int result = checkGameResult();

	    if (result == 1) return 1;
	    else if (result == 2) return -1;
	    else if (result == 3) return 0;

	    if (isMaximizing) {
	        int bestScore = Integer.MAX_VALUE;

	        for (int i = 1; i <= 9; i++) {
	            if (getStatus(i) == 0) {
	                setStatus(i, 2);
	                cnt++;
	                int score = minimaxX(false);
	                setStatus(i, 0);
	                cnt--;
	                bestScore = Math.min(score, bestScore);
	            }
	        }
	        return bestScore;
	    } else {
	        int bestScore = Integer.MIN_VALUE;

	        for (int i = 1; i <= 9; i++) {
	            if (getStatus(i) == 0) {
	                setStatus(i, 1);
	                cnt++;
	                int score = minimaxX(true);
	                setStatus(i, 0);
	                cnt--;
	                bestScore = Math.max(score, bestScore);
	            }
	        }
	        return bestScore;
	    }
	}
	
	private int minimaxO(boolean isMaximizing) {
	    int result = checkGameResult();

	    if (result == 1) return 1;
	    else if (result == 2) return -1;
	    else if (result == 3) return 0;

	    if (isMaximizing) {
	        int bestScore = Integer.MIN_VALUE;

	        for (int i = 1; i <= 9; i++) {
	            if (getStatus(i) == 0) {
	                setStatus(i, 1);
	                cnt++;
	                int score = minimaxO(false);
	                setStatus(i, 0);
	                cnt--;
	                bestScore = Math.max(score, bestScore);
	            }
	        }
	        return bestScore;
	    } else {
	        int bestScore = Integer.MAX_VALUE;

	        for (int i = 1; i <= 9; i++) {
	            if (getStatus(i) == 0) {
	                setStatus(i, 2);
	                cnt++;
	                int score = minimaxO(true);
	                setStatus(i, 0);
	                cnt--;
	                bestScore = Math.min(score, bestScore);
	            }
	        }
	        return bestScore;
	    }
	}
	
	void performAIMove() {
		if(easyMode || autoPlay) {
			int rand = (int)(Math.random()*3 + 1);
			if(rand == 1) randomMove();
			else if(initialTurn.equals("X")) aiMoveX();
			else if(initialTurn.equals("O")) aiMoveO();
		} else {
			if(initialTurn.equals("X")) aiMoveX();
			else if(initialTurn.equals("O")) aiMoveO();
		}
	}

	
	// Getter-Setter
	private JButton getButton(int index) {
	    switch (index) {
	        case 1: return btn1;
	        case 2: return btn2;
	        case 3: return btn3;
	        case 4: return btn4;
	        case 5: return btn5;
	        case 6: return btn6;
	        case 7: return btn7;
	        case 8: return btn8;
	        case 9: return btn9;
	        default: return null;
	    }
	}
	
	private int getStatus(int index) {
	    switch (index) {
	        case 1: return b1;
	        case 2: return b2;
	        case 3: return b3;
	        case 4: return b4;
	        case 5: return b5;
	        case 6: return b6;
	        case 7: return b7;
	        case 8: return b8;
	        case 9: return b9;
	        default: return 0; // Default status when index is invalid
	    }
	}
	
	private void setStatus(int index, int value) {
	    switch (index) {
	        case 1: b1 = value; break;
	        case 2: b2 = value; break;
	        case 3: b3 = value; break;
	        case 4: b4 = value; break;
	        case 5: b5 = value; break;
	        case 6: b6 = value; break;
	        case 7: b7 = value; break;
	        case 8: b8 = value; break;
	        case 9: b9 = value; break;
	        default: return;
	    }
	}
	
	public void setAITimerDelay(int delay) {
	    aiTimer.setDelay(delay);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {	
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	    int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
	    int frameWidth = frame.getWidth();
	    int frameHeight = frame.getHeight();
	    int x = (screenWidth - frameWidth) / 2;
	    int y = (screenHeight - frameHeight) / 2;
	    
	    frame.setLocation(x, y);	
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(new Color(255, 255, 255));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		changeTurnForNewGame();
		gameCounts++;
		
		aiTimer = new Timer(300, new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	performAIMove();
		        if(!autoPlay) {
		        	aiTimer.stop();
		        } else {
		        	if(cnt == 9 || checkForGameOver) aiTimer.stop();
		        }
		    }
		});
		
		gameStatus = new JLabel(checkTurn());
		gameStatus.setFont(new Font("Rockwell", Font.BOLD, 14));
		gameStatus.setForeground(new Color(5, 182, 147));
		gameStatus.setBounds(364, 159, 210, 23);
		panel.add(gameStatus);
		
		btn1 = new JButton("");
		btn1.setFont(new Font("Rockwell", Font.BOLD, 55));
		btn1.setForeground(new Color(5, 182, 147));
		btn1.setBorder(null);
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAction(btn1, 1);
			}
		});
		btn1.setBackground(new Color(255, 255, 255));
		btn1.setBounds(10, 46, 108, 94);
		panel.add(btn1);
		
		btn2 = new JButton("");
		btn2.setFont(new Font("Rockwell", Font.BOLD, 55));
		btn2.setBorder(null);
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAction(btn2, 2);
			}
		});
		btn2.setForeground(new Color(5, 182, 147));
		btn2.setBackground(new Color(255, 255, 255));
		btn2.setBounds(128, 46, 108, 94);
		panel.add(btn2);
		
		btn3 = new JButton("");
		btn3.setFont(new Font("Rockwell", Font.BOLD, 55));
		btn3.setBorder(null);
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAction(btn3, 3);
			}
		});
		btn3.setForeground(new Color(5, 182, 147));
		btn3.setBackground(new Color(255, 255, 255));
		btn3.setBounds(246, 46, 108, 94);
		panel.add(btn3);
		
		btn4 = new JButton("");
		btn4.setFont(new Font("Rockwell", Font.BOLD, 55));
		btn4.setBorder(null);
		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAction(btn4, 4);
			}
		});
		btn4.setForeground(new Color(5, 182, 147));
		btn4.setBackground(new Color(255, 255, 255));
		btn4.setBounds(10, 151, 108, 94);
		panel.add(btn4);
		
		btn5 = new JButton("");
		btn5.setFont(new Font("Rockwell", Font.BOLD, 55));
		btn5.setBorder(null);
		btn5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAction(btn5, 5);
			}
		});
		btn5.setForeground(new Color(5, 182, 147));
		btn5.setBackground(new Color(255, 255, 255));
		btn5.setBounds(128, 151, 108, 94);
		panel.add(btn5);
		
		btn6 = new JButton("");
		btn6.setFont(new Font("Rockwell", Font.BOLD, 55));
		btn6.setBorder(null);
		btn6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAction(btn6, 6);
			}
		});
		btn6.setForeground(new Color(5, 182, 147));
		btn6.setBackground(new Color(255, 255, 255));
		btn6.setBounds(246, 151, 108, 94);
		panel.add(btn6);
		
		btn7 = new JButton("");
		btn7.setFont(new Font("Rockwell", Font.BOLD, 55));
		btn7.setBorder(null);
		btn7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAction(btn7, 7);
			}
		});
		btn7.setForeground(new Color(5, 182, 147));
		btn7.setBackground(new Color(255, 255, 255));
		btn7.setBounds(10, 256, 108, 94);
		panel.add(btn7);
		
		btn8 = new JButton("");
		btn8.setFont(new Font("Rockwell", Font.BOLD, 55));
		btn8.setBorder(null);
		btn8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAction(btn8, 8);
			}
		});
		btn8.setForeground(new Color(5, 182, 147));
		btn8.setBackground(new Color(255, 255, 255));
		btn8.setBounds(128, 256, 108, 94);
		panel.add(btn8);
		
		btn9 = new JButton("");
		btn9.setFont(new Font("Rockwell", Font.BOLD, 55));
		btn9.setBorder(null);
		btn9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAction(btn9, 9);
			}
		});
		btn9.setForeground(new Color(5, 182, 147));
		btn9.setBackground(new Color(255, 255, 255));
		btn9.setBounds(246, 256, 108, 94);
		panel.add(btn9);
		
		JButton newGame = new JButton("New Game");
		newGame.setFont(new Font("Rockwell", Font.BOLD, 20));
		newGame.setBorder(null);
		newGame.setForeground(new Color(255, 255, 255));
		newGame.setBackground(new Color(59, 198, 171));
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame newFrame = new JFrame("Confirmation");
				if(JOptionPane.showConfirmDialog(newFrame, "Start a new game?", "Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
					for(int i = 1; i <= 9; i++) {
						getButton(i).setText("");
						getButton(i).setBackground(new Color(255, 255, 255));
						setStatus(i, 0);
					}
					playerOne.setText("changePlayerTurn 1");
					playerTwo.setText("changePlayerTurn 2");
					score1.setText("0");
					score2.setText("0");
					cnt = 0;
					checkForGameOver = false;
					initialTurn = "X";
					gameStatus.setText(checkTurn());
					xCnt = 0;
					oCnt = 0;
					gameCounts = 0;
					changeTurnForNewGame();
					gameCounts++;
					
					InitialPage login = new InitialPage();
					login.frame.setVisible(true);
					frame.dispose();
				}
			}
		});
		newGame.setBounds(364, 46, 210, 32);
		panel.add(newGame);
		
		JButton resetScore = new JButton("Reset Score");
		resetScore.setFont(new Font("Rockwell", Font.BOLD, 14));
		resetScore.setBorder(null);
		resetScore.setForeground(new Color(255, 255, 255));
		resetScore.setBackground(new Color(59, 198, 171));
		resetScore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				score1.setText("0");
				score2.setText("0");
				xCnt = 0;
				oCnt = 0;
			}
		});
		resetScore.setBounds(364, 241, 129, 29);
		panel.add(resetScore);
		
		JButton resetBoard = new JButton("Reset Board");
		resetBoard.setFont(new Font("Rockwell", Font.BOLD, 14));
		resetBoard.setBorder(null);
		resetBoard.setForeground(new Color(255, 255, 255));
		resetBoard.setBackground(new Color(59, 198, 171));
		resetBoard.setBounds(364, 281, 129, 29);
		resetBoard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i = 1; i <= 9; i++) {
					getButton(i).setText("");
					getButton(i).setBackground(new Color(255, 255, 255));
					setStatus(i, 0);
				}
				cnt = 0;
				checkForGameOver = false;
				changeTurnForNewGame();
				gameCounts++;
				gameStatus.setText(checkTurn());
				if(autoPlay) performAIMove();
				else if(initialTurn.equals("O") && AI) aiTimer.start();
			}
		});
		panel.add(resetBoard);
		
		JButton exit = new JButton("Exit");
		exit.setFont(new Font("Rockwell", Font.BOLD, 14));
		exit.setBorder(null);
		exit.setForeground(new Color(255, 255, 255));
		exit.setBackground(new Color(59, 198, 171));
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame = new JFrame("Exit");
				if(JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Exit TicTacToe", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
			}
		});
		exit.setBounds(364, 321, 129, 29);
		panel.add(exit);
		
		playerOne = new JTextField();
		playerOne.setEditable(false);
		playerOne.setFont(new Font("Rockwell", Font.PLAIN, 11));
		playerOne.setBounds(364, 89, 86, 20);
		panel.add(playerOne);
		playerOne.setColumns(10);
		
		playerTwo = new JTextField();
		playerTwo.setEditable(false);
		playerTwo.setFont(new Font("Rockwell", Font.PLAIN, 11));
		playerTwo.setBounds(364, 120, 86, 20);
		panel.add(playerTwo);
		playerTwo.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("TicTacToe");
		lblNewLabel.setBackground(new Color(5, 182, 147));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Rockwell", Font.BOLD, 28));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 0, 564, 42);
		panel.add(lblNewLabel);
		
		score1 = new JTextField();
		score1.setEditable(false);
		score1.setFont(new Font("Rockwell", Font.PLAIN, 11));
		score1.setText("0");
		score1.setHorizontalAlignment(SwingConstants.CENTER);
		score1.setBounds(460, 89, 33, 20);
		panel.add(score1);
		score1.setColumns(10);
		
		score2 = new JTextField();
		score2.setEditable(false);
		score2.setFont(new Font("Rockwell", Font.PLAIN, 11));
		score2.setHorizontalAlignment(SwingConstants.CENTER);
		score2.setText("0");
		score2.setBounds(460, 120, 33, 20);
		panel.add(score2);
		score2.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Menu");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Rockwell", Font.BOLD, 16));
		lblNewLabel_1.setBounds(379, 205, 195, 25);
		panel.add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(166, 243, 228));
		panel_1.setBounds(10, 46, 344, 304);
		panel.add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(59, 198, 171));
		panel_2.setBounds(10, 0, 564, 42);
		panel.add(panel_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(59, 198, 171));
		panel_3.setBounds(364, 201, 210, 32);
		panel.add(panel_3);
		
		JLabel lblNewLabel_3 = new JLabel("X");
		lblNewLabel_3.setBounds(515, 91, 9, 15);
		panel.add(lblNewLabel_3);
		lblNewLabel_3.setFont(new Font("Rockwell", Font.BOLD, 12));
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
	}
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Driver extends JFrame {
	private final int WINDOW_WIDTH = 400; // Window width
	private final int WINDOW_HEIGHT = 400; // Window height
	private JButton[] buttons;
	private JButton[][] boardButtons;
	private Connect4 board;
	private int turn;
	
	//GUI Constructor
	public Driver() {
		setTitle("Connect 4");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new GridLayout(7, 7));
		buttons = new JButton[7];
		
		for(int b = 0; b < 7; b++) {
			buttons[b] = new JButton((b + 1) + "");
			buttons[b].setBackground(Color.RED);
			buttons[b].addActionListener(new ButtonListener());
			add(buttons[b]);
		}

		boardButtons = new JButton[6][7];
		for(int r = 5; r >= 0; r--) {
			for(int c = 0; c < 7; c++) {
				boardButtons[r][c] = new JButton();
				boardButtons[r][c].setBackground(Color.WHITE);
				add(boardButtons[r][c]);
			}
		}
		
		board = new Connect4();
		turn = 1; //red plays first
		
		setVisible(true);
		
	}
	
	private class ButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			for(int col = 0; col < 7; col++) {
				if(e.getSource() == buttons[col]) {
					
					if(board.validCol(col)) {
						int row = board.playTurn(turn, col);
						String whoPlayed = new String();			
						
						if(turn == 1) {
							boardButtons[row][col].setBackground(Color.RED);
							whoPlayed = "RED";
							
							//Change whose turn it is
							turn = 2;
							for(int b = 0; b < 7; b++)
								buttons[b].setBackground(Color.YELLOW);
						}
						else {
							boardButtons[row][col].setBackground(Color.YELLOW);
							whoPlayed = "YELLOW";
							
							//Change whose turn it is
							turn = 1;
							for(int b = 0; b < 7; b++)
								buttons[b].setBackground(Color.RED);
						}
						
						if(board.fourInRow(row, col)) {
							JOptionPane.showMessageDialog(null, whoPlayed + " WINS!");
							System.exit(0);
						}
					}
					
					break;
				}
			}
			
		} //end method
		
	} //end class
	
	public static void main(String[] args) {
		JOptionPane.showMessageDialog(null, "Use the top row to drop coins into the grid on your turn.");
		new Driver();
	}
	
}


class Connect4 {
    private int[][] grid;
    
    public Connect4() {
        grid = new int[6][7];
        
        for(int r = 0; r < 6; r++)
        	for(int c= 0; c< 7; c++)
        		grid[r][c] = 0;
    }
    
    public int playTurn(int color, int col) {
        for(int r = 0; r < 6; r++) {
            if(grid[r][col] == 0) {
                grid[r][col] = color;
                return r;
            }
        }
        
        return -1;
    }
    
    public boolean validCol(int col) {
    	if(grid[5][col] == 0)
    		return true;
    	else
    		return false;
    }        
    
    public boolean fourInRow(int row, int col) {
        int color = grid[row][col];
        
        int h = 1; //horizontal counter
        int v = 1; //vertical counter
        int d = 1; //diagonal counter
        
        //count horizontal in a row
        for(int c = col + 1; c < 7; c++) {
            if(grid[row][c] == color)
                h++;
            else
                break;
        }
        for(int c = col - 1; c >= 0; c--) {
            if(grid[row][c] == color)
                h++;
            else
                break;
        }
        
        if(h >= 4)
            return true;
        
        //count vertical in a row
        for(int r = row + 1; r < 6; r++) {
            if(grid[r][col] == color)
                v++;
            else
                break;
        }
        for(int r = row - 1; r >= 0; r--) {
            if(grid[r][col] == color)
                v++;
            else
                break;
        }
        
        if(v >= 4)
            return true;
        
        //count diagonal in a row
        int r = row + 1;
        int c = col + 1;
        while(r < 6 && c < 7) {
            if(grid[r][c] == color)
                d++;
            else
                break;
                
            r++;
            c++;
        }
        
        r = row - 1;
        c = col - 1;
        while(r >= 0 && c >= 0) {
            if(grid[r][c] == color)
                d++;
            else
                break;
                
            r--;
            c--;
        }
        
        if(d >= 4)
            return true;
        else
            d = 1;
        
        r = row + 1;
        c = col - 1;
        while(r < 6 && c >= 0) {
            if(grid[r][c] == color)
                d++;
            else
                break;
                
            r++;
            c--;
        }
        
        r = row - 1;
        c = col + 1;
        while(c < 7 && r >= 0) {
            if(grid[r][c] == color)
                d++;
            else
                break;
                
            r--;
            c++;
        }
        
        if(d >= 4)
            return true;
        else
            return false;
    }
}

package backtrack;

import java.awt.Frame;
import java.awt.Panel;
import java.awt.Button;
import java.awt.GridLayout;

import java.awt.event.WindowAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

public class PlayTicTacToe extends Frame {

	private static final long serialVersionUID = 1L;

	public PlayTicTacToe() {
		add(new TicTacPanel());

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) {
		Frame f = new PlayTicTacToe();
		f.pack();
		f.setVisible(true);
	}

	private static class TicTacPanel extends Panel implements ActionListener {
	
		private static final long serialVersionUID = 1L;

		private int gameNum = 0;
		private Button[][] squares = new Button[3][3];
		private AlphaBetaPruningTTT ttt;	// MinimaxTicTacToe
		private String computerSide = "O";
		private String humanSide = "X";
		
		public TicTacPanel() {
			setLayout(new GridLayout(3, 3));
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++) {
					squares[i][j] = new Button();
					add(squares[i][j]);
					squares[i][j].addActionListener(this);
				}

			resetBoard();
		}

		public void resetBoard() {
			ttt = new AlphaBetaPruningTTT();
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++) {
					squares[i][j].setLabel("");
					squares[i][j].setEnabled(true);
				}
		}

		public void doCompMove(boolean thinkAboutIt) {
			Move compMove;

			if (thinkAboutIt)
				compMove = ttt.chooseMove(MinimaxTicTacToe.COMPUTER);
			else {
				compMove = new Move(0, gameNum % 3, gameNum / 3);
				gameNum = (gameNum + 1) % 9;
			}

			System.out.println(" ROW = " + compMove.row + " COL = "
					+ compMove.column);

			squares[compMove.row][compMove.column].setLabel(computerSide);
			squares[compMove.row][compMove.column].setEnabled(false);
			ttt.playMove(MinimaxTicTacToe.COMPUTER, compMove.row, compMove.column);
		}

		public boolean resetIfDone(boolean condition, String message,
				boolean compMoves) {
			if (condition) {
				System.out.println(message);
				System.out.println("Restarting...");
				resetBoard();
				if (compMoves) {
					System.out.println("I go first...");
					computerSide = "X";
					humanSide = "O";
					doCompMove(false);
				} else {
					humanSide = "X";
					computerSide = "O";
					System.out.println("You go first...");
				}
			}
			return condition;
		}

		public void actionPerformed(ActionEvent evt) {
			if (evt.getSource() instanceof Button) {
				((Button) evt.getSource()).setLabel(humanSide);
				((Button) evt.getSource()).setEnabled(false);

				for (int i = 0; i < 3; i++)
					for (int j = 0; j < 3; j++)
						if (evt.getSource() == squares[i][j])
							ttt.playMove(MinimaxTicTacToe.HUMAN, i, j);

				if (resetIfDone(ttt.boardIsFull(), "DRAW", true))
					return;
				doCompMove(true);
				resetIfDone(ttt.isAWin(MinimaxTicTacToe.COMPUTER), "I WIN!!",
						true);
				resetIfDone(ttt.boardIsFull(), "DRAW", false);

				return;
			}
		}

	}

}

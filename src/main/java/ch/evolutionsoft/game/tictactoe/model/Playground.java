package ch.evolutionsoft.game.tictactoe.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author EvolutionSoft
 */
public class Playground {

	public static final int MAX_ROW = 3;
	public static final int MAX_COLUMN = 3;
	public static final char EMPTY_FIELD = ' ';

	private char[][] position;

	Playground() {

		this.position = new char[MAX_ROW][MAX_COLUMN];
		this.initPlayGround();
	}

	public Playground(char[][] position) {

		this.position = new char[MAX_ROW][MAX_COLUMN];
		System.arraycopy(position, 0, this.position, 0, MAX_ROW);
	}

	public char[][] getPosition() {

		char[][] positionCopy = new char[MAX_ROW][MAX_COLUMN];
		System.arraycopy(this.position, 0, positionCopy, 0, MAX_ROW);

		return positionCopy;
	}

	public char getFieldValue(int row, int column) {
		
		return this.position[row][column];
	}
	
	boolean isEmpty(int row, int column) {

		return this.position[row][column] == EMPTY_FIELD;
	}

	void reset() {

		this.initPlayGround();
	}

	public boolean hasWon(char player) {

		return this.horizontalWin(player) || this.verticalWin(player) || this.diagonalWin(player);
	}

	public void enterMove(int row, int column, char player) {

		ensureLegalRange(row, column);

		this.position[row][column] = player;
	}

	public void undoMove(int row, int column) {

		ensureLegalRange(row, column);

		this.position[row][column] = EMPTY_FIELD;
	}

	public List<Move> getAvailableMoves(char color) {

		List<Move> moves = new ArrayList<>();

		for (int m = 0; m < MAX_ROW; m++) {

			for (int n = 0; n < MAX_COLUMN; n++) {

				if (this.position[m][n] == EMPTY_FIELD) {

					moves.add(new Move(m, n, color));
				}
			}
		}

		return moves;
	}

	public boolean hasEmptyFieldsLeft() {

		for (char[] row : position) {

			for (char field : row) {

				if (EMPTY_FIELD == field) {

					return true;
				}
			}
		}

		return false;
	}

	private boolean diagonalWin(char player) {

		return (this.position[0][0] == player && this.position[1][1] == player && this.position[2][2] == player)
		    || (this.position[2][0] == player && this.position[1][1] == player && this.position[0][2] == player);
	}

	private boolean verticalWin(char player) {

		return (this.position[0][0] == player && this.position[1][0] == player && this.position[2][0] == player)
		    || (this.position[0][1] == player && this.position[1][1] == player && this.position[2][1] == player)
		    || (this.position[0][2] == player && this.position[1][2] == player && this.position[2][2] == player);
	}

	private boolean horizontalWin(char player) {

		return (this.position[0][0] == player && this.position[0][1] == player && this.position[0][2] == player)
		    || (this.position[1][0] == player && this.position[1][1] == player && this.position[1][2] == player)
		    || (this.position[2][0] == player && this.position[2][1] == player && this.position[2][2] == player);
	}

	private void initPlayGround() {

		for (int n = 0; n < position.length; n++) {

			for (int m = 0; m < position[0].length; m++) {

				position[n][m] = EMPTY_FIELD;
			}
		}
	}

	private void ensureLegalRange(int row, int column) {

		if (!inRange(row, column)) {

			throw new IllegalArgumentException();
		}
	}

	private boolean inRange(int row, int column) {

		return row >= 0 && row < MAX_ROW && column >= 0 && column < MAX_COLUMN;
	}
}

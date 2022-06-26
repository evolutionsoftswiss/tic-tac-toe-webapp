package ch.evolutionsoft.game.tictactoe.model;

/**
 * @author EvolutionSoft
 */
public class Move {

	private int row;
	private int column;
	private char color;

	public Move(int row, int column, char color) {

		this.row = row;
		this.column = column;
		this.color = color;
	}
	
	public char getColor() {

		return this.color;
	}
	
	public int getColumn() {

		return this.column;
	}
	
	public int getRow() {

		return this.row;
	}
	
	public String toString(){

		return color + ": " + this.row + ", " + this.column;
	}
}

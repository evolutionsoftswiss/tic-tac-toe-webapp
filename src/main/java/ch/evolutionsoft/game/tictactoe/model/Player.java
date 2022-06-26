package ch.evolutionsoft.game.tictactoe.model;

/**
 * @author EvolutionSoft
 */
public abstract class Player {

	public static final char FIRST_PLAYER = 'X';
	public static final char SECOND_PLAYER = 'O';

	protected char color;
	
	Player(char color) {

		this.color = color;
	}
	
	public char getColor() {

		return this.color;
	}

	public void setColor(char color) {

		this.color = color;
	}
}

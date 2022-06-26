package ch.evolutionsoft.game.tictactoe.model;

import java.util.Observable;

/**
 * @author EvolutionSoft
 */
public class Game extends Observable {

	private Player playerX;
	private Player playerO;
	private char turn;
	private Playground playground;

	private boolean gameOver = false;

	public Game(Player firstPlayer, Player secondPlayer) {

		this.playerX = firstPlayer.getColor() == Player.FIRST_PLAYER ? firstPlayer : secondPlayer;
		this.playerO = secondPlayer.getColor() == Player.SECOND_PLAYER ? secondPlayer : firstPlayer;
		this.turn = Player.FIRST_PLAYER;
		this.playground = new Playground();
	}

	public Playground getPlayground() {

		return this.playground;
	}

	public Player getCurrentPlayer() {

		return (this.turn == Player.FIRST_PLAYER) ? playerX : playerO;
	}

	public Player getPlayerX() {
		
		return this.playerX;
	}

	public Player getPlayerO() {
		
		return this.playerO;
	}
	
	public void switchPlayers() {

		Player tempPlayer = playerX;

		this.playerX = playerO;
		this.playerX.setColor(Player.FIRST_PLAYER);

		this.playerO = tempPlayer;
		this.playerO.setColor(Player.SECOND_PLAYER);
	}

	public boolean isOver() {

		return this.gameOver;
	}
	
	public void start() {

		if (this.playerX instanceof ComputerPlayer) {

			((ComputerPlayer) this.playerX).move(this);
		}
	}

	public void newGame() {

		this.gameOver = false;
		this.turn = Player.FIRST_PLAYER;
		this.playground.reset();
	}

	public void move(Move move) {

		this.playground.enterMove(move.getRow(), move.getColumn(), getCurrentPlayer().getColor());

		this.checkState(move);
		this.swapTurn();

		if (this.getCurrentPlayer() instanceof ComputerPlayer && !this.gameOver) {

			((ComputerPlayer) this.getCurrentPlayer()).move(this);
		}
	}
	
	public boolean isLegalMove(int row, int column) {
		
		return this.playground.isEmpty(row, column);
	}

	private void swapTurn() {

		turn = (turn == Player.FIRST_PLAYER) ? Player.SECOND_PLAYER : Player.FIRST_PLAYER;
	}

	public void checkState(Move move) {

		if (playground.hasWon(turn)) {

			this.gameOver = true;
			this.setChanged();
			
			Player winningPlayer = (turn == Player.FIRST_PLAYER) ? playerX : playerO;
			this.notifyObservers(winningPlayer);

		} else if (!playground.hasEmptyFieldsLeft()) {

			this.gameOver = true;
			this.setChanged();
			this.notifyObservers("draw");
		
		} else {

			this.setChanged();
			this.notifyObservers(move);
		}
	}
}

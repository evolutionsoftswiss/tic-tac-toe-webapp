package ch.evolutionsoft.game.tictactoe.treesearch;

import ch.evolutionsoft.game.tictactoe.model.Move;
import ch.evolutionsoft.game.tictactoe.model.Player;
import ch.evolutionsoft.game.tictactoe.model.Playground;

import java.util.Collections;
import java.util.List;

/**
 * @author EvolutionSoft
 */
public class AlphaBetaSearch {

	private static final int MAX_WIN = 10;
	private static final int MIN_WIN = -10;

	private static final int DRAW = 0;

	private Playground playGround;
	private Move bestMove;
	private int numberOfNodes;


	public AlphaBetaSearch(char[][] position) {

		char[][] positionCopy = new char[Playground.MAX_ROW][Playground.MAX_COLUMN];
		System.arraycopy(position, 0, positionCopy, 0, Playground.MAX_ROW);
		this.playGround = new Playground(positionCopy);
		this.bestMove = null;
	}


	public Move getBestMove() {

		return this.bestMove;
	}


	public int getNumberOfNodes() {

		return this.numberOfNodes;
	}


	public void searchMax() {

		int bestValue = Integer.MIN_VALUE;
		List<Move> moves = this.playGround.getAvailableMoves(Player.FIRST_PLAYER);

		Collections.shuffle(moves);

		for (int n = 0; n < moves.size(); n++) {

			Move currentMove = moves.get(n);
			this.playGround.enterMove(currentMove.getRow(), currentMove.getColumn(), Player.FIRST_PLAYER);
			int currentValue = min(Integer.MIN_VALUE, Integer.MAX_VALUE, 1);

			if (currentValue > bestValue) {
				bestValue = currentValue;
				bestMove = currentMove;
			}

			this.playGround.undoMove(currentMove.getRow(), currentMove.getColumn());
		}
	}


	public void searchMin() {

		int bestValue = Integer.MAX_VALUE;
		List<Move> moves = this.playGround.getAvailableMoves(Player.SECOND_PLAYER);

		Collections.shuffle(moves);

		for (int n = 0; n < moves.size(); n++) {

			Move currentMove = moves.get(n);
			this.playGround.enterMove(currentMove.getRow(), currentMove.getColumn(), Player.SECOND_PLAYER);
			int currentValue = max(Integer.MIN_VALUE, Integer.MAX_VALUE, 1);

			if (currentValue < bestValue) {
				bestValue = currentValue;
				bestMove = currentMove;
			}

			this.playGround.undoMove(currentMove.getRow(), currentMove.getColumn());
		}
	}


	private int max(int alpha, int beta, int depth) {

		this.numberOfNodes++;
		if (this.playGround.hasWon(Player.SECOND_PLAYER)) {

			return MIN_WIN + depth;
		}

		if (!this.playGround.hasEmptyFieldsLeft()) {

			return DRAW;
		}

		int value = Integer.MIN_VALUE;
		List<Move> moves = this.playGround.getAvailableMoves(Player.FIRST_PLAYER);

		for (int n = 0; n < moves.size(); n++) {

			Move currentMove = moves.get(n);
			this.playGround.enterMove(currentMove.getRow(), currentMove.getColumn(), Player.FIRST_PLAYER);

			value = Math.max(value, this.min(alpha, beta, depth + 1));

			this.playGround.undoMove(currentMove.getRow(), currentMove.getColumn());

			if (value > beta) {
				return value;
			}

			alpha = Math.max(alpha, value);
		}
		return value;
	}


	private int min(int alpha, int beta, int depth) {

		this.numberOfNodes++;

		if (this.playGround.hasWon(Player.FIRST_PLAYER)) {

			return MAX_WIN - depth;
		}

		if (!this.playGround.hasEmptyFieldsLeft()) {

			return DRAW;
		}

		int value = Integer.MAX_VALUE;
		List<Move> moves = this.playGround.getAvailableMoves(Player.SECOND_PLAYER);

		for (int n = 0; n < moves.size(); n++) {

			Move currentMove = moves.get(n);
			this.playGround.enterMove(currentMove.getRow(), currentMove.getColumn(), Player.SECOND_PLAYER);

			value = Math.min(value, this.max(alpha, beta, depth + 1));

			this.playGround.undoMove(currentMove.getRow(), currentMove.getColumn());

			if (value < alpha) {
				return value;
			}

			beta = Math.min(value, beta);
		}
		return value;
	}
}

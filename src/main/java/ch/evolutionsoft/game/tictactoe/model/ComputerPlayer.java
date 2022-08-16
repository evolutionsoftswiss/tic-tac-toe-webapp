package ch.evolutionsoft.game.tictactoe.model;

import ch.evolutionsoft.game.tictactoe.treesearch.AlphaBetaSearch;

/**
 * @author EvolutionSoft
 */
public class ComputerPlayer extends Player {

	public ComputerPlayer(char color) {

		super(color);
	}


	void move(Game game) {

		AlphaBetaSearch alphaBetaSearch = new AlphaBetaSearch(game.getPlayground().getPosition());

		if (this.color == Player.FIRST_PLAYER) {

			alphaBetaSearch.searchMax();

		} else {

			alphaBetaSearch.searchMin();
		}

		Move move = alphaBetaSearch.getBestMove();
		game.move(move);
	}
}

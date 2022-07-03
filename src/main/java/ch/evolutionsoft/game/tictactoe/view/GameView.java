package ch.evolutionsoft.game.tictactoe.view;

import java.util.Collection;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.evolutionsoft.game.tictactoe.model.ComputerPlayer;
import ch.evolutionsoft.game.tictactoe.model.Game;
import ch.evolutionsoft.game.tictactoe.model.HumanPlayer;
import ch.evolutionsoft.game.tictactoe.model.Move;
import ch.evolutionsoft.game.tictactoe.model.Player;
import ch.evolutionsoft.game.tictactoe.model.Playground;

@ManagedBean
@ViewScoped
public class GameView implements Observer {

	Game game;

	String statusText;

	public static final String PLAY_WITH_X = "tictactoe.youBegin";
	public static final String PLAY_WITH_O = "tictactoe.cpuBegins";
	boolean switchColorsEnabled;
  String startPlayerText = PLAY_WITH_O;

	Random random = new Random();

	static final Logger gameLog = LogManager.getLogger(GameView.class); 


  @PostConstruct
	public void initialize() {

  	this.game = new Game(
  				new ComputerPlayer(Player.FIRST_PLAYER),
  				new HumanPlayer(Player.SECOND_PLAYER));
		this.game.addObserver(this);
		this.initGame();
		
		gameLog.info("Tic Tac Toe Game initialized.");
	}

  public void move(int row, int column) {

    Player currentPlayer = this.game.getCurrentPlayer();

    if (currentPlayer instanceof HumanPlayer) {
  
      if (this.game.isLegalMove(row, column) && !game.isOver()) {
      
        Move humanMove = new Move(row, column, currentPlayer.getColor());
        this.game.move(humanMove);
      }
    }
  }

	
	public void newGame() {
		
		this.initGame();
		
		gameLog.info("Tic Tac Toe Game restarted.");
	}


	public void switchColors() {

		if (PLAY_WITH_O.equals(this.startPlayerText)) {
			
			this.startPlayerText = PLAY_WITH_X;
			
		} else {
			
			this.startPlayerText = PLAY_WITH_O;
		}

		this.game.switchPlayers();
		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("menuForm");
	}


	public boolean notOver() {
	  
	  return !this.game.isOver();
	}
	
	
	public String getCurrentPlayer() {
	  
	  return String.valueOf(this.game.getCurrentPlayer().getColor());
	}


	public String getPlaygroundField(int row, int column) {

		Playground playground = this.game.getPlayground();

		char playgroundField = playground.getFieldValue(row, column);

		return String.valueOf(playgroundField);
	}


	@Override
	public void update(Observable observable, Object argument) {

		if (argument instanceof String && argument.equals("draw")) {

			setStatusText("tictactoe.draw");
			this.setSwitchColorsEnabled(true);

		} else if (argument instanceof Player) {

			String newText = "tictactoe.cpuWins";

			if (argument instanceof HumanPlayer) {
				newText = "tictactoe.humanWins";
			}

			setStatusText(newText);
			this.setSwitchColorsEnabled(true);
		
		} else if (argument instanceof Move) {
			
			this.setMoveStatus((Move) argument);
		}

		this.rerenderForms();
	}


	public void rerenderForms() {
	
		Collection<String> renderIds = FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds();
		renderIds.add("tictactoeForm");
		renderIds.add("menuForm");
	}


	public String getStatusText() {

		return statusText;
	}

	
	public void setStatusText(String statusText) {

		this.statusText = statusText;
	}


	public boolean isSwitchColorsEnabled() {

		return switchColorsEnabled;
	}


	public void setSwitchColorsEnabled(boolean switchColorsEnabled) {

		this.switchColorsEnabled = switchColorsEnabled;
	}


	public String getStartPlayerText() {

		return startPlayerText;
	}


	public void setStartPlayerText(String switchColorText) {

		this.startPlayerText = switchColorText;
	}

	
	private void initGame() {

		this.game.newGame();
		
		Player firstPlayer = this.game.getCurrentPlayer();
		
		this.switchColorsEnabled = firstPlayer instanceof HumanPlayer;
		
		this.setMoveStatus(null);
		
		if (firstPlayer instanceof ComputerPlayer) {
			
			Move randomMove = new Move(
					this.random.nextInt(Playground.MAX_ROW),
					this.random.nextInt(Playground.MAX_COLUMN),
					Player.FIRST_PLAYER);
			this.game.move(randomMove);
		}
	}


	private void setMoveStatus(Move lastMove) {

		Player lastMovePlayer;
		if (lastMove != null && Player.FIRST_PLAYER == lastMove.getColor()) {
			
			lastMovePlayer = this.game.getPlayerX();
		
		} else {
			
			lastMovePlayer = this.game.getPlayerO();
		}
		
		if (lastMovePlayer instanceof ComputerPlayer) {
			
			this.statusText = "tictactoe.yourTurn";
		
		} else {

			this.statusText = "tictactoe.cpuTurn";
		}
	}
}

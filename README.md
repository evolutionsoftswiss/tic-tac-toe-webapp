# tic-tac-toe-webapp
A Tic Tac Toe web application designed for web servers like tomcat. Successfully tested with apache tomcat 9.0.64.

You find a running version on [evolutionsoft.ch](https://evolutionsoft.ch/tic-tac-toe).

## License
Tic Tac Toe webapp is provided under the Apache-2.0 license.

## Installation instructions
You can download and install the latest release war file on a recent version of tomcat.

## Implementation Information

### Alpha Beta search intelligence
Tic Tac Toe allows because of the simplicity and very limited number of possible game variations a full brute force search.
Here the advanced variation of mini max search alpha beta is used to additionally reduce the search space.

You can find the search intelligence source in *AlphaBetaSearch.java* in package *ch.evolutionsoft.game.tictactoe.treesearch*.

### Tic Tac Toe Frontend UI
The Tic Tac Toe portlet uses a Primefaces 10.0 JSF implementation to interact with users.

package com.negusoft.tictactoe

import com.negusoft.tictactoe.data.Grid
import com.negusoft.tictactoe.data.Player
import com.negusoft.tictactoe.data.Position

/**
 * A tic-tac-toe game where two players on a 3x3 grid.
 */
class Game {

    var state: GameState = GameState.Ongoing(Player.X)
        private set
    var grid = Grid()
        private set

    /**
     * Make a move with the current player.
     * Returns the result with either the new state or the error of the move.
     */
    fun makeMove(column: Position.Row, row: Position.Column): MoveResult {
        return TODO()
    }
}

sealed class GameState {
    class Ongoing(val currentPlayer: Player) : GameState()
    class Finished(val result: GameResult): GameState()
}

sealed class GameResult {
    class Win(val winner: Player): GameResult()
    object Draw: GameResult()
}

sealed class MoveResult {
    enum class Reason { SQUARE_NOT_EMPTY, GAME_ALREADY_FINISHED }

    class Success(val state: GameState): MoveResult()
    class Error(val reason: Reason): MoveResult()
}
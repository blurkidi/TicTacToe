package com.negusoft.tictactoe.domain

import com.negusoft.tictactoe.data.Grid
import com.negusoft.tictactoe.data.Player
import com.negusoft.tictactoe.data.Position

/**
 * A tic-tac-toe game where two players on a 3x3 grid.
 */
class Game(private val lineDetector: LineDetector = LineDetector()) {

    var state: GameState =
        GameState.Ongoing(Player.X)
        private set
    var grid = Grid()
        private set

    /**
     * Make a move with the current player.
     * Returns the result with either the new state or the error of the move.
     */
    fun makeMove(column: Position.Row, row: Position.Column): MoveResult {
        val ongoingState = state as? GameState.Ongoing
            ?: return MoveResult.Error(
                MoveResult.Reason.GAME_ALREADY_FINISHED
            )
        val currentPlayer = ongoingState.currentPlayer

        if (grid[column, row] != null)
            return MoveResult.Error(
                MoveResult.Reason.SQUARE_NOT_EMPTY
            )

        grid = grid.edit(currentPlayer, column, row)
        state = getNewState(currentPlayer, grid)

        return MoveResult.Success(state)
    }

    private fun getNewState(currentPlayer: Player, grid: Grid): GameState {
        val lines = lineDetector.detect(currentPlayer, grid)
        if (lines.isNotEmpty())
            return GameState.Finished(
                GameResult.Win(currentPlayer)
            )

        if (grid.isFull)
            return GameState.Finished(
                GameResult.Draw
            )

        // Next player
        return GameState.Ongoing(currentPlayer.toggle())
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
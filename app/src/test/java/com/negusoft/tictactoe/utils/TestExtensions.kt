package com.negusoft.tictactoe.utils

import com.negusoft.tictactoe.domain.GameResult
import com.negusoft.tictactoe.domain.GameState
import com.negusoft.tictactoe.domain.MoveResult
import com.negusoft.tictactoe.data.Player
import org.junit.Assert


// Assert that the state is ongoing with the given players turn.
fun GameState.assertOngoing(player: Player) {
    val ongoing = this as? GameState.Ongoing ?: error("Invalid state")
    Assert.assertEquals(ongoing.currentPlayer, player)
}

// Assert that the state is ongoing with the given players turn.
fun GameState.assertFinished(assertion: (GameState.Finished) -> Unit) {
    val finished = this as? GameState.Finished ?: error("Invalid state")
    assertion(finished)
}

// Assert that the move is successful and use the expression for additional checks.
fun MoveResult.assertSuccess(assertion: (MoveResult.Success) -> Unit) {
    val success = this as? MoveResult.Success ?: error("Invalid move result")
    assertion(success)
}

// Assert that the move is successful and use the expression for additional checks.
fun MoveResult.assertError(assertion: (MoveResult.Error) -> Unit) {
    val error = this as? MoveResult.Error ?: error("Invalid move result")
    assertion(error)
}

// Assert that the move is successful and use the expression for additional checks.
fun GameResult.assertWinner(winner: Player) {
    val win = this as? GameResult.Win ?: error("Invalid game result")
    Assert.assertEquals(win.winner, winner)
}
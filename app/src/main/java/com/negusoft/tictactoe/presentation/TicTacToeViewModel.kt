package com.negusoft.tictactoe.presentation

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.negusoft.tictactoe.Game
import com.negusoft.tictactoe.GameResult
import com.negusoft.tictactoe.GameState
import com.negusoft.tictactoe.MoveResult
import com.negusoft.tictactoe.data.Position
import com.negusoft.tictactoe.utils.databinding.Event
import com.negusoft.tictactoe.utils.databinding.setEventValue

class TicTacToeViewModel : ViewModel() {

    private var game = Game()

    val grid = ObservableField(game.grid)
    val state = ObservableField(game.state)

    // The result observable depends on the 'state' and maps to the result when it is finished
    val result: ObservableField<GameResult> = object : ObservableField<GameResult>(state) {
        override fun get(): GameResult? {
            return (state.get() as? GameState.Finished)?.result
        }
    }

    private val _errorEvent = MutableLiveData<Event<MoveResult.Reason>>()
    val errorEvent: LiveData<Event<MoveResult.Reason>> get() = _errorEvent

    fun makeMove(column: Position.Row, row: Position.Column) {
        when (val result = game.makeMove(column, row)) {
            is MoveResult.Success -> handleMoveSuccess(result)
            is MoveResult.Error -> handleMoveError(result)
        }
    }

    fun newGame() {
        game = Game()
        grid.set(game.grid)
        state.set(game.state)
    }

    private fun handleMoveSuccess(success: MoveResult.Success) {
        state.set(success.state)
        grid.set(game.grid)
    }

    private fun handleMoveError(error: MoveResult.Error) {
        _errorEvent.setEventValue(error.reason)
    }

}